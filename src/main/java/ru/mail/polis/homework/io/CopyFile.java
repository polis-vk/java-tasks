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

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream input = Files.newInputStream(from);
             OutputStream output = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int recorded;
            while ((recorded = input.read(buffer)) > 0) {
                output.write(buffer, 0, recorded);
            }
        }
    }

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return null;
        }
        Path to = Paths.get(pathTo);

        try {
            Files.createDirectories(to.resolve(from.relativize(to)).getParent());
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(to.resolve(from.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyFile(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
