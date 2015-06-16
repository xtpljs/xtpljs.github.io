package ru.xtplplugin;

import com.intellij.codeInsight.completion.*;
import com.intellij.codeInsight.lookup.LookupElement;
import com.intellij.codeInsight.lookup.LookupElementBuilder;
import com.intellij.patterns.PlatformPatterns;
import com.intellij.util.ProcessingContext;
import ru.xtplplugin.psi.xtplToken;
import org.jetbrains.annotations.NotNull;

import javax.swing.*;

public class xtplCompletionContributor extends CompletionContributor {
    private final String[] systemTags = new String[]{"if", "else", "for"};

    private final String[] htmlTags = new String[]{"a", "abbr", "acronym", "address", "applet", "area", "article",
            "aside", "audio", "b", "base", "basefont", "bdi", "bdo", "big", "blockquote", "body",
            "br", "canvas", "caption", "center", "cite", "code", "col", "colgroup", "command",
            "datalist", "dd", "del", "details", "dfn", "dialog", "dir", "div", "dl", "dt", "em",
            "embed", "fieldset", "figcaption", "figure", "font", "footer", "form", "frame",
            "frameset", "h1", "to", "h6", "head", "header", "hgroup", "hr", "html", "i", "iframe",
            "img", "input", "button", "ins", "kbd", "keygen", "label", "legend", "li", "link", "map", "mark",
            "menu", "meta", "meter", "nav", "noframes", "noscript", "object", "ol", "optgroup",
            "option", "output", "p", "param", "pre", "progress", "q", "rp", "rt", "ruby", "s",
            "samp", "script", "section", "select", "small", "source", "span", "strike", "strong",
            "style", "sub", "summary", "sup", "table", "tbody", "td", "textarea", "tfoot", "th",
            "thead", "time", "title", "tr", "track", "tt", "u", "ul", "var", "video", "wbr"};


    private final String[] events = new String[]{"x-change", "x-select", "x-reset", "x-submit", "x-click", "x-dblclick",
            "x-mousedown", "x-mouseup", "x-mouseover", "x-mouseout", "x-mousemove", "x-mouseenter", "x-mouseleave",
            "x-input", "x-keydown", "x-keypress", "x-keyup",
            "x-model", "x-autofocus"};


    private final String[] attrs = new String[]{"id", "href", "class", "style", "data", "name", "title", "type",
            "readonly", "disabled", "checked", "selected", "maxlength", "method", "action",
            "placeholder"};


    public xtplCompletionContributor() {
        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(xtplToken.NODE_NAME).withLanguage(xtplLang.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        this.addItems(resultSet, systemTags, xtplIcons.TAG, 20);
                        this.addItems(resultSet, htmlTags, null, 10);
                    }

                    private void addItems(CompletionResultSet resultSet, String[] tags, Icon icon, int priority) {
                        for( int i = 0; i < tags.length; i++ ){
                            String name = tags[i];

                            LookupElement element = LookupElementBuilder.create("xtpl", name)
                                    .withPresentableText(name)
                                    .withIcon(icon)
                            ;

                            resultSet.addElement(PrioritizedLookupElement.withPriority(element, priority));
                        }
                    }
                }
        );


        extend(CompletionType.BASIC,
                PlatformPatterns.psiElement(xtplToken.NODE_OR_ATTR).withLanguage(xtplLang.INSTANCE),
                new CompletionProvider<CompletionParameters>() {
                    public void addCompletions(@NotNull CompletionParameters parameters,
                                               ProcessingContext context,
                                               @NotNull CompletionResultSet resultSet) {

                        this.addItems(resultSet, events, xtplIcons.EVENT, 40);
                        this.addItems(resultSet, attrs, xtplIcons.ATTR, 30);
                        this.addItems(resultSet, systemTags, xtplIcons.TAG, 20);
                        this.addItems(resultSet, htmlTags, null, 10);
                    }

                    private void addItems(CompletionResultSet resultSet, String[] tags, Icon icon, int priority) {
                        for( int i = 0; i < tags.length; i++ ){
                            String name = tags[i];

                            LookupElement element = LookupElementBuilder.create("xtpl", name)
                                    .withPresentableText(name)
                                    .withIcon(icon)
                            ;

                            resultSet.addElement(PrioritizedLookupElement.withPriority(element, priority));
                        }
                    }
                }
        );
    }
}