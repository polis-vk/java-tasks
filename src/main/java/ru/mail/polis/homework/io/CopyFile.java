package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;

public class CopyFile {
    private final static String ERROR_MESSAGE_1 = "The path to the file does not specified";
    private final static String ERROR_MESSAGE_2 = "Directory does not exist";
    private final static String SUCCESS_MESSAGE = "Copy successful";

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return ERROR_MESSAGE_1;
        }

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (!Files.exists(from)) {
            return ERROR_MESSAGE_2;
        }

        try {
            if (!Files.exists(to)) {
                if (Files.isDirectory(to)) {
                    Files.createDirectories(to);
                } else {
                    Files.createDirectories(to.getParent());
                }
            }
            Files.walkFileTree(Path.of(pathFrom), new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path path = to.resolve(from.relativize(dir));
                    if (!Files.exists(path)) {
                        Files.createDirectory(path);
                    }
                    return CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path path = to.resolve(from.relativize(file));
                    if (!Files.exists(path)) {
                        Files.createFile(path);
                    }
                    InputStream in = Files.newInputStream(file);
                    OutputStream out = Files.newOutputStream(path);
                    out.write(in.readAllBytes());
                    return CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS_MESSAGE;
    }
}