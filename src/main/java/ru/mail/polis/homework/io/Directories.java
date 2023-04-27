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
    public static int removeWithFile(String path) throws IOException {
        File newFile = new File(path);
        if (!newFile.exists()) {
            return 0;
        }
        if (newFile.isFile()) {
            return newFile.delete() ? 1 : 0;
        }
        int deletedFiles = 1;
        for (File file : Objects.requireNonNull(newFile.listFiles())) {
            deletedFiles += removeWithFile(file.getAbsolutePath());
        }
        Files.delete(newFile.toPath());
        return deletedFiles;
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
        if (Files.isRegularFile(directory)) {
            Files.delete(directory);
            return 1;
        }
        int deletedFiles = 1;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path file : directoryStream) {
                deletedFiles += removeWithPath(file.toString());
            }
        }
        Files.delete(directory);
        return deletedFiles;
    }
}
