package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File toRemove = new File(path);
        if (!toRemove.exists()) {
            return 0;
        }
        if (toRemove.isFile()) {
            toRemove.delete();
            return 1;
        }
        int count = 0;
        for (File file : toRemove.listFiles()) {
            if (file.isFile()) {
                file.delete();
                count++;
            } else {
                count += removeWithFile(file.toString());
            }
        }
        toRemove.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path pathToRemove = Paths.get(path);
        if (Files.notExists(pathToRemove)) {
            return 0;
        }
        if (Files.isRegularFile(pathToRemove)) {
            Files.delete(pathToRemove);
            return 1;
        }
        int count = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(pathToRemove)) {
            for (Path currentPath : directoryStream) {
                if (Files.isRegularFile(currentPath)) {
                    Files.delete(currentPath);
                    count++;
                } else {
                    count += (removeWithPath(currentPath.toString()));
                }
            }
        }
        Files.delete(pathToRemove);
        return ++count;
    }
}
