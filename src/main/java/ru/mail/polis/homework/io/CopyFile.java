package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static final int BUFFER_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        Path to = Paths.get(pathTo);
        try {
            if (Files.isRegularFile(from)) {
                Files.createDirectories(to.getParent());
            } else {
                Files.createDirectories(to);
            }
            copy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            copyFile(from, to);
            return;
        }
        if (Files.notExists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(from)) {
            for (Path file : stream) {
                copy(file, to.resolve(file.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path from, Path to) {
        try (InputStream in = Files.newInputStream(from)) {
            try (OutputStream out = Files.newOutputStream(to)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
