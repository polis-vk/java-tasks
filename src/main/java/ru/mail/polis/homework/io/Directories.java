package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
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
        int count = 0;
        if (!file.exists()) {
            return 0;
        }
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                count += removeWithFile(f.getPath());
            }
        }
        if (file.delete()) {
            count++;
        }
        return count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path path1 = Paths.get(path);
        AtomicInteger count = new AtomicInteger(0);
        if (!Files.exists(path1)) {
            return 0;
        }

        Files.walkFileTree(path1, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                try {
                    Files.delete(file);
                    count.incrementAndGet();
                } catch (IOException ignored) {
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                try {
                    Files.delete(dir);
                    count.incrementAndGet();
                } catch (IOException ignored) {
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return count.get();
    }
}
