package ru.xtplplugin;

import com.intellij.openapi.application.ApplicationManager;
import com.intellij.openapi.components.ApplicationComponent;
import com.intellij.openapi.fileTypes.FileTypeManager;
import org.jetbrains.annotations.NotNull;


public class xtplLoader implements ApplicationComponent {

    public void initComponent() {
        ApplicationManager.getApplication().runWriteAction(
                new Runnable() {
                    public void run() {
                        FileTypeManager.getInstance().registerFileType(xtplFileType.INSTANCE, "xtpl");
                    }
                }
        );
    }

    public void disposeComponent() {
    }

    @NotNull
    public String getComponentName() {
        return "xtpl.support.loader";
    }

}
