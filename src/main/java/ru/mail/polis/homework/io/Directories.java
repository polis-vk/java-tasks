package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
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

        int filesDeleted = 0;
        if (file.isFile()) {
            if (!file.delete()) {
                throw new RuntimeException();
            }
            filesDeleted = 1;
        } else {
            for (File childFile : file.listFiles()) {
                filesDeleted += removeWithFile(childFile.getPath());
            }

            if (!file.delete()) {
                throw new RuntimeException();
            }
            filesDeleted += 1;
        }

        return filesDeleted;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (!Files.exists(dir) || !Files.exists(dir)) {
            return 0;
        }

        if (!Files.isDirectory(dir)) {
            Files.delete(dir);
            return 1;
        }

        int filesDeleted = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                filesDeleted += removeWithPath(entry.toString());
            }
        }

        Files.delete(dir);
        filesDeleted += 1;
        return filesDeleted;
    }
}
