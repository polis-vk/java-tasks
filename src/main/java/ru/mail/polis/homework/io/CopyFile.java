package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    private static final int BUFFER_SIZE = 1024;

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

        Path fromPath = Paths.get(pathFrom);
        if (Files.notExists(fromPath)) {
            return;
        }

        Path toPath = Paths.get(pathTo);
        try {
            Files.createDirectories(toPath.getParent());
            Files.walkFileTree(fromPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(toPath.resolve(fromPath.relativize(directory)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    fileCopy(file, toPath.resolve(fromPath.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileCopy(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream input = Files.newInputStream(pathFrom)) {
            try (OutputStream output = Files.newOutputStream(pathTo)) {
                byte[] buff = new byte[BUFFER_SIZE];
                int lengthOfData;
                while ((lengthOfData = input.read(buff)) > 0) {
                    output.write(buff, 0, lengthOfData);
                }
            }
        }
    }
}

