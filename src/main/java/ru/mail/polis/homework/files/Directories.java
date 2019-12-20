package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
         File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            file.delete();
            return 1;
        } else {
            int counter = 1;
            for (File infile : Objects.requireNonNull(file.listFiles())) {
                counter += removeWithFile(infile.getAbsolutePath());
            }
            file.delete();
            return counter;
        }
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);

        if (!Files.exists(file)) {
            return 0;
        } else {
            if (Files.isRegularFile(file)) {
                Files.delete(file);
                return 1;
            }
            AtomicInteger counter = new AtomicInteger(1);
            try (Stream<Path> paths = Files.list(file)) {
                paths.forEach(nextPath -> {
                    try {
                        counter.addAndGet(removeWithPath(nextPath.toString()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return counter.get();
        }
    }   
}
