package ru.mail.polis.homework.io;


import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;


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

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from)) {
            try (OutputStream outputStream = Files.newOutputStream(to)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }

    private static void copyInternalFiles(Path pathFrom, Path pathTo) throws IOException {
        Files.walkFileTree(Paths.get(String.valueOf(pathFrom)), new SimpleFileVisitor<>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copy(file, pathTo.resolve(pathFrom.relativize(file)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectories(pathTo.resolve(pathFrom.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
