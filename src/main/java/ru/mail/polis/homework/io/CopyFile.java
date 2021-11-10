package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFile {
    private static final int DEFAULT_BUFFER_SIZE = 0x400;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }
        Path from = Path.of(pathFrom);
        Path to = Path.of(pathTo);
        if (!Files.exists(from)) {
            return;
        }
        try {
            Files.createDirectories(Files.isRegularFile(from) ? to.getParent() : to);
            copyFileRecursively(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFileRecursively(Path from, Path to) throws IOException {
        if (!Files.isDirectory(from)) {
            copyFile(from, to);
            return;
        }
        if (!Files.exists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> folder = Files.newDirectoryStream(from)) {
            for (Path path : folder) {
                copyFileRecursively(path, to.resolve(path.getFileName()));
            }
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            byte[] buffer = new byte[DEFAULT_BUFFER_SIZE];
            int lenRead;
            while ((lenRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lenRead);
                out.flush();
            }
        }
    }
}