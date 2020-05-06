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

        File directory = new File(path);
        int count = 0;
        if (!directory.exists()) {
            return count;
        }
        if (directory.isFile()) {
            return deleteFile(directory, 0);
        }

        for (File file : directory.listFiles()) {
            count += removeWithFile(file.toString());
        }
        return deleteFile(directory, count);
    }

    private static int deleteFile(File file, int count) {
        return file.delete() ? count + 1 : count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path directoryPath = Paths.get(path);
        if (Files.notExists(directoryPath)) {
            return 0;
        }
        if (Files.isRegularFile(directoryPath)) {
            Files.delete(directoryPath);
            return 1;
        }
        int count = 0;
        try (DirectoryStream<Path> entries = Files.newDirectoryStream(directoryPath)) {
            for (Path entry : entries) {
                count += removeWithPath(entry.toString());
            }
        }
        Files.delete(directoryPath);
        return count + 1;
    }
}
