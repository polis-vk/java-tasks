package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
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
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int count = 0;
        if (file.isDirectory()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                count += removeWithFile(f.getPath());
            }
        }
        return file.delete() ? ++count : count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (Files.notExists(filePath)) {
            return 0;
        }
        int count = 0;
        try (Stream<Path> pathStream = Files.walk(filePath)) {
            List<Path> paths = pathStream
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            for (Path currentPath : paths) {
                Files.delete(currentPath);
                count++;
            }
        }
        return count;
    }
}
