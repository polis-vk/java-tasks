package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);

        if (!Files.exists(from)) {
            return null;
        }

        try {
            Path to = Paths.get(pathTo);

            Path dir = to;
            if (!Files.isDirectory(from)) {
                dir = to.getParent();
            }
            createDirectories(dir);

            visitorFiles(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void createDirectories(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }

    private static void visitorFiles(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copyInnerContent(file, Files.createFile(to.resolve(from.relativize(file))));

                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path targetPath = to.resolve(from.relativize(dir));

                if (!Files.exists(targetPath)) {
                    Files.createDirectory(targetPath);
                }

                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyInnerContent(Path from, Path to) throws IOException {
        try (InputStream input = Files.newInputStream(from);
             OutputStream output = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int sizeBlock;

            while ((sizeBlock = input.read(buffer)) > 0) {
                output.write(buffer, 0, sizeBlock);
            }
        }
    }
}
