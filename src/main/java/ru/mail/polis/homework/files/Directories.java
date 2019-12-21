package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
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
        }
        int count = 0;
        for (File subFile : file.listFiles()) {
            count += removeWithFile(subFile.getAbsolutePath());
        }
        file.delete();
        return count + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);
        if (!Files.exists(file)) {
            return 0;
        }
        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return 1;
        }

        AtomicInteger count = new AtomicInteger(0);
        AtomicReference<IOException> ioException = new AtomicReference<>();
        try (Stream<Path> paths = Files.list(file)) {
            paths.forEach(tempPath -> {
                try {
                    count.addAndGet(removeWithPath(tempPath.toString()));
                } catch (IOException e) {
                    ioException.set(e);
                }
            });
            if (ioException.get() != null) {
                throw ioException.get();
            }
        }
        Files.delete(file);
        return count.get() + 1;
    }
}
