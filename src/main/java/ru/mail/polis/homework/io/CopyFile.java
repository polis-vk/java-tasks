package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static final short BUFFER_SIZE = 1024;
    private static byte[] buffer = new byte[BUFFER_SIZE];

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path source = Paths.get(pathFrom);
        Path destination = Paths.get(pathTo);
        if (!Files.exists(source)) {
            return;
        }
        try {
            Files.createDirectories(destination.getParent());
            deepDirectoryCopy(source, destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deepDirectoryCopy(Path source, Path destination) throws IOException {
        if (!Files.isDirectory(source)) {
            copyFileData(source, destination);
            return;
        }
        if (!Files.exists(destination)) {
            Files.createDirectory(destination);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            for (Path file : stream) {
                deepDirectoryCopy(file, destination.resolve(file.getFileName()));
            }
        }
    }

    private static void copyFileData(Path source, Path destination) throws IOException {
        try (InputStream in = Files.newInputStream(source);
             OutputStream out = Files.newOutputStream(destination)) {
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}

