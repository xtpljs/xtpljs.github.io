package ru.xtplplugin;
 
import com.intellij.lexer.FlexLexer;
import com.intellij.psi.tree.IElementType;
import ru.xtplplugin.psi.xtplToken;
import com.intellij.psi.TokenType;
 
%%
%{
    private int myPrevState = YYINITIAL;

    private boolean isOpenBrace = false;
    private boolean isOpenParentheses = false;

    public int yyprevstate() {
        return myPrevState;
    }

    private int popState(){
        final int prev = myPrevState;
        myPrevState = YYINITIAL;
        return prev;
    }

    protected void pushState(int state){
        myPrevState = state;
    }

    private boolean pushBackTo(String text){
        final int position = yytext().toString().indexOf(text);

        if( position != -1 ){
            yypushback(yylength() - position);
            return true;
        }

        return false;
    }

    private IElementType openBrace(){
        isOpenBrace = true;
        return xtplToken.BRACES;
    }

    private IElementType closeBrace(){
        isOpenBrace = false;
        return xtplToken.BRACES;
    }

    private IElementType openParentheses(){
        isOpenParentheses = true;
        return xtplToken.PARENTHESES;
    }

    private IElementType closeParentheses(){
        isOpenParentheses = false;
        return xtplToken.PARENTHESES;
    }
%}

 
%class xtplLexer
%implements FlexLexer
%unicode
%function advance
%type IElementType
%eof{  return;
%eof}


ALPHA   = [:letter:]
DIGIT   = [0-9]

WS      = [\ \t\f]
WSNL    = [\ \n\r\t\f]

S       = {WS}+
SNL     = {WSNL}+

CRLF    = \n|\r|\r\n
END     = [\r\n,;]

ESCAPE_SEQ      = \\[^\r\n]
SQ_ESCAPE_SEQ   = ([^\\'\r\n]|{ESCAPE_SEQ})*
DQ_ESCAPE_SEQ   = ([^\\\"\r\n]|{ESCAPE_SEQ})*

SQ_STRING       = ' {SQ_ESCAPE_SEQ} '
DQ_STRING       = \" {DQ_ESCAPE_SEQ} \"


BOOL        = true|false|undefined|null
NUMBER      = (0(x|X)[0-9a-fA-F]+)|(-?[0-9]+(\.[0-9]+)?(e[+\-]?[0-9]+)?)

NODE_NAME   = {ALPHA}({ALPHA}|{DIGIT}|"-")*
NODE_CLASS  = ({ALPHA}|"_")({ALPHA}|{DIGIT}|"-"|"_")*
NODE_ATTR   = {ALPHA}({ALPHA}|{DIGIT}|"-"|"_")*
ID          = [#]({ALPHA}|[_-])*

ATTR_NAME   = ['\"]?{NODE_ATTR}['\"]?{WS}*[:]
XATTR_NAME   = ['\"]?x-{ALPHA}({ALPHA}|{DIGIT}|"-"|"_")*['\"]?{WS}*[:]

VAR_NAME    = [$_a-zA-Z][$_a-zA-Z0-9]*

DECL            = &"{"?({ALPHA}({ALPHA}|{DIGIT}|"_"|"-"|".")*)"}"?
SYSTEM          = else|include|default
SYSTEM_EXPR     = if|for|switch|case
RESERVED        = typeof|void|in
OPERATION_SIGN  = [?:&|/%*~!=<>+-]+


%state YYNODE_NAME, YYNODE_CLASS, YYNODE_ATTR, YYATTR_NAME, YYXATTR_NAME
%state YYEXPR
%state YYEXPR_VALUE_SQ, YYEXPR_VALUE_DQ
%state YYDECL, YYPROP
%state YY_DQ_STRING
%state YY_SCRIPT

%%
 
<YYINITIAL> {
    "//"[^\n]+      { return xtplToken.COMMENT; }

    ">"             { return xtplToken.SEMICOLON; }

    // ~ TEXT ~
    "|"[^\n]+       { return xtplToken.TEXT; }

    {DECL}          { yybegin(YYDECL); return xtplToken.DECLARATION; }

    "@"[^ ]*        { yybegin(YYEXPR); return xtplToken.DECLARATION; }

    "#"             { yypushback(1); yybegin(YYNODE_NAME); }
    "."             { yypushback(1); yybegin(YYNODE_CLASS); }

    // ~ SYSTEM NODE? ~
    {SYSTEM}        { return xtplToken.KEYWORD; }
    {SYSTEM_EXPR}   { yybegin(YYEXPR); return xtplToken.KEYWORD; }

    // ~ START NODE? ~
    {NODE_NAME}     { yybegin(YYNODE_NAME); return  isOpenBrace ? xtplToken.NODE_OR_ATTR : xtplToken.NODE_NAME; }

    // ~ START ATTR? ~
    {XATTR_NAME}    { yypushback(1); yybegin(YYXATTR_NAME); return xtplToken.XATTR_NAME; }
    {ATTR_NAME}     { yypushback(1); yybegin(YYATTR_NAME); return xtplToken.ATTR_NAME; }

    // ~ OTHER CHARACTERS ~
    "{"             { return openBrace(); }
    "}"             { return closeBrace(); }
    "(" | ")"       { return xtplToken.PARENTHESES; }
    {SNL}           { return xtplToken.WHITE_SPACE; }

    // Start SCRIPT block
    "`"[\sa-z0-9]*  { yybegin(YY_SCRIPT); return xtplToken.SCRIPT; }

    ([^@.:#|>\[ \n\r\t\f]|(\\\$))*    { return xtplToken.TEXT; }
    [^@.:#|>\[ \n\r\t\f]|(\\\$)       { return xtplToken.TEXT; }
}


<YYDECL> {
    {S}     { return xtplToken.WHITE_SPACE; }
    "="     { yybegin(YYINITIAL); return xtplToken.OPERATION_SIGN; }
    "{"     { yypushback(1); yybegin(YYINITIAL); }
    [^]     { yypushback(1); yybegin(YYEXPR); }
}


// ~ NODE ~
<YYNODE_NAME> {
    {ID}    { return xtplToken.CTX; }
    "["     { yybegin(YYNODE_ATTR); return xtplToken.BRACES; }
    "."     { yybegin(YYNODE_CLASS); return xtplToken.DOT; }
    {SNL}   { return xtplToken.WHITE_SPACE; }
}


// ~ NODE CLASSES ~
<YYNODE_CLASS> {
    {NODE_CLASS}    { return xtplToken.ATTR_VALUE; }
    "."             { return xtplToken.DOT; }
    "["             { yybegin(YYNODE_ATTR); return xtplToken.BRACES; }
    {SNL}           { yybegin(YYINITIAL); return xtplToken.WHITE_SPACE; }
}


// ~ NODE ATTR ~
<YYNODE_ATTR> {
    {NODE_ATTR}        { return xtplToken.ATTR_NAME; }
    "="                 { return xtplToken.TEXT; }
    "]"                 { yybegin(YYNODE_NAME); return xtplToken.BRACES; }
    "\""[^\]]+           { return xtplToken.STRING; }
}


// ~ XATTR NAME ~
<YYATTR_NAME, YYXATTR_NAME>  {
    ":"     { return xtplToken.COLON; }
    {S}     { return xtplToken.WHITE_SPACE; }
    "{"     { yybegin(YYINITIAL); return xtplToken.BRACES; }
    [^]     { yypushback(1); yybegin(YYEXPR); }
}


// ~ EXPRSSION ~
<YYEXPR> {
    {BOOL}              { return xtplToken.BOOL; }
    {NUMBER}            { return xtplToken.NUMBER; }
    {RESERVED}          { return xtplToken.KEYWORD; }
    {OPERATION_SIGN}    { return xtplToken.OPERATION_SIGN; }

    "{" | "}"           { return xtplToken.BRACES; }
    "[" | "]"           { return xtplToken.BRACKETS; }
    "("                 { return openParentheses(); }
    ")"                 { return closeParentheses(); }

    "."                 { return xtplToken.DOT; }
    ";"                 { return xtplToken.SEMICOLON; }

//    {SQ_STRING}        { return xtplToken.STRING; }
//    {DQ_STRING}        { return xtplToken.STRING; }
    "\""                { yybegin(YY_DQ_STRING); return xtplToken.STRING; }

    "ctx"               { return xtplToken.CTX; }
    {VAR_NAME}          { return xtplToken.VAR; }
    "//"[^\n]+          { return xtplToken.COMMENT; }

    {S}                 { return xtplToken.WHITE_SPACE; }
    {END}               { if( !isOpenParentheses ){ yybegin(YYINITIAL); } return xtplToken.WHITE_SPACE; }
}


// ~ STRING "..." ~
<YY_DQ_STRING> {
    {DQ_ESCAPE_SEQ}     { return xtplToken.STRING; }
    "\""                { yybegin(YYEXPR); return xtplToken.STRING; }
}


// SCRIPT block
<YY_SCRIPT> {
    "`"     { yybegin(YYINITIAL); return xtplToken.SCRIPT; }
    [^]     { return xtplToken.SCRIPT; }
}


// ~ Nothing matched ~
[^] {
    if( yystate() == YYINITIAL ){
        return xtplToken.BAD_CHARACTER;
    }
    else {
        yybegin(popState());
        yypushback(yylength());
    }
}
