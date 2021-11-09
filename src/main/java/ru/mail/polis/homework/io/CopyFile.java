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

public class CopyFile {
    private final static Integer BUFFER_SIZE = 1024;
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
        if (checkNullPath(pathFrom, pathTo)) return ERROR_MESSAGE_1;

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (checkNoExistFiles(from)) return ERROR_MESSAGE_2;
        try {
            createDirectory(to);
            Files.walkFileTree(from, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path path = to.resolve(from.relativize(dir));
                    if (!Files.exists(path)) {
                        Files.createDirectory(path);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path path = to.resolve(from.relativize(file));
                    copyFile(file, path);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return SUCCESS_MESSAGE;
    }


    private static boolean checkNullPath(String from, String to) {
        return from == null || to == null;
    }

    private static boolean checkNoExistFiles(Path from) {
        return !Files.exists(from);
    }

    private static void createDirectory(Path to) throws IOException {
        if (Files.isDirectory(to)) {
            Files.createDirectories(to);
        } else {
            Files.createDirectories(to.getParent());
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from);
             OutputStream outputStream = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            outputStream.write(inputStream.read(buffer));
            outputStream.flush();
        }
    }
}