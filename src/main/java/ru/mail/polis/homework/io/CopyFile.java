package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return;
        }
        Path target = Paths.get(pathTo);
        try {
            Files.createDirectories((Files.isRegularFile(source) ? target.getParent() : target));
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
                        byte[] buffer = new byte[1024];
                        int lengthRead = inputStream.read(buffer);
                        if (lengthRead > 0) {
                            outputStream.write(buffer);
                            outputStream.flush();
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (EOFException e) {
            e.printStackTrace();
        }
    }
}
