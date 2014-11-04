package ru.xtplplugin.psi;

import com.intellij.psi.tree.IElementType;
import ru.xtplplugin.xtplLang;
import org.jetbrains.annotations.NonNls;
import org.jetbrains.annotations.NotNull;

public class xtplElementType extends IElementType {
    public xtplElementType(@NotNull @NonNls String debugName) {
        super(debugName, xtplLang.INSTANCE);
    }
}