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
        File dir = new File(path);
        if (!dir.exists()) {
            return 0;
        }

        int count = 0;
        if (dir.isDirectory()) {
            for (File file : Objects.requireNonNull(dir.listFiles(Objects::nonNull))) {
                count += removeWithFile(file.toString());
            }
        }
        dir.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (Files.notExists(dir)) {
            return 0;
        }

        int count = 0;
        if (Files.isDirectory(dir)) {
            List<Path> paths;
            try (Stream<Path> stream = Files.list(dir)) {
                paths = stream.collect(Collectors.toList());
            }
            for (Path file : paths) {
                count += removeWithFile(file.toString());
            }
        }
        Files.delete(dir);
        return ++count;
    }
}
