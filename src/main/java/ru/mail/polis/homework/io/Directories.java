package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            file.delete();
            return 1;
        }

        int filesCount = 0;
        if (file.isDirectory()) {
            for (File currentFile : file.listFiles()) {
                filesCount += removeWithFile(currentFile.getPath());
            }
        }
        file.delete();
        return filesCount + 1;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path currentPath = Paths.get(path);
        if (!Files.exists(currentPath)) {
            return 0;
        }

        if (Files.isRegularFile(currentPath)) {
            Files.delete(currentPath);
            return 1;
        }

        int filesCount = 0;
        if (Files.isDirectory(currentPath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(currentPath)) {
                for (Path tempDir : directoryStream) {
                    filesCount += removeWithPath(tempDir.toString());
                }
            }
        }
        Files.delete(currentPath);
        return filesCount + 1;
    }
}
