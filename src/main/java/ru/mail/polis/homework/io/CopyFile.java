package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }

        try {
            Path to = Paths.get(pathTo);
            Path dir = !Files.isDirectory(to) ? to.getParent() : to;
            Files.createDirectories(dir);
            copyInternalFiles(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void copyInternalFiles(Path pathFrom, Path pathTo) throws IOException {
        Files.walkFileTree(pathFrom, new SimpleFileVisitor<Path>() {
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

            private void copy(Path pathFrom, Path pathTo) throws IOException {
                try (InputStream input = Files.newInputStream(pathFrom)) {
                    try (OutputStream output = Files.newOutputStream(pathTo)) {
                        byte[] buffer = new byte[1024];
                        int bytesNumber;
                        while ((bytesNumber = input.read(buffer)) > 0) {
                            output.write(buffer, 0, bytesNumber);
                        }
                    }
                }
            }
        });
    }
}

