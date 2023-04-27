package ru.mail.polis.homework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathFrom.isBlank()) {
            return;
        }
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return;
        }
        if (pathTo == null || pathTo.isBlank()) {
            return;
        }
        Path dest = Paths.get(pathTo);
        try {
            Files.createDirectories(Files.isRegularFile(source) ? dest.getParent() : dest);
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path destPath = dest.resolve(source.relativize(dir));
                    if (!Files.exists(destPath)) {
                        Files.createDirectories(destPath);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path fileFrom, BasicFileAttributes attrs) throws IOException {
                    Path fileTo = dest.resolve(source.relativize(fileFrom));
                    copyFileContent(fileFrom, fileTo);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Exception: " + e);
        }
    }

    public static void copyFileContent(Path fileFrom, Path fileTo) throws IOException {
        try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(fileFrom))) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(fileTo))) {
                byte[] buffer = new byte[inputStream.available()];
                int length;
                while ((length = inputStream.read(buffer)) != -1) {
                    outputStream.write(buffer, 0, length);
                    outputStream.flush();
                }
            }
        }
    }
}
