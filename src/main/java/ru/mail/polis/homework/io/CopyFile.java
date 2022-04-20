package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.FileVisitResult;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path whereFrom = Paths.get(pathFrom);
        if (Files.notExists(whereFrom)) {
            return;
        }
        Path whereTo = Paths.get(pathTo);
        try {
            if (Files.isRegularFile(whereFrom)) {
                Files.createDirectories(whereTo.getParent());
            } else {
                Files.createDirectories(whereTo);
            }
            doFileCopy(whereFrom, whereTo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void doFileCopy(Path whereFrom, Path whereTo) throws IOException {
        Files.walkFileTree(whereFrom, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = whereTo.resolve(whereFrom.relativize(dir));
                if (Files.notExists(targetPath)) {
                    Files.createDirectory(targetPath);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (InputStream inputStream = Files.newInputStream(file)) {
                    try (OutputStream outputStream = Files.newOutputStream(whereTo.resolve(whereFrom.relativize(file)))) {
                        byte[] buffer = new byte[1024];
                        int blockSize = inputStream.read(buffer);
                        while (blockSize > 0) {
                            outputStream.write(buffer, 0, blockSize);
                            blockSize = inputStream.read(buffer);
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}
