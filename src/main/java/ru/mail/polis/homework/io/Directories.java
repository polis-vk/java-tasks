package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File source = new File(path);
        if (!source.exists()) {
            return 0;
        }

        int deletionCounter = 0;

        if (source.isDirectory()) {
            for (File file : Objects.requireNonNull(source.listFiles())) {
                deletionCounter += removeWithFile(file.toString());
            }
        }
        if (source.delete()) {
            deletionCounter++;
        }
        return deletionCounter;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path source = Paths.get(path);
        if (Files.notExists(source)) {
            return 0;
        }

        AtomicInteger deletionCounter = new AtomicInteger(0);
        Files.walkFileTree(source, new PathSimpleFileVisitor(deletionCounter));
        return deletionCounter.get();
    }

    static class PathSimpleFileVisitor extends SimpleFileVisitor<Path> {

        private final AtomicInteger deletionCounter;

        public PathSimpleFileVisitor(AtomicInteger deletionCounter) {
            this.deletionCounter = deletionCounter;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            deletionCounter.incrementAndGet();
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            deletionCounter.incrementAndGet();
            return FileVisitResult.CONTINUE;
        }

    }

}
