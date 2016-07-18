package ru.xtplplugin;

import com.intellij.lexer.FlexAdapter;
import java.io.Reader;

public class xtplLexerAdapter extends FlexAdapter {
    public xtplLexerAdapter() {
        super(new xtplLexer((Reader) null));
    }
}
