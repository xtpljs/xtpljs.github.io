package ru.xtplplugin.psi;

import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IElementType;

public interface xtplToken {

    public static final IElementType BAD_CHARACTER = TokenType.BAD_CHARACTER;
    public static final IElementType ERROR_ELEMENT = TokenType.ERROR_ELEMENT;
    public static final IElementType WHITE_SPACE = TokenType.WHITE_SPACE;

    public static final IElementType TEXT = new xtplElementType("TEXT");
    public static final IElementType COMMENT = new xtplElementType("COMMENT");
    public static final IElementType STRING = new xtplElementType("STRING");

    public static final IElementType NODE_NAME = new xtplElementType("NODE_NAME");
    public static final IElementType NODE_OR_ATTR = new xtplElementType("NODE_OR_ATTR");

    public static final IElementType ATTR_NAME = new xtplElementType("ATTR_NAME");
    public static final IElementType XATTR_NAME = new xtplElementType("XATTR_NAME");
    public static final IElementType ATTR_VALUE = new xtplElementType("ATTR_VALUE");

    public static final IElementType DOT = new xtplElementType("DOT");
    public static final IElementType COLON = new xtplElementType("COLON");
    public static final IElementType BRACES = new xtplElementType("BRACES");
    public static final IElementType BRACKETS = new xtplElementType("BRACKETS");
    public static final IElementType SEMICOLON = new xtplElementType("SEMICOLON");
    public static final IElementType PARENTHESES = new xtplElementType("PARENTHESES");

    public static final IElementType BOOL = new xtplElementType("BOOL");
    public static final IElementType NUMBER = new xtplElementType("NUMBER");

    public static final IElementType OPERATION_SIGN = new xtplElementType("OPERATION_SIGN");

    public static final IElementType VAR = new xtplElementType("VAR");
    public static final IElementType CTX = new xtplElementType("CTX");
    public static final IElementType KEYWORD = new xtplElementType("KEYWORD");
    public static final IElementType DECLARATION = new xtplElementType("DECLARATION");

    public static final IElementType SCRIPT = new xtplElementType("SCRIPT");

}
