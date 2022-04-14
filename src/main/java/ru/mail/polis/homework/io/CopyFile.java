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
    public static String copyFiles(String pathFrom, String pathTo) {
        if (Files.notExists(Paths.get(pathFrom))) {
            return null;
        }

        try {
            Path to = Paths.get(pathTo);
            if (!Files.isDirectory(to)) {
                to = to.getParent();
            }
            Files.createDirectories(to);
            copyInternalFiles(Paths.get(pathFrom), Paths.get(pathTo));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from);
             OutputStream outputStream = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
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
