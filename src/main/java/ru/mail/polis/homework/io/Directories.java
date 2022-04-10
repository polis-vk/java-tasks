package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
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
        File files = new File(path);

        if (!files.exists()) {
            return 0;
        }

        if (files.isFile()) {
            return files.delete() ? 1 : 0;
        }

        int count = 0;
        for (File file : files.listFiles()) {
            count += removeWithFile(file.toString());
        }

        return files.delete() ? ++count : count;
    }


    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);

        if (Files.notExists(dir)) {
            return 0;
        }

        if (Files.isRegularFile(dir)) {
            Files.delete(dir);
            return 1;
        }

        int count = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(dir)) {
            for (Path incoming : directoryStream) {
                count += removeWithPath(incoming.toString());
            }
        }

        Files.delete(dir);
        return ++count;
    }
}
