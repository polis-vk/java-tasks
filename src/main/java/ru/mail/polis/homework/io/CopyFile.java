package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path src = Paths.get(pathFrom);
        if (Files.notExists(src)) {
            return;
        }
        Path dest = Paths.get(pathTo);
        if (Files.notExists(dest)) {
            if (Files.isDirectory(dest)) {
                Files.createDirectories(dest);
            } else {
                Files.createDirectories(dest.getParent());
            }
        }
        Files.walkFileTree(src, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectory(dest.resolve(src.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (FileInputStream input = new FileInputStream(file.toFile());
                     FileOutputStream output = new FileOutputStream(dest.resolve(src.relativize(file)).toFile())) {
                    int temp;
                    while ((temp = input.read()) != -1) {
                        output.write(temp);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }
}