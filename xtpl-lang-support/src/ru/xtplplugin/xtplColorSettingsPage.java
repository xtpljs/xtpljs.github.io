package ru.xtplplugin;

import com.intellij.openapi.editor.colors.TextAttributesKey;
import com.intellij.openapi.options.colors.AttributesDescriptor;
import com.intellij.openapi.options.colors.ColorDescriptor;
import com.intellij.openapi.options.colors.ColorSettingsPage;
import ru.xtplplugin.xtplIcons;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;
import java.util.Map;

public class xtplColorSettingsPage implements ColorSettingsPage {

    private static final AttributesDescriptor[] ATTRS = new AttributesDescriptor[]{
            new AttributesDescriptor("Bad character", xtplSyntaxHighlighter.BAD_CHARACTER),
            new AttributesDescriptor("Dot", xtplSyntaxHighlighter.DOT),
            new AttributesDescriptor("Colon", xtplSyntaxHighlighter.COLON),
            new AttributesDescriptor("Semicolon", xtplSyntaxHighlighter.SEMICOLON),
            new AttributesDescriptor("Operation sign", xtplSyntaxHighlighter.OPERATION_SIGN),
            new AttributesDescriptor("Parentheses", xtplSyntaxHighlighter.PARENTHESES),
            new AttributesDescriptor("Braces", xtplSyntaxHighlighter.BRACES),
            new AttributesDescriptor("Brackets", xtplSyntaxHighlighter.BRACKETS),
            new AttributesDescriptor("Comment", xtplSyntaxHighlighter.COMMENT),
            new AttributesDescriptor("Number", xtplSyntaxHighlighter.NUMBER),
            new AttributesDescriptor("Boolean", xtplSyntaxHighlighter.BOOL),
            new AttributesDescriptor("String", xtplSyntaxHighlighter.STRING),
            new AttributesDescriptor("Keyword", xtplSyntaxHighlighter.KEYWORD),
            new AttributesDescriptor("Node name", xtplSyntaxHighlighter.NODE_NAME),
            new AttributesDescriptor("Attribute name", xtplSyntaxHighlighter.ATTR_NAME),
            new AttributesDescriptor("ctx", xtplSyntaxHighlighter.CTX),
            new AttributesDescriptor("Variable", xtplSyntaxHighlighter.VAR),
            new AttributesDescriptor("Declaration", xtplSyntaxHighlighter.DECLARATION),
    };

    @NotNull
    public String getDisplayName() {
        return "xtpl";
    }

    public Icon getIcon() {
        return xtplIcons.FILE;
    }

    @NotNull
    public AttributesDescriptor[] getAttributeDescriptors() {
        return ATTRS;
    }

    @NotNull
    public ColorDescriptor[] getColorDescriptors() {
        return ColorDescriptor.EMPTY_ARRAY;
    }

    @NotNull
    public xtplSyntaxHighlighter getHighlighter() {
        return new xtplSyntaxHighlighter();
    }

    @NotNull
    public String getDemoText() {
        return ""+
        "// xtpl example\n"+
        "div {\n"+
        "    data-id: 123\n"+
        "    class: {\n"+
        "        btn: true\n"+
        "        btn_disable: ctx.user.name.length < 1\n"+
        "    }\n"+
        "    style: {\n"+
        "        position: \"absolute\";\n"+
        "        maring-left: -10;\n"+
        "    }\n"+
        "\n"+
        "    expression: (1 + 2) * 3 / 10.12 + ctx.name;\n"+
        "    \"prop-name\": \"prop-value\"\n"+
        "\n"+
        "    a.foo.bar {}\n"+
        "}\n"+
        "\n"+
        "// Declaration\n"+
        "&icon = i.icon {\n"+
        "    class: 'icon_' + ctx.name\n"+
        "    i {}\n"+
        "}\n"+
        "\n"+
        "// Usage\n"+
        "&icon { name: \"search\" }\n"+
        "&icon ctx.icon;\n"+
        "\n"+
        "// Menu\n"+
        "if ctx.items.length {\n"+
        "    ul.menu {\n"+
        "        for item in ctx.items {\n"+
        "            li.menu__item {\n"+
        "                a {\n"+
        "                    href: \"/page/\" + item.href\n"+
        "                    > item.href\n"+
        "                }\n"+
        "            }\n"+
        "        }\n"+
        "    }\n"+
        "} else {\n"+
        "    div.menu.menu_empty {\n"+
        "        > \"Empty menu\"\n"+
        "    }\n"+
        "}\n"+
        "\n"+
        "form {\n"+
        "    submit: ctx.onSubmit($el);\n"+
        "\n"+
        "    > \"Hello \" + ctx.user\n"+
        "\n"+
        "    &icon { name: \"note\" }\n"+
        "    input { x-bind: ctx.user, type: \"text\", maxlength: 32 }\n"+
        "}"
        ;
    }

    public Map<String, TextAttributesKey> getAdditionalHighlightingTagToDescriptorMap() {
        return null;
    }

}
