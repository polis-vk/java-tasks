package ru.mail.polis.homework.io;

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
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return null;
        }
        Path target = Paths.get(pathTo);
        if (!Files.isDirectory(target)) {
            Files.createDirectories(target.getParent());
        }

        Files.walkFileTree(source, new SimpleFileVisitor<>() {

                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (target.equals(dir)) {
                            return FileVisitResult.CONTINUE;
                        }
                        Path newDir = target.resolve(source.relativize(dir));
                        Files.createDirectories(newDir);
                        return FileVisitResult.CONTINUE;
                    }

                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        Path newFile = target.resolve(source.relativize(file));
                        try {
                            Files.copy(file, newFile);
                            return FileVisitResult.CONTINUE;
                        } catch (IOException e) {
                            return FileVisitResult.CONTINUE;
                        }
                    }
                }
        );
        return null;
    }
}
