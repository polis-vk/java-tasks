package ru.mail.polis.homework.io;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;
import java.io.IOException;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File currentPath = new File(path);
        if (currentPath == null || !currentPath.exists()) {
            return 0;
        }

        if (currentPath.isFile()) {
            currentPath.delete();
            return 1;
        }

        int count = 0;
        for (File temp : currentPath.listFiles()) {
            count += removeWithFile(String.valueOf(temp));
        }
        currentPath.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path currentPath = Paths.get(path);
        if (Files.notExists(currentPath)) {
            return 0;
        }

        AtomicInteger count = new AtomicInteger();
        Files.walkFileTree(currentPath, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return count.get();
    }
}
