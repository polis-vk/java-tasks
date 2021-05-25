package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.List;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File currentFile = new File(path);
        if (!currentFile.exists()) {
            return 0;
        }
        if (currentFile.isFile()) {
            currentFile.delete();
            return 1;
        }
        int counter = 0;
        for (File childFile : currentFile.listFiles()) {
            if (childFile.isDirectory()) {
                counter += removeWithFile(childFile.getPath());
            } else {
                childFile.delete();
                counter++;
            }
        }
        currentFile.delete();
        return counter + 1;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path removingPath = Paths.get(path);
        if (Files.notExists(removingPath)) {
            return 0;
        }
        int deletedCounter = 0;
        try (Stream<Path> pathStream = Files.walk(removingPath)) {
            List<Path> pathList = pathStream
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            for (Path currentPath : pathList) {
                Files.delete(currentPath);
                deletedCounter++;
            }
        }
        return deletedCounter;
    }
}
