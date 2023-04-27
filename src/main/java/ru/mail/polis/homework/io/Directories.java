package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {

        int count = 0;
        File directory = new File(path);
        if (!directory.exists()) {
            return count;
        }

        if (directory.isDirectory()) {
            for (File file : Objects.requireNonNull(directory.listFiles(Objects::nonNull))) {
                count += removeWithFile(file.toString());
            }
        }

        directory.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {

        int count = 0;
        Path directory = Paths.get(path);
        if (Files.notExists(directory)) {
            return count;
        }

        if (Files.isDirectory(directory)) {
            try (Stream<Path> pathStream = Files.list(directory)) {
                List<Path> directoryPaths = pathStream.collect(Collectors.toList());
                for (Path directoryPath : directoryPaths) {
                    count += removeWithPath(directoryPath.toString());
                }
            }
        }
        Files.delete(directory);
        return ++count;
    }
}
