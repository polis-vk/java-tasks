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
        Path source = Paths.get(pathFrom);
        if (Files.notExists(source)) {
            return;
        }
        Path destination = Paths.get(pathTo);
        if (Files.notExists(destination)) {
            if (Files.isDirectory(destination)) {
                Files.createDirectories(destination);
            } else {
                Files.createDirectories(destination.getParent());
            }
        }
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.createDirectory(destination.resolve(source.relativize(dir)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                try (BufferedReader input
                             = Files.newBufferedReader(file);
                     BufferedWriter output =
                             Files.newBufferedWriter(destination.resolve(source.relativize(file)))) {
                    int str;
                    while ((str = input.read()) != -1) {
                        output.write(str);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

}
