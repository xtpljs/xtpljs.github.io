package ru.xtplplugin;

import com.intellij.lang.ASTNode;
import com.intellij.lang.Language;
import com.intellij.lang.ParserDefinition;
import com.intellij.lang.PsiParser;
import com.intellij.lexer.FlexAdapter;
import com.intellij.lexer.Lexer;
import com.intellij.openapi.project.Project;
import com.intellij.psi.FileViewProvider;
import com.intellij.psi.PsiElement;
import com.intellij.psi.PsiFile;
import com.intellij.psi.TokenType;
import com.intellij.psi.tree.IFileElementType;
import com.intellij.psi.tree.TokenSet;
import ru.xtplplugin.parser.xtplParser;
import ru.xtplplugin.psi.xtplFile;
import ru.xtplplugin.psi.xtplToken;
import ru.xtplplugin.psi.xtplElementImpl;
import org.jetbrains.annotations.NotNull;
import ru.xtplplugin.psi.xtplElementImpl;
import ru.xtplplugin.psi.xtplFile;

import java.io.Reader;

public class xtplParserDefinition implements ParserDefinition{
    public static final TokenSet WHITE_SPACES = TokenSet.create(TokenType.WHITE_SPACE);
    public static final TokenSet COMMENTS = TokenSet.create(xtplToken.COMMENT);
    public static final IFileElementType FILE = new IFileElementType(Language.<xtplLang>findInstance(xtplLang.class));


    @NotNull
    @Override
    public Lexer createLexer(Project project) {
        return new FlexAdapter(new xtplLexer((Reader) null));
    }

    @NotNull
    public TokenSet getWhitespaceTokens() {
        return WHITE_SPACES;
    }

    @NotNull
    public TokenSet getCommentTokens() {
        return COMMENTS;
    }

    @NotNull
    public TokenSet getStringLiteralElements() {
        return TokenSet.EMPTY;
    }

    @NotNull
    public PsiParser createParser(final Project project) {
        return new xtplParser();
    }

    @Override
    public IFileElementType getFileNodeType() {
        return FILE;
    }

    public PsiFile createFile(FileViewProvider viewProvider) {
        return new xtplFile(viewProvider);
    }

    public SpaceRequirements spaceExistanceTypeBetweenTokens(ASTNode left, ASTNode right) {
        return SpaceRequirements.MAY;
    }

    @NotNull
    public PsiElement createElement(ASTNode node) {
        return new xtplElementImpl(node);
    }
}