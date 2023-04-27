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
        File source = new File(path);
        if (!source.exists()) {
            return 0;
        }

        int deletionCounter = 0;

        if (source.isDirectory()) {
            for (File file : Objects.requireNonNull(source.listFiles())) {
                deletionCounter += removeWithFile(file.toString());
            }
        }
        if (source.delete()) {
            deletionCounter++;
        }
        return deletionCounter;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path source = Paths.get(path);
        if (Files.notExists(source)) {
            return 0;
        }

        int deletionCounter = 0;

        if (Files.isDirectory(source)) {
            try (Stream<Path> pathStream = Files.list(source)) {
                List<Path> directoryPaths = pathStream.collect(Collectors.toList());
                for (Path directoryPath : directoryPaths) {
                    deletionCounter += removeWithPath(directoryPath.toString());
                }
            }
        }
        Files.delete(source);
        deletionCounter++;
        return deletionCounter;
    }

}
