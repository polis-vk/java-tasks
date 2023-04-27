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
        Path pathFromPath = Paths.get(pathFrom);
        Path pathToPath = Paths.get(pathTo);
        if (!Files.exists(pathFromPath)) {
            return;
        }
        try {
            Files.createDirectories(pathToPath.getParent());
            copyDirectory(pathFromPath, pathToPath);
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void copyDirectory(Path pathFrom, Path pathTo) {
        if (Files.isRegularFile(pathFrom)) {
            try {
                Files.createFile(pathTo);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
            copyFile(pathFrom, pathTo);
        }
        if (!Files.exists(pathTo)) {
            try {
                Files.createDirectory(pathTo);
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(pathFrom)) {
            for (Path dir : directoryStream) {
                copyDirectory(dir, pathTo.resolve(dir.getFileName()));
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }

    private static void copyFile(Path pathFrom, Path pathTo) {
        try (InputStream inputStream = Files.newInputStream(pathFrom); OutputStream outputStream = Files.newOutputStream(pathTo)) {
            byte[] copyBuffer = new byte[1024];
            int length;
            while ((length = inputStream.read(copyBuffer)) > 0) {
                outputStream.write(copyBuffer, 0, length);
            }
        } catch (IOException ioException) {
            ioException.printStackTrace();
        }
    }
}
