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
                file.delete();
                count++;
            } else {
                file.delete();
                count++;
            }
        }
        return count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        //Костыль...
        //Подскажите: как исправить?
        final int[] count = {0};
        Path file = Paths.get(path);
        Files.walkFileTree(file, new FileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                count[0]++;
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                count[0]++;
                return FileVisitResult.CONTINUE;
            }
        });
        return count[0];
    }
}