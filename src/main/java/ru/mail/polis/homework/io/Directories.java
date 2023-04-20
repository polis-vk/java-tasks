package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.List;
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
        } else if (file.isFile()) {
            return file.delete() ? 1 : 0;
        } else {
            int count = 0;
            File[] files = file.listFiles();
            if (files != null) {
                for (File fileToDelete : files) {
                    count += removeWithFile(fileToDelete.getPath());
                }
            }
            return file.delete() ? count + 1 : 1;
        }
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path myPath = Paths.get(path);
        if (!Files.exists(myPath)) {
            return 0;
        }
        int count = 0;
        try (Stream<Path> walk = Files.walk(myPath)) {
            List<Path> list = walk
                    .sorted(Comparator.reverseOrder())
                    .collect(Collectors.toList());
            for (Path p : list) {
                Files.delete(p);
                count++;
            }
        }
        return count;
    }
}
