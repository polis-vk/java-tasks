package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int deletedFiles = 0;
        if (file.isDirectory()) {
            for (File tempFile : file.listFiles()) {
                deletedFiles += removeWithFile(tempFile.getPath());
            }
        }
        file.delete();
        return ++deletedFiles;
    }
    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);

        if (!Files.exists(file)) {
            return 0;
        }
        AtomicInteger deletedFiles = new AtomicInteger();
        Files.walkFileTree(file, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                deletedFiles.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                deletedFiles.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return deletedFiles.get();
    }
}
