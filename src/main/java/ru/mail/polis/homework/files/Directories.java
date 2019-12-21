package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int deleted = 0;
        File[] subFiles = file.listFiles();
        if (subFiles != null) {
            for (File subFile : subFiles) {
                if (subFile.isDirectory()) {
                    deleted += removeWithFile(subFile.getAbsolutePath());
                } else {
                    subFile.delete();
                    deleted++;
                }
            }
        }
        file.delete();
        return ++deleted;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            return 0;
        }
        try {
            if (Files.isRegularFile(filePath)) {
                Files.delete(filePath);
                return 1;
            }
            final int[] deleted = new int[1];
            try (Stream<Path> pathStream = Files.list(filePath)) {
                pathStream
                    .forEach(subPath -> {
                        deleted[0] += removeWithPath(subPath.toString());
                    });
                Files.setAttribute(filePath, "dos:readonly", false);
                Files.delete(filePath);
                return ++deleted[0];
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
