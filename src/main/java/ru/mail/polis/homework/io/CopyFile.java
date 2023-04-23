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
        Path srcDirectory = Paths.get(pathFrom).getParent();
        Path destDirectory = Paths.get(pathTo).getParent();
        if (Files.notExists(srcDirectory)) {
            return;
        } else if (Files.notExists(destDirectory)) {
            try {
                destDirectory = Files.createDirectories(destDirectory);
            } catch (IOException e) {
                throw new RuntimeException(e.getMessage());
            }
        }
        try {
            Files.walkFileTree(srcDirectory, new PathSimpleFileVisitor(srcDirectory, destDirectory));
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    static class PathSimpleFileVisitor implements FileVisitor<Path> {

        private final Path srcDirectory;
        private final Path destDirectory;

        public PathSimpleFileVisitor(Path srcDirectory, Path destDirectory) {
            this.srcDirectory = srcDirectory;
            this.destDirectory = destDirectory;
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
            copyFile(file, newFilePath);
            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
            throw exc;
        }

        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null)
                throw exc;
            return FileVisitResult.CONTINUE;
        }

        private Path getNewDirPath(Path dir) {
            return destDirectory.resolve(srcDirectory.relativize(dir));
        }

        private void copyFile(Path pathFrom, Path pathTo) throws IOException {
            try (InputStream inputStream = Files.newInputStream(pathFrom)) {
                try (OutputStream outputStream = Files.newOutputStream(pathTo)) {
                    byte[] buffer = new byte[1024];
                    int bytesLength = 0;
                    while ((bytesLength = inputStream.read()) > 0) {
                        outputStream.write(buffer, 0, bytesLength);
                    }
                }
            }
        }

    }

}
