package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        int count = 0;
        File files = new File(path);
        if (files.isDirectory()) {
            count = recursiveDeleteWithFile(files, count);
            files.delete();
            return ++count;
        } else if (files.delete()) {
            files.delete();
            count++;
        }
        return count;
    }

    private static int recursiveDeleteWithFile(File files, int count) {
        for (File file : files.listFiles()) {
            if (file.isDirectory()) {
                count = recursiveDeleteWithFile(file, count);
            }
            file.delete();
            count++;
        }
        return count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);
        Visitor v = new Visitor();
        try {
            Files.walkFileTree(file, v);
        } catch (NoSuchFileException e) {
            return 0;
        }
        return v.count;
    }

    static class Visitor extends SimpleFileVisitor<Path> {
        int count = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            count++;
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            count++;
            return FileVisitResult.CONTINUE;
        }
    }
}