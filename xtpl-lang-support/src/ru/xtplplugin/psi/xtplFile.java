
package ru.xtplplugin.psi;

import com.intellij.extapi.psi.PsiFileBase;
import com.intellij.openapi.fileTypes.FileType;
import com.intellij.psi.FileViewProvider;
import ru.xtplplugin.*;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class xtplFile extends PsiFileBase {
    public xtplFile(@NotNull FileViewProvider viewProvider) {
        super(viewProvider, xtplLang.INSTANCE);
    }

    @NotNull
    @Override
    public FileType getFileType() {
        return xtplFileType.INSTANCE;
    }

    @Override
    public String toString() {
        return "xtpl File";
    }

    @Override
    public Icon getIcon(int flags) {
        return super.getIcon(flags);
    }
}