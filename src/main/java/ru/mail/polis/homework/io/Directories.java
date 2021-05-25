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
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        return removeFile(file);
    }

    private static int removeFile(File file) {
        if (file == null || !file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            file.delete();
            return 1;
        }

        int count = 0;
        for (File temp : file.listFiles()) {
            count += removeFile(temp);
        }
        file.delete();

        return count + 1;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) {
        Path filePath = Paths.get(path);
        if (!Files.exists(filePath)) {
            return 0;
        }

        AtomicInteger counter = new AtomicInteger();

        SimpleFileVisitor<Path> visitor = new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                counter.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                counter.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        };

        try {
            Files.walkFileTree(filePath, visitor);
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return counter.get();
    }
}
