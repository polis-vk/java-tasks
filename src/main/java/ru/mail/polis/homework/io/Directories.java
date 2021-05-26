package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
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
        int counter = 1;
        for (File subFile : file.listFiles()) {
            counter += removeWithFile(subFile.getAbsolutePath());
        }
        file.delete();
        return counter;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        AtomicInteger counter = new AtomicInteger();

        if(!Files.exists(Paths.get(path))) {
            return 0;
        }

        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Objects.requireNonNull(dir);
                if (exc != null) {
                    throw exc;
                } else {
                    counter.incrementAndGet();
                    Files.delete(dir);
                    return FileVisitResult.CONTINUE;
                }
            }

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Objects.requireNonNull(file);
                Objects.requireNonNull(attrs);
                counter.incrementAndGet();
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

        });
        return counter.get();
    }
}
