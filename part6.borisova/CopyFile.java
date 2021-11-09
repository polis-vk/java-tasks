package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {
    private static byte[] buffer = new byte[0];
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        try {
            Path toParent = to.getParent();
            if (toParent != null) {
                Files.createDirectories(toParent);
            }
            deepDirectoryCopy(from, to);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deepDirectoryCopy(Path from, Path to) throws IOException {
        if (!Files.exists(from)) {
            return;
        }
        if (!Files.isDirectory(from)) {
            copyFileData(from, to);
            return;
        }
        if (!Files.exists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(from)) {
            for (Path file : stream) {
                deepDirectoryCopy(file, to.resolve(file.getFileName()));
            }
        }
    }

    private static void copyFileData(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}
