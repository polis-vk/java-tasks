package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
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
        int countRemoved = 0;
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            countRemoved++;
            file.delete();
            return countRemoved;
        }
        File[] listFiles = file.listFiles();

        for (File insideFile : listFiles) {

            countRemoved += removeWithFile(insideFile.toString());

        }

        countRemoved++;
        file.delete();
        return countRemoved;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        AtomicInteger countRemoved = new AtomicInteger();
        if (!Files.exists(Paths.get(path))) {
            return 0;
        }
        Files.walkFileTree(Paths.get(path), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                countRemoved.incrementAndGet();
                Files.delete(file);
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                countRemoved.incrementAndGet();
                Files.delete(dir);
                return FileVisitResult.CONTINUE;
            }

        });
        return countRemoved.get();
    }

}

