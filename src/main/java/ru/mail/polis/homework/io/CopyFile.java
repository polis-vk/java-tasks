package ru.mail.polis.homework.io;

import java.io.File;
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
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path from = Paths.get(pathFrom);
        if(from.toFile().exists()) {
            Path to = Paths.get(pathTo);
            if (from.toFile().isDirectory()) {
                to.toFile().mkdirs();
                Files.walk(from)
                        .forEach(departure -> {
                            Path destination = Paths.get(pathTo, departure.toString().substring(pathFrom.length()));
                            destination.toFile().mkdirs();
                            if (!destination.toFile().isDirectory()) {
                                try {
                                    copy(departure, destination);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        });
                return null;
            }
            copy(from, to);
        }
        return null;
    }

    private static void copy(Path departure, Path destination) throws IOException {
        destination.getParent().toFile().mkdirs();
        Files.createFile(destination);
        OutputStream output = Files.newOutputStream(departure);
        InputStream input = Files.newInputStream(destination);
        byte[] buffer = new byte[128];
        while (input.read(buffer) > 0) {
            output.write(buffer);
        }
    }
}
