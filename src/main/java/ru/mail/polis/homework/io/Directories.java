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
        int deletedFileCounter = 0;
        return removeAllContentWithFile(new File(path), deletedFileCounter);
    }

    private static int removeAllContentWithFile(File directory, int deletedFileCounter) {
        if (!directory.exists()) {
            return deletedFileCounter;
        }
        if (directory.delete()) {
            deletedFileCounter++;
            return deletedFileCounter;
        }
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.delete()) {
                deletedFileCounter++;
            } else {
                deletedFileCounter = removeAllContentWithFile(file, deletedFileCounter);
            }
        }
        if (directory.delete()) {
            deletedFileCounter++;
        }
        return deletedFileCounter;
    }


    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        int deletedFileCounter = 0;
        return removeAllContentWithPath(Paths.get(path), deletedFileCounter);
    }

    private static int removeAllContentWithPath(Path directory, int deletedFileCounter) throws IOException {
        if (!Files.exists(directory)) {
            return deletedFileCounter;
        }
        if (Files.isRegularFile(directory)) {
            deletedFileCounter++;
            Files.delete(directory);
            return deletedFileCounter;
        }
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(directory)) {
            for (Path path : paths) {
                if (Files.isRegularFile(path)) {
                    deletedFileCounter++;
                    Files.delete(path);
                } else {
                    deletedFileCounter = removeAllContentWithPath(path, deletedFileCounter);
                }
            }
        }
        deletedFileCounter++;
        Files.delete(directory);
        return deletedFileCounter;
    }

}
