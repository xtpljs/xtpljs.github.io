package ru.xtplplugin;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.editor.DefaultLanguageHighlighterColors;
import com.intellij.openapi.editor.HighlighterColors;
import com.intellij.openapi.editor.XmlHighlighterColors;
import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.editor.markup.TextAttributes;
import com.intellij.openapi.fileTypes.SyntaxHighlighterBase;
import com.intellij.psi.tree.IElementType;
import ru.xtplplugin.psi.xtplToken;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.io.Reader;

import java.util.HashMap;
import java.util.Map;


public class xtplSyntaxHighlighter extends SyntaxHighlighterBase {

    private static final Map<IElementType, TextAttributesKey> TOKENS_TO_STYLES;

    @NotNull
    @Override
    public Lexer getHighlightingLexer() {
        return new FlexAdapter(new xtplLexer((Reader) null));
    }

    static final TextAttributesKey TEXT =
            TextAttributesKey.createTextAttributesKey("xtpl.TEXT", HighlighterColors.TEXT.getDefaultAttributes());
//    static final TextAttributesKey TEXT = TextAttributesKey.createTextAttributesKey(
//            "xtpl.TEXT"
//            , TextAttributes.merge(HighlighterColors.TEXT.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.ITALIC)));

    static final TextAttributesKey BAD_CHARACTER =
            TextAttributesKey.createTextAttributesKey("xtpl.BAD_CHARACTER", HighlighterColors.BAD_CHARACTER.getDefaultAttributes());

    static final TextAttributesKey WHITE_SPACE =
            TextAttributesKey.createTextAttributesKey("xtpl.WHITE_SPACE", HighlighterColors.TEXT.getDefaultAttributes());

    static final TextAttributesKey COMMENT =
            TextAttributesKey.createTextAttributesKey("xtpl.COMMENT", DefaultLanguageHighlighterColors.LINE_COMMENT.getDefaultAttributes());

    static final TextAttributesKey STRING =
            TextAttributesKey.createTextAttributesKey("xtpl.STRING", DefaultLanguageHighlighterColors.STRING.getDefaultAttributes());

    static final TextAttributesKey NODE_NAME =
            TextAttributesKey.createTextAttributesKey("xtpl.NODE_NAME", XmlHighlighterColors.HTML_TAG_NAME.getDefaultAttributes());

    static final TextAttributesKey ATTR_NAME =
            TextAttributesKey.createTextAttributesKey("xtpl.ATTR_NAME", DefaultLanguageHighlighterColors.INSTANCE_FIELD.getDefaultAttributes());

    static final TextAttributesKey XATTR_NAME = TextAttributesKey.createTextAttributesKey(
            "xtpl.XATTR_NAME"
            , TextAttributes.merge(DefaultLanguageHighlighterColors.INSTANCE_FIELD.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.ITALIC)));

    static final TextAttributesKey ATTR_VALUE =
            TextAttributesKey.createTextAttributesKey("xtpl.ATTR_VALUE", XmlHighlighterColors.HTML_ATTRIBUTE_VALUE.getDefaultAttributes());

    static final TextAttributesKey DOT =
            TextAttributesKey.createTextAttributesKey("xtpl.DOT", DefaultLanguageHighlighterColors.DOT.getDefaultAttributes());

    static final TextAttributesKey COLON =
            TextAttributesKey.createTextAttributesKey("xtpl.COLON", DefaultLanguageHighlighterColors.SEMICOLON.getDefaultAttributes());

    static final TextAttributesKey BRACES =
            TextAttributesKey.createTextAttributesKey("xtpl.BRACES", DefaultLanguageHighlighterColors.BRACES.getDefaultAttributes());

    static final TextAttributesKey BRACKETS =
            TextAttributesKey.createTextAttributesKey("xtpl.BRACKETS", DefaultLanguageHighlighterColors.BRACKETS.getDefaultAttributes());

    static final TextAttributesKey PARENTHESES =
            TextAttributesKey.createTextAttributesKey("xtpl.PARENTHESES", DefaultLanguageHighlighterColors.PARENTHESES.getDefaultAttributes());

    static final TextAttributesKey SEMICOLON =
            TextAttributesKey.createTextAttributesKey("xtpl.SEMICOLON", DefaultLanguageHighlighterColors.SEMICOLON.getDefaultAttributes());

    static final TextAttributesKey BOOL =
            TextAttributesKey.createTextAttributesKey("xtpl.BOOL", DefaultLanguageHighlighterColors.NUMBER.getDefaultAttributes());

    static final TextAttributesKey NUMBER =
            TextAttributesKey.createTextAttributesKey("xtpl.NUMBER", DefaultLanguageHighlighterColors.NUMBER.getDefaultAttributes());

    static final TextAttributesKey OPERATION_SIGN =
            TextAttributesKey.createTextAttributesKey("xtpl.OPERATION_SIGN", DefaultLanguageHighlighterColors.OPERATION_SIGN.getDefaultAttributes());

    static final TextAttributesKey VAR =
            TextAttributesKey.createTextAttributesKey("xtpl.VAR", DefaultLanguageHighlighterColors.METADATA.getDefaultAttributes());

//    static final TextAttributesKey CTX =
//            TextAttributesKey.createTextAttributesKey("xtpl.CTX", DefaultLanguageHighlighterColors.INSTANCE_METHOD.getDefaultAttributes());
    static final TextAttributesKey CTX = TextAttributesKey.createTextAttributesKey(
        "xtpl.CTX"
        , TextAttributes.merge(DefaultLanguageHighlighterColors.METADATA.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.BOLD)));


        static final TextAttributesKey KEYWORD = TextAttributesKey.createTextAttributesKey(
                  "xtpl.KEYWORD"
                , TextAttributes.merge(DefaultLanguageHighlighterColors.KEYWORD.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.BOLD)));
//    static final TextAttributesKey KEYWORD =
//            TextAttributesKey.createTextAttributesKey("xtpl.KEYWORD", DefaultLanguageHighlighterColors.KEYWORD.getDefaultAttributes());

    static final TextAttributesKey DECLARATION = TextAttributesKey.createTextAttributesKey(
                  "xtpl.DECLARATION"
                , DefaultLanguageHighlighterColors.FUNCTION_CALL.getDefaultAttributes());
//    static final TextAttributesKey DECLARATION = TextAttributesKey.createTextAttributesKey(
//                  "xtpl.DECLARATION"
//                , TextAttributes.merge(DefaultLanguageHighlighterColors.KEYWORD.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.PLAIN)));


    static final TextAttributesKey SCRIPT = TextAttributesKey.createTextAttributesKey(
            "xtpl.SCRIPT"
            , TextAttributes.merge(HighlighterColors.TEXT.getDefaultAttributes(), new TextAttributes(null, null, null, null, Font.ITALIC)));


    static {
        TOKENS_TO_STYLES = new HashMap<IElementType, TextAttributesKey>();

        TOKENS_TO_STYLES.put(xtplToken.TEXT, TEXT);
        TOKENS_TO_STYLES.put(xtplToken.BAD_CHARACTER, BAD_CHARACTER);
        TOKENS_TO_STYLES.put(xtplToken.COMMENT, COMMENT);
        TOKENS_TO_STYLES.put(xtplToken.STRING, STRING);
        TOKENS_TO_STYLES.put(xtplToken.WHITE_SPACE, WHITE_SPACE);

        TOKENS_TO_STYLES.put(xtplToken.NODE_NAME, NODE_NAME);
        TOKENS_TO_STYLES.put(xtplToken.NODE_OR_ATTR, NODE_NAME);

        TOKENS_TO_STYLES.put(xtplToken.ATTR_NAME, ATTR_NAME);
        TOKENS_TO_STYLES.put(xtplToken.ATTR_VALUE, ATTR_VALUE);
        TOKENS_TO_STYLES.put(xtplToken.XATTR_NAME, XATTR_NAME);

        TOKENS_TO_STYLES.put(xtplToken.DOT, DOT);
        TOKENS_TO_STYLES.put(xtplToken.COLON, COLON);
        TOKENS_TO_STYLES.put(xtplToken.BRACES, BRACES);
        TOKENS_TO_STYLES.put(xtplToken.BRACKETS, BRACKETS);
        TOKENS_TO_STYLES.put(xtplToken.SEMICOLON, SEMICOLON);
        TOKENS_TO_STYLES.put(xtplToken.PARENTHESES, PARENTHESES);

        TOKENS_TO_STYLES.put(xtplToken.BOOL, BOOL);
        TOKENS_TO_STYLES.put(xtplToken.NUMBER, NUMBER);
        TOKENS_TO_STYLES.put(xtplToken.OPERATION_SIGN, OPERATION_SIGN);

        TOKENS_TO_STYLES.put(xtplToken.VAR, VAR);
        TOKENS_TO_STYLES.put(xtplToken.CTX, CTX);
        TOKENS_TO_STYLES.put(xtplToken.KEYWORD, KEYWORD);
        TOKENS_TO_STYLES.put(xtplToken.DECLARATION, DECLARATION);
        TOKENS_TO_STYLES.put(xtplToken.SCRIPT, SCRIPT);
    }

    @NotNull
    public TextAttributesKey[] getTokenHighlights(IElementType tokenType) {
        if (!TOKENS_TO_STYLES.containsKey(tokenType)) {
            throw new UnsupportedOperationException(tokenType.toString());
        }
        return pack(TOKENS_TO_STYLES.get(tokenType));
    }
}