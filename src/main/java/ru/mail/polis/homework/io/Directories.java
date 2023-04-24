package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File rootDir = new File(path);
        if (!rootDir.exists()) {
            return 0;
        }
        int counterFile = 1;
        if (rootDir.isFile()) {
            rootDir.delete();
            return counterFile;
        }
        for (File currentFile : rootDir.listFiles()) {
            if (currentFile.isDirectory()) {
                counterFile += removeWithFile(currentFile.getPath());
            } else {
                currentFile.delete();
                counterFile++;
            }
        }
        rootDir.delete();
        return counterFile;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path rootDir = Paths.get(path);
        if (Files.notExists(rootDir)) {
            return 0;
        }
        int counterPath = 1;
        if (Files.isRegularFile(rootDir)) {
            Files.delete(rootDir);
            return counterPath;
        }
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(rootDir)) {
            for (Path currentFile : dirStream) {
                if (Files.isDirectory(currentFile)) {
                    counterPath += removeWithPath(currentFile.toString());
                }
                if (Files.exists(currentFile)) {
                    Files.delete(currentFile);
                    counterPath++;
                }
            }
        }
        Files.delete(rootDir);
        return counterPath;
    }
}