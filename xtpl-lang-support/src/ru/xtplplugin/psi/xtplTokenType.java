package ru.xtplplugin.psi;

import com.intellij.psi.tree.IElementType;
import ru.xtplplugin.xtplLang;
import org.jetbrains.annotations.*;

public class xtplTokenType extends IElementType {
    public xtplTokenType(@NotNull @NonNls String debugName) {
        super(debugName, xtplLang.INSTANCE);
    }

    @Override
    public String toString() {
        return "xtplTokenType." + super.toString();
    }
}