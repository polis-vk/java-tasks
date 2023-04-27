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
        File currentFile = new File(path);
        if (!currentFile.exists()) {
            return 0;
        }
        int result = 0;
        if (currentFile.isDirectory()) {
            File[] files = currentFile.listFiles();
            if (files == null) {
                throw new RuntimeException();
            }
            for (File temp : files) {
                result += removeWithFile(temp.toString());
            }
        }
        if (!currentFile.delete()) {
            throw new RuntimeException("file deletion error");
        }
        return ++result;
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
        AtomicInteger result = new AtomicInteger();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                result.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException e) throws IOException {
                Files.delete(dir);
                result.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return result.get();
    }
}
