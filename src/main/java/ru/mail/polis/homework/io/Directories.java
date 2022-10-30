package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
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
        File currPath = new File(path);
        if (!currPath.exists()) {
            return 0;
        }

        int deletedCount = 0;
        if (currPath.isDirectory()) {
            File[] innerFiles = currPath.listFiles();
            if (innerFiles != null) {
                for (File file : innerFiles) {
                    deletedCount += removeWithFile(file.toString());
                }
            }
        }
        currPath.delete();
        return ++deletedCount;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path currPath = Paths.get(path);
        if (Files.notExists(currPath)) {
            return 0;
        }

        AtomicInteger deletedCount = new AtomicInteger();
        Files.walkFileTree(currPath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                deletedCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                deletedCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

        });
        return deletedCount.get();
    }
}
