package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    private static final int BUFFER_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            throw new NullPointerException();
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        try {
            Path to = Paths.get(pathTo);
            copyingFileVisitor(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyingFileVisitor(Path from, Path to) throws IOException {
        Path directory = Files.isDirectory(to) ? to : to.getParent();
        if (Files.notExists(directory)) {
            Files.createDirectories(directory);
        }

        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path pathForCopy = to.resolve(from.relativize(dir));
                if (Files.notExists(pathForCopy)) {
                    Files.createDirectory(pathForCopy);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copy(file, to.resolve(from.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from)) {
            try (OutputStream outputStream = Files.newOutputStream(to)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int read;
                while ((read = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, read);
                }
            }
        }
    }

}
