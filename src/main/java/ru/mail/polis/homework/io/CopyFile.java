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
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return null;
        }
        Path target = Paths.get(pathTo);
        Files.createDirectories(target.getParent());

        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = target.resolve(source.relativize(dir));
                if (!Files.exists(targetPath)) {
                    Files.createDirectory(targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (InputStream inputStream = Files.newInputStream(file);
                     OutputStream outputStream = Files.newOutputStream(target.resolve(source.relativize(file)))) {
                    byte[] buffer = new byte[inputStream.available()];
                    int lengthRead = inputStream.read(buffer);
                    if (lengthRead > 0) {
                        outputStream.write(buffer);
                        outputStream.flush();
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });

        return null;
    }
}
