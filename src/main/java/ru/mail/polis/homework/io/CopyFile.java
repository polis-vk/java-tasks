package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.security.InvalidParameterException;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            throw new InvalidParameterException();
        }
        final Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        final Path to = Paths.get(pathTo);
        final Path parent = Files.isDirectory(to) ? to : to.getParent();
        try {
            Files.createDirectories(parent);
            copyFolder(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFolder(Path pathFrom, Path pathTo) throws IOException {
        Files.walkFileTree(pathFrom, new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path dirPath = pathTo.resolve(pathFrom.relativize(dir));
                if (Files.notExists(dirPath)) {
                    Files.createDirectory(dirPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copy(file, pathTo.resolve(pathFrom.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copy(Path from, Path to) throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(from)));
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(to)))) {
            while (input.ready()) {
                output.write(input.readLine());
            }
        }
    }
}
