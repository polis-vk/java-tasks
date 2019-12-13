package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        return removeWithFile(new File(path));

    }

    private static int removeWithFile(File file) {
        int res = 0;
        if (file.isDirectory()) {
            File[] listOfFiles = file.listFiles();
            if (listOfFiles != null) {
                for (File f : listOfFiles) {
                    res += removeWithFile(f.getPath());
                }
            }
        }
        if (file.delete()) {
            res += 1;
        } else {
            System.out.println("Не удалось удалить файл " + file.toString());
        }
        return res;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        AtomicInteger count = new AtomicInteger(0);
        Files.walkFileTree(directory, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
                Files.delete(path);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path path, IOException e) {
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path path, IOException e) throws IOException {
                Files.delete(path);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return count.get();
    }
}
