package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
            if (file.delete()) {
                return 1;
            }
            return 0;
        }

        int countDeletedFiles = 0;
        for (File f : file.listFiles()) {
            countDeletedFiles += removeWithFile(f.getAbsolutePath());
        }

        file.delete();
        return countDeletedFiles + 1;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);

        if (!Files.exists(directory)) {
            return 0;
        }

        final AtomicInteger count = new AtomicInteger();
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc == null) {
                    Files.delete(dir);
                    count.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                } else {
                    throw exc;
                }
            }
        });
        return count.get();
    }
}
