package ru.xtplplugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.*;

import javax.swing.*;

public class xtplFileType extends LanguageFileType {
    public static final xtplFileType INSTANCE = new xtplFileType();

    private xtplFileType() {
        super(xtplLang.INSTANCE);
    }

    @NotNull
    @Override
    public String getName() {
        return "xtpl";
    }

    @NotNull
    @Override
    public String getDescription() {
        return "xtpl language file";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return "xtpl";
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return xtplIcons.FILE;
    }
}