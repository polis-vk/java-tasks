package ru.mail.polis.homework.io;


import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static final int BUF_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path fromDir = Paths.get(pathFrom);
        if (Files.notExists(fromDir)) {
            return;
        }
        Path toDir = Paths.get(pathTo);
        if (!Files.isDirectory(toDir)) {
            toDir = toDir.getParent();
        }
        if (!Files.exists(toDir)) {
            Files.createDirectories(toDir);
        }
        Files.walk(fromDir)
                .forEach(source -> {
                    Path destination = Paths.get(pathTo, source.toString()
                            .substring(pathFrom.length()));
                    try {
                        if (Files.isDirectory(source)) {
                            Files.copy(source, destination);
                        } else if (Files.isRegularFile(source)) {
                            copy(source, destination);
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from);
             OutputStream outputStream = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUF_SIZE];
            int length;
            while ((length = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, length);
            }
        }
    }

}
