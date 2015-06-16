package ru.xtplplugin.psi;

import com.intellij.extapi.psi.ASTWrapperPsiElement;
import com.intellij.lang.ASTNode;
import com.intellij.psi.PsiElement;

public class xtplElementImpl extends ASTWrapperPsiElement implements PsiElement {

  private final ASTNode node;

  public xtplElementImpl(final ASTNode node) {
    super(node);
    this.node = node;
  }

  public String toString() {
    return "xtpl:" + node.getElementType().toString();
  }

}
