package ru.xtplplugin;

import com.intellij.openapi.fileTypes.LanguageFileType;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.swing.*;

public class xtplFileType extends LanguageFileType {
    public static final xtplFileType INSTANCE = new xtplFileType();

    @NonNls
    public static final String DEFAULT_EXTENSION = "xtpl";

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
        return "xtpl files";
    }

    @NotNull
    @Override
    public String getDefaultExtension() {
        return DEFAULT_EXTENSION;
    }

    @Nullable
    @Override
    public Icon getIcon() {
        return xtplIcons.FILE;
    }
}