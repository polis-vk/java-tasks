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

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path wayFrom = Paths.get(pathFrom);
        if (Files.notExists(wayFrom)) {
            return null;
        }
        Path wayTo = Paths.get(pathTo);
        try {
            if (!Files.isDirectory(wayFrom)) {
                wayTo = wayTo.getParent();
            }
            Files.createDirectories(wayTo);
            fileCopy(pathFrom, pathTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copy(Path wayFrom, Path wayTo) throws IOException {
        try (InputStream inStream = Files.newInputStream(wayFrom)) {
            try (OutputStream outStream = Files.newOutputStream(wayTo)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inStream.read(buffer)) > 0) {
                    outStream.write(buffer, 0, length);
                }
            }
        }
    }

    private static void fileCopy(String wayFrom, String wayTo) throws IOException {
        Path pathFrom = Paths.get(wayFrom);
        Path pathTo = Paths.get(wayTo);
        Files.walkFileTree(Paths.get(wayFrom), new SimpleFileVisitor<Path>() {
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
