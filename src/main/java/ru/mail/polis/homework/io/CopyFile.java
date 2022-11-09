package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    private static final int BUFFER_CAPACITY = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        try {
            createParentDirs(Paths.get(pathTo).getParent());
            if (Files.isRegularFile(from)) {
                copyFile(pathFrom, pathTo);
                return;
            }
            int firstDirIndex = from.getNameCount() - 1;
            Files.walkFileTree(from, new FileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path newPathTo = getNewParentDirPath(dir, firstDirIndex, pathTo);
                    Files.createDirectory(newPathTo);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path newPathTo = getNewParentDirPath(file, firstDirIndex, pathTo);
                    copyFile(file.toString(), newPathTo.toString());
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getNewParentDirPath(Path currentPath, int startIndex, String pathTo) {
        int count = currentPath.getNameCount();
        // If that's the first directory in the source
        if (startIndex == count - 1) {
            return Paths.get(pathTo);
        }
        // Otherwise, this is a subdirectory
        Path subPath = currentPath.subpath(startIndex + 1, count);
        return Paths.get(pathTo, subPath.toString());
    }

    private static void createParentDirs(Path path) throws IOException {
        Path parent = path.getParent();
        if (Files.notExists(parent)) {
            createParentDirs(parent);
        }
        if (Files.notExists(path)) {
            Files.createDirectory(path);
        }
    }

    private static void copyFile(String pathFrom, String pathTo) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(pathFrom)) {
            try (FileOutputStream outputStream = new FileOutputStream(pathTo)) {
                byte[] buffer = new byte[BUFFER_CAPACITY];
                int bytes;
                while ((bytes = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytes);
                }
            }
        }
    }

}
