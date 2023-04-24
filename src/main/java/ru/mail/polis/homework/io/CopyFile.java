package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
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
            Path directory;
            if (Files.isDirectory(to)) {
                directory = to;
            } else {
                directory = to.getParent();
            }
            if (Files.notExists(directory)) {
                Files.createDirectories(directory);
            }
            FileVisitor(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void FileVisitor(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attributes) throws IOException {
                copyFile(file, to.resolve(from.relativize(file)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
                Path path = to.resolve(from.relativize(directory));
                if (Files.notExists(path)) {
                    Files.createDirectories(path);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from)) {
            try (OutputStream out = Files.newOutputStream(to)) {
                byte[] buffer = new byte[1024];
                int length = in.read(buffer);
                while (length > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }

}
