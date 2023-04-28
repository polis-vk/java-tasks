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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (Files.notExists(from)) {
            return;
        }
        try {
            if (Files.isRegularFile(from)) {
                Files.createDirectories(to.getParent());
            }
            copyDirectory(from, to);
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private static void copyDirectory(Path source, Path target) throws IOException {
        if (Files.isRegularFile(source)) {
            copyFile(source, target);
            return;
        }
        if (Files.notExists(target)) {
            Files.createDirectories(target);
        }
        try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(source)) {
            for (Path path : dirStream) {
                copyDirectory(path, target.resolve(path.getFileName()));
            }
        }
    }

    private static void copyFile(Path source, Path target) throws IOException {
        try (InputStream inputStream = Files.newInputStream(source)) {
            try (OutputStream outputStream = Files.newOutputStream(target)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }
}
