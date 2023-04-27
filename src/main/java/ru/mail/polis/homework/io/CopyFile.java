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
    private final static int BUFF_SIZE = 1024;

    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return;
        }
        try {
            if (Files.isRegularFile(from)) {
                Files.createDirectories(to.getParent());
            } else if (Files.exists(from)) {
                Files.createDirectory(to.getParent());
            }
            copyDir(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyDir(Path pathFrom, Path pathTo) throws IOException {
        if (!Files.isDirectory(pathFrom)) {
            copyFile(pathFrom, pathTo);
            return;
        }
        if (Files.notExists(pathTo)) {
            Files.createDirectory(pathTo);
        }
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(pathFrom)) {
            for (Path path : stream) {
                copyDir(path, pathTo.resolve(path.getFileName()));
            }
        }
    }

    public static void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream in = Files.newInputStream(pathFrom)) {
            try (OutputStream out = Files.newOutputStream(pathTo)) {
                byte[] buffer = new byte[BUFF_SIZE];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }
}
