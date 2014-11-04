package ru.xtplplugin;

import com.intellij.lang.Language;

public class xtplLang extends Language {
    public static final xtplLang INSTANCE = new xtplLang();

    private xtplLang() {
        super("xtpl", "text/xtpl");
    }
}