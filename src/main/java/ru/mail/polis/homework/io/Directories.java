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
        File files = new File(path);
        if (!files.exists()) {
            return 0;
        } else if (files.isFile()) {
            return files.delete() ? 1 : 0;
        }

        int counter = 0;
        for (File file : files.listFiles()) {
            counter += removeWithFile(file.getPath());
        }
        return files.delete() ? ++counter : counter;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (Files.notExists(filePath)) {
            return 0;
        }
        if (Files.isRegularFile(filePath)) {
            Files.delete(filePath);
            return 1;
        }

        int counter = 0;
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(filePath)) {
            for (Path pathFile : dirStream) {
                counter += removeWithPath(pathFile.toString());
            }
        }
        Files.delete(filePath);
        return ++counter;
    }
}
