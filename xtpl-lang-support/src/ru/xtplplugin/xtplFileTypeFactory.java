package ru.xtplplugin;

import com.intellij.openapi.fileTypes.*;
import org.jetbrains.annotations.NotNull;

public class xtplFileTypeFactory extends FileTypeFactory {
    @Override
    public void createFileTypes(@NotNull FileTypeConsumer fileTypeConsumer) {
        fileTypeConsumer.consume(xtplFileType.INSTANCE, "xtpl");
    }
}