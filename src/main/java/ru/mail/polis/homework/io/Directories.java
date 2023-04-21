package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        int countFiles = 1;

        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        if (file.list() == null) {
            file.delete();
            countFiles += removeWithFile(file.getPath());
        } else {
            for (File x : Objects.requireNonNull(file.listFiles())) {
                countFiles += removeWithFile(x.getPath());
                if (x.list() == null) {
                    x.delete();
                }
            }
        }
        file.delete();
        return countFiles;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static void options(File file) {

    }

    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);
        if (!Files.exists(file)) {
            return 0;
        }
        final int[] countFile = {0};

        Files.walkFileTree(file, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(final Path file, final BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                countFile[0] += 1;
                return super.visitFile(file, attrs);
            }

            @Override
            public FileVisitResult postVisitDirectory(final Path dir, final IOException exc) throws IOException {
                Files.delete(dir);
                countFile[0] += 1;
                return super.postVisitDirectory(dir, exc);
            }
        });
        return countFile[0];

    }
}
