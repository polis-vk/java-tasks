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
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return;
        }

        Path to = Paths.get(pathTo);
        try {
            if (Files.isDirectory(from)) {
                if (Files.notExists(to)) {
                    Files.createDirectories(to);
                }
            } else {
                if (Files.notExists(to)) {
                    Files.createDirectories(to.getParent());
                }
                copyFile(from, to);
                return;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DirectoryStream<Path> files = Files.newDirectoryStream(from)) {
            for (Path path : files) {
                copyFiles(path.toString(), to.resolve(path.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from)) {
            try (OutputStream outputStream = Files.newOutputStream(to)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                int length;
                do {
                    length = inputStream.read(buffer);
                    outputStream.write(buffer);
                } while (length > 0);
                outputStream.flush();
            }
        }
    }
}
