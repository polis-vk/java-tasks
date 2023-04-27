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
        Path copyFrom = Paths.get(pathFrom).getParent();
        Path copyTo = Paths.get(pathTo).getParent();
        if (Files.notExists(copyFrom)) {
            return;
        }
        try {
            copyTo = Files.createDirectories(copyTo);
        } catch (IOException exception) {
            return;
        }
        try {
            Files.walkFileTree(copyFrom, new MyFileVisitor(copyFrom, copyTo));
        } catch (IOException exception) {
            return;
        }

    }

    static class MyFileVisitor implements FileVisitor<Path> {
        private final Path copyFrom;
        private final Path copyTo;

        public MyFileVisitor(Path copyFrom, Path copyTo) {
            this.copyFrom = copyFrom;
            this.copyTo = copyTo;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
            Path newDirPath = getNewDirPath(dir);
            if (Files.notExists(newDirPath)) {
                Files.createDirectories(getNewDirPath(dir));
            }
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Path newFilePath = Files.createFile(getNewDirPath(file));
            copying(file, newFilePath);
            return FileVisitResult.CONTINUE;
        }

        private Path getNewDirPath(Path dir) {
            return copyTo.resolve(copyFrom.relativize(dir));
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException {
            throw exception;
        }

        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null)
                throw exc;
            return FileVisitResult.CONTINUE;
        }

        private void copying(Path pathFrom, Path pathTo) throws IOException {
            try (InputStream inputStream = Files.newInputStream(pathFrom);
                 OutputStream outputStream = Files.newOutputStream(pathTo)) {
                byte[] buffer = new byte[4096];
                int bytesLength;
                while ((bytesLength = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytesLength);
                }
            }

        }

    }

}