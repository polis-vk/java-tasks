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
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            file.delete();
            return 1;
        }

        int countDeletedFiles = 0;
        for (File f : Objects.requireNonNull(file.listFiles())) {
            countDeletedFiles += removeWithFile(f.getAbsolutePath());
        }

        file.delete();
        return countDeletedFiles + 1;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);

        if (!Files.exists(dir)) {
            return 0;
        }

        if (Files.isRegularFile(dir)) {
            Files.delete(dir);
            return 1;
        }

        int countDeletedFiles = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path file : stream) {
                countDeletedFiles += removeWithPath(file.toString());
            }
        }

        Files.delete(dir);
        return countDeletedFiles + 1;
    }
}
