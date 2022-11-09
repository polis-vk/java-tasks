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

    public static final int BUFFER_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path dirFrom = Paths.get(pathFrom);
        if (Files.notExists(dirFrom)) {
            return;
        }
        Path dirTo = Paths.get(pathTo);

        try {
            if (Files.notExists(dirTo.getParent())) {
                Files.createDirectories(dirTo.getParent());
            }
            if (Files.isRegularFile(dirFrom)) {
                copyFile(dirFrom, dirTo);
                return;
            }
            Files.walkFileTree(dirFrom, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path target = dirTo.resolve(dirFrom.relativize(dir));
                    if (Files.notExists(target)) {
                        Files.createDirectory(target);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path targetFile = dirTo.resolve(dirFrom.relativize(file));
                    if (Files.notExists(targetFile)) {
                        Files.createFile(targetFile);
                    }
                    copyFile(file, targetFile);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
            }
        }
    }
}
