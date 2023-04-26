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
        Path copyOld = Paths.get(pathFrom).getParent();
        Path copyNew = Paths.get(pathTo).getParent();
        if (Files.notExists(copyOld)) {
            return;
        }
        try {
            copyNew = Files.createDirectories(copyNew);
        } catch (IOException exception) {
            return;
        }
        try {
            Files.walkFileTree(copyOld, new PathVisitor(copyOld, copyNew));
        } catch (IOException exception) {
            return;
        }

    }

    static class PathVisitor implements FileVisitor<Path> {
        private final Path copyOld;
        private final Path copyNew;

        public PathVisitor(Path copyOld, Path copyNew) {
            this.copyOld = copyOld;
            this.copyNew = copyNew;
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
            return copyNew.resolve(copyOld.relativize(dir));
        }
        @Override
        public FileVisitResult visitFileFailed(Path file, IOException exception) throws IOException{
            throw exception;
        }
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            if (exc != null)
                throw exc;
            return FileVisitResult.CONTINUE;
        }

        private void copying(Path pathFrom, Path pathTo) throws IOException {
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
