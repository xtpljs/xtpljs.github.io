package ru.xtplplugin;

import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.FlexLexer;

/**
 * Created with IntelliJ IDEA.
 * User: RubaXa
 * Date: 11.07.13
 * Time: 23:53
 * To change this template use File | Settings | File Templates.
 */
public class xtplLexerAdapter extends FlexAdapter {
    public xtplLexerAdapter(FlexLexer flex) {
        super(flex);
    }
}
