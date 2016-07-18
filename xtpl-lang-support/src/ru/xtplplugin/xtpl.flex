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


WS      = [\ \t\f]
WSNL    = [\ \n\r\t\f]
LINE_COMMENT = "//"[^\r\n]*

%state YYNODE_NAME

%%
 
<YYINITIAL> {
    {LINE_COMMENT}  { return xtplToken.COMMENT; }
}


// ~ Nothing matched ~
. {
    return xtplToken.BAD_CHARACTER;
}
