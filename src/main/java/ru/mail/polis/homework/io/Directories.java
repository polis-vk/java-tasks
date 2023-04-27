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
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            return file.delete() ? 1 : 0;
        }
        int count = 0;
        File[] files = file.listFiles();
        if (files != null) {
            for (File fileToDelete : files) {
                count += removeWithFile(fileToDelete.getPath());
            }
        }
        return file.delete() ? count + 1 : count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path myPath = Paths.get(path);
        if (!Files.exists(myPath)) {
            return 0;
        }
        if (Files.isRegularFile(myPath)) {
            Files.delete(myPath);
            return 1;
        }
        AtomicInteger count = new AtomicInteger();
        Files.walkFileTree(myPath, new SimpleFileVisitor<>(){
            @Override
            public FileVisitResult visitFile(Path myPath, BasicFileAttributes attrs) throws IOException {
                Files.delete(myPath);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path myPath, IOException exc) throws IOException {
                Files.delete(myPath);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

        });
        return count.get();
    }
}
