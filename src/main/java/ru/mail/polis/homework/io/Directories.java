package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int countFiles = 0;
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File child : files) {
                    countFiles += removeWithFile(child.getPath());
                }
            }
            file.delete();
            countFiles++;
        }
        return countFiles;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            return 0;
        }
        int countFiles = 0;
        if (!Files.isDirectory(directory)) {
            Files.delete(directory);
            return 1;
        }
        if (Files.isDirectory(directory)) {
            try (var pathStream = Files.list(directory)) {
                countFiles += pathStream
                        .mapToInt(p -> {
                            try {
                                return removeWithPath(p.toString());
                            } catch (IOException e) {
                                return 0;
                            }
                        })
                        .sum();
            }
            Files.delete(directory);
            countFiles++;
        }
        return countFiles;
    }
}
