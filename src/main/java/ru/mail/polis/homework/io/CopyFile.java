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
            } else if (Files.exists(from)) {
                Files.createDirectories(to);
            }
            copy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            byte[] buff = new byte[BUFFER_SIZE];
            try (InputStream in = Files.newInputStream(from);
                 OutputStream out = Files.newOutputStream(to)) {
                int length;
                while ((length = in.read(buff)) > 0) {
                    out.write(buff, 0, length);
                }
            }
            return;
        }
        if (Files.notExists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(from)) {
            for (Path path : stream) {
                copy(path, to.resolve(path.getFileName()));
            }
        }
    }
}
