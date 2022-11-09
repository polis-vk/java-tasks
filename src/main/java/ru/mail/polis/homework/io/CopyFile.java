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
    private static final byte[] buff = new byte[2048];

    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }

        Path to = Paths.get(pathTo);
        try {
            Files.createDirectories(to.getParent());
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(to.resolve(from.relativize(directory)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copy(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream input = Files.newInputStream(pathFrom)) {
            try (OutputStream output = Files.newOutputStream(pathTo)) {
                int dataLen;
                while ((dataLen = input.read(buff)) > 0) {
                    output.write(buff, 0, dataLen);
                }
            }
        }
    }
}
