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
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File files = new File(path);
        int count = 0;
        if (!files.exists()) {
            return count;
        }

        if (files.isFile()) {
            return files.delete() ? 1 : 0;
        }

        for (File tmp : Objects.requireNonNull(files.listFiles())) {
            count += removeWithFile(tmp.getPath());
        }
        return files.delete() ? count + 1 : count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        int count = 0;
        if (!Files.exists(directory)) {
            return count;
        }

        if (Files.isRegularFile(directory)) {
            Files.delete(directory);
            return ++count;
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(directory)) {
            for (Path pathFile : directoryStream) {
                count += removeWithPath(pathFile.toString());
            }
        }

        Files.delete(directory);
        return ++count;
    }
}
