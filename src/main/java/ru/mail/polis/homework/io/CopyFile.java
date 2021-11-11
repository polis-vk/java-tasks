package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */

    public static final int BUFFER_SIZE = 1024;

    public static String copyFiles(String pathFrom, String pathTo) {
        if (Objects.requireNonNull(pathFrom).isEmpty() || Objects.requireNonNull(pathTo).isEmpty()
                || pathFrom.equals(pathTo)) {
            return null;
        }
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return null;
        }
        Path to = Paths.get(pathTo);
        try {
            if (!Files.exists(to)) {
                Files.createDirectories(Files.isRegularFile(from) ? to.getParent() : to);
            }
            copyFileInside(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }

    private static void copyFileInside(Path from, Path to) throws IOException {
        if (!Files.isDirectory(from)) {
            copyFile(from, to);
            return;
        }
        if (!Files.exists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(from)) {
            for (Path path : paths) {
                copyFileInside(path, to.resolve(from.relativize(path)));
            }
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream is = Files.newInputStream(from);
             OutputStream os = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}
