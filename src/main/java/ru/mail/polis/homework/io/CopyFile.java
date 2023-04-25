package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
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
        Path src = Paths.get(pathFrom).getParent();
        Path dest = Paths.get(pathTo).getParent();
        if (Files.notExists(dest)) {
            try {
                Files.createDirectories(dest);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        try {
            Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    try {
                        Path path = dest.resolve(src.relativize(dir));
                        Files.createDirectory(path);
                    } catch (FileAlreadyExistsException ignored) {
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path newFile = Files.createFile(dest.resolve(src.relativize(file)));
                    copyFile(file, newFile);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("Error: " + e);
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from)) {
            try (OutputStream outputStream = Files.newOutputStream(to)) {
                byte[] buffer = new byte[1024];
                int content = 0;
                while ((content = inputStream.read()) > 0) {
                    outputStream.write(buffer, 0, content);
                }
            }
        }
    }

}
