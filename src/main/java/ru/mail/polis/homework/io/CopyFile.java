package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.NoSuchElementException;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException, IllegalArgumentException {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (Files.isRegularFile(from)) {
            if (!Files.exists(to)) {
                Files.createDirectories(to.getParent());
            }
            Files.createFile(to);
            copyFile(from, to);
            return;
        }
        walkTreeImplementation(from, to);
    }

    private static void walkTreeImplementation(Path from, Path to) throws IOException {
        if (!Files.exists(from)) {
            return;
        }

        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path destination = buildNewPathFromExisted(to, from, dir);
                if (!Files.exists(destination)) {
                    Files.createDirectories(destination);
                }
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path destination = buildNewPathFromExisted(to, from, file);
                if (!Files.exists(destination.getParent())) {
                    Files.createDirectories(destination.getParent());
                }
                Files.createFile(destination);
                copyFile(file, destination);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(to);
             BufferedReader reader = Files.newBufferedReader(from)) {
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
        }
    }

    private static Path buildNewPathFromExisted(Path to, Path from, Path notExisted) {
        return to.resolve(from.relativize(notExisted));
    }


}
