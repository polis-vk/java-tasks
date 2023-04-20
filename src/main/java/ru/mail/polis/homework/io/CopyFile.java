package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return;
        }
        if (Files.isRegularFile(from)) {
            Files.createDirectories(to.getParent());
        }
        try {
            copyDirectory(from, to);
        } catch (IOException E) {
            E.printStackTrace();
        }
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        if (Files.isRegularFile(source)) {
            Files.createFile(target);
            copyFile(source, target);
        } else {
            if (Files.notExists(target)) {
                Files.createDirectories(target);
            }
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(source)) {
                for (Path path : dirStream) {
                    copyDirectory(path, target.resolve(path.getFileName()));
                }
            }
        }
    }

    private static void copyFile(Path source, Path target) {
        try (InputStream inputStream = Files.newInputStream(source);
             OutputStream outputStream = Files.newOutputStream(target)) {
            int bufferSize = 1024;
            byte[] buffer = new byte[bufferSize];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
