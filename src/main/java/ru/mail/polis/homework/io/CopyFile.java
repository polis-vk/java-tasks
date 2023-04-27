package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */

    public static void copyFiles(String pathFrom, String pathTo) {
        Path source = Paths.get(pathFrom);
        Path destination = Paths.get(pathTo);
        if (Files.notExists(source)) {
            return;
        }
        try {
            Files.walk(source)
                    .forEach(sourcePath -> {
                        Path destinationPath = destination.resolve(source.relativize(sourcePath));
                        try {
                            if (Files.isDirectory(sourcePath)) {
                                Files.createDirectories(destinationPath);
                            } else {
                                Files.createDirectories(destinationPath.getParent());
                                try (InputStream inputStream = Files.newInputStream(sourcePath);
                                     OutputStream outputStream = Files.newOutputStream(destinationPath)) {
                                    byte[] buffer = new byte[1024];
                                    int bytesRead;
                                    while ((bytesRead = inputStream.read(buffer)) > 0) {
                                        outputStream.write(buffer, 0, bytesRead);
                                    }
                                }
                            }
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}

