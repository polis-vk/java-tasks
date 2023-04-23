package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

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
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int count = 0;
        for (File tempFile : file.listFiles()) {
            count += removeWithFile(tempFile.getPath());
        }
        file.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (!Files.exists(dir)) {
            return 0;
        }
        AtomicInteger count = new AtomicInteger();
        if (Files.isDirectory(dir)) {
            Files.list(dir)
                    .collect(Collectors.toList())
                    .forEach(f -> {
                        try {
                            count.addAndGet(removeWithPath(f.toString()));
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    });
        }
        Files.delete(dir);
        return count.addAndGet(1);
    }
}
