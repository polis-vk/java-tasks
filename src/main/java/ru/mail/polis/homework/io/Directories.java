package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        File src = new File(path);
        if (!src.exists()) {
            return 0;
        }

        int deletedCounter = 0;
        if (src.isDirectory()) {
            for (File file : Objects.requireNonNull(src.listFiles())) {
                deletedCounter += removeWithFile(file.toString());
            }
        }

        if (src.delete()) {
            ++deletedCounter;
        }
        return deletedCounter;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path src = Paths.get(path);
        if (Files.notExists(src)) {
            return 0;
        }

        int deletedCounter = 0;
        if (Files.isDirectory(src)) {
            try (Stream<Path> pathStream = Files.list(src)) {
                List<Path> directoryPaths = pathStream.collect(Collectors.toList());
                for (Path directoryPath : directoryPaths) {
                    deletedCounter += removeWithPath(directoryPath.toString());
                }
            }
        }
        Files.delete(src);
        return ++deletedCounter;
    }
}
