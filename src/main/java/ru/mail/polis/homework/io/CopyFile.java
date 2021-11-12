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
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        try {
            copyFiles(Paths.get(pathFrom), Paths.get(pathTo).getParent());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copyFiles(Path source, Path destination) throws IOException {
        if (!Files.exists(source)) {
            return;
        }
        if (!Files.exists(destination)) {
            Files.createDirectories(destination);
        }
        Files.walkFileTree(source, new CopyFileVisitor<>(destination));
    }

    private static class CopyFileVisitor<T extends Path> extends SimpleFileVisitor<T> {
        // 1 KByte
        private static final int BUFFER_SIZE = 1 << 10;
        Path destination;
        final byte[] buffer = new byte[BUFFER_SIZE];

        CopyFileVisitor(Path destination) {
            this.destination = destination;
        }

        @Override
        public FileVisitResult preVisitDirectory(T dir, BasicFileAttributes attrs) throws IOException {
            destination = destination.resolve(dir.getFileName());
            if (!Files.exists(destination)) {
                Files.createDirectory(destination);
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(T file, BasicFileAttributes attrs) throws IOException {
            try (InputStream in = Files.newInputStream(file);
                 OutputStream out = Files.newOutputStream(destination.resolve(file.getFileName()))) {
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult postVisitDirectory(T dir, IOException exc) {
            destination = destination.getParent();
            return FileVisitResult.CONTINUE;
        }
    }
}
