package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     * <p>
     * Работа с walkFileTree взята из https://habr.com/ru/post/437694/
     */
    private static final int BUFFER_SIZE = 1024;

    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }
        Path source = Paths.get(pathFrom);
        Path dist = Paths.get(pathTo);
        if (Files.notExists(source)) {
            return null;
        }

        try {
            Files.createDirectories(dist.resolve(source.relativize(dist)).getParent());
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(dist.resolve(source.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyOneFile(file, dist.resolve(source.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void copyOneFile(Path from, Path to) throws IOException {
        try (InputStream input = Files.newInputStream(from);
             OutputStream output = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int rec;
            while ((rec = input.read(buffer)) > 0) {
                output.write(buffer, 0, rec);
            }
        }
    }
}

