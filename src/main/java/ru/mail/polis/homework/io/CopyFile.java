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
        Path fileFrom = Paths.get(pathFrom);
        if (Files.notExists(fileFrom)) {
            return;
        }
        Path fileTo = Paths.get(pathTo);

        try {
            Files.createDirectories(fileTo.getParent());
            Files.walkFileTree(fileFrom, new SimpleFileVisitor<Path>() {
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copy(file, fileTo.resolve(fileFrom.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }

                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path resolve = fileTo.resolve(fileFrom.relativize(dir));
                    if (Files.notExists(resolve)) {
                        Files.createDirectory(resolve);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from); OutputStream outputStream = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length = inputStream.read(buffer);
            while (length > 0) {
                outputStream.write(buffer, 0, length);
                length = inputStream.read(buffer);
            }
        }
    }
}
