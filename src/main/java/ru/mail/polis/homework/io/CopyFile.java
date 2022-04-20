package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return;
        }
        Path target = Paths.get(pathTo);
        try {
            if (!Files.isDirectory(target)) {
                Files.createDirectories(target.getParent());
            }
            walkTree(source, target);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void walkTree(Path source, Path target) throws IOException {
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (target.equals(dir)) {
                            return FileVisitResult.CONTINUE;
                        }
                        Path newDirPath = target.resolve(source.relativize(dir));
                        if (Files.exists(newDirPath.getParent())) {
                            Files.createDirectory(newDirPath);
                        } else {
                            Files.createDirectories(newDirPath);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        Path newFilePath = target.resolve(source.relativize(file));
                        copyContent(file, newFilePath);
                        return FileVisitResult.CONTINUE;
                    }
                }
        );
    }

    private static void copyContent(Path source, Path target) throws IOException {
        try (FileInputStream inputStream = new FileInputStream(source.toFile());
             FileOutputStream outputStream = new FileOutputStream(target.toFile())) {
            byte[] buffer = new byte[1024];
            while (inputStream.available() > 0) {
                int count = inputStream.read(buffer);
                outputStream.write(buffer, 0, count);
            }
        }
    }
}
