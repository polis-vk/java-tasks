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
        File currentDir = new File(path);
        if (!currentDir.exists()) {
            return 0;
        }
        if (currentDir.isFile()) {
            currentDir.delete();
            return 1;
        }
        int count = 0;
        for (File file : currentDir.listFiles()) {
            if (file.isFile()) {
                file.delete();
                count++;
            } else {
                count += removeWithFile(file.toString());
            }
        }
        currentDir.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path currentDir = Paths.get(path);
        if (Files.notExists(currentDir)) {
            return 0;
        }
        if (Files.isRegularFile(currentDir)) {
            Files.delete(currentDir);
            return 1;
        }
        int count = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(currentDir)) {
            for (Path file : directoryStream) {
                if (Files.isRegularFile(file)) {
                    Files.delete(file);
                    count++;
                } else {
                    count += removeWithPath(file.toString());
                }
            }
        }
        Files.delete(currentDir);
        return ++count;
    }
}
