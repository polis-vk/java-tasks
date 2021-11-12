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
            copyDirectoryOrFile(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }

    public static void copyDirectoryOrFile(Path from, Path to) throws IOException {
        if (Files.isDirectory(from)) {
            Files.createDirectories(to);
            copyFolder(from, to);
            return;
        }
        Files.createDirectories(to.getParent());
        copyFile(from, to);
    }

    private static void copyFolder(Path from, Path to) throws IOException {
        if (!Files.exists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(from)) {
            for (Path path : paths) {
                if (Files.isDirectory(path)) {
                    copyFolder(path, to.resolve(from.relativize(path)));
                    continue;
                }
                copyFile(path, to.resolve(from.relativize(path)));
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
