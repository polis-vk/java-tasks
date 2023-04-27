package ru.mail.polis.homework.io;

import java.io.*;
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
        if (pathTo == null || pathFrom == null || pathTo.isEmpty() || pathFrom.isEmpty()) {
            return;
        }

        Path source = Paths.get(pathFrom);
        Path dest = Paths.get(pathTo);

        if (Files.notExists(source)) {
            return;
        }

        try {
            if (Files.notExists(dest)){
                if (Files.isRegularFile(source)){
                    Files.createDirectories(dest.getParent());
                }
                else{
                    Files.createDirectories(dest);
                }
            }
            copyDirectory(source, dest);
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void copyDirectory(Path source, Path dest) throws IOException {
        if (Files.isRegularFile(source)) {
            Files.createFile(dest);
            copyFile(source, dest);
        }

        if (Files.notExists(dest)) {
            Files.createDirectory(dest);
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)) {
            for (Path directory : directoryStream) {
                copyDirectory(directory, dest.resolve(directory.getFileName()));
            }
        }
    }

    private static void copyFile(Path source, Path dest) throws IOException {
        try (InputStream inputStream = Files.newInputStream(source)) {
            try (OutputStream outputStream = Files.newOutputStream(dest)) {
                byte[] buffer = new byte[1024];
                int len;
                while ((len = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, len);
                }
            }
        }
    }
}
