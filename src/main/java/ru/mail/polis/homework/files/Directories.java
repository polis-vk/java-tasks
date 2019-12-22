package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
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
            AtomicInteger counter = new AtomicInteger(1);
            Arrays.stream(Objects.requireNonNull(file.listFiles()))
                    .forEach(tmpFile -> {
                        counter.addAndGet(removeWithFile(tmpFile.getAbsolutePath()));
                    });
            file.delete();
            return counter.get();
        }
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);
        AtomicInteger counter = new AtomicInteger(1);
        if (!Files.exists(file)) {
            return 0;
        }
        try (Stream<Path> paths = Files.list(file)) {
            paths.forEach(tmpFile -> {
                try {
                    counter.addAndGet(removeWithPath(tmpFile.toString()));
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        Files.delete(file);
        return counter.get();
    }
}
