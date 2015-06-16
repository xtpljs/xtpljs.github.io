package ru.xtplplugin.parser;

import com.intellij.lang.ASTNode;
import com.intellij.lang.PsiBuilder;
import com.intellij.lang.PsiParser;
import com.intellij.psi.PlainTextTokenTypes;
import com.intellij.psi.impl.source.tree.PlainTextASTFactory;
import com.intellij.psi.tree.IElementType;
import org.jetbrains.annotations.NotNull;

public class xtplParser implements PsiParser {

    @NotNull
    public ASTNode parse(IElementType root, PsiBuilder builder) {
        final PsiBuilder.Marker rootMarker = builder.mark();

        while (!builder.eof()) {
            IElementType token = builder.getTokenType();

            if (token != null) {
                builder.mark().done(token);
            }

            builder.advanceLexer();
        }

        rootMarker.done(root);
        return builder.getTreeBuilt();
    }

}
