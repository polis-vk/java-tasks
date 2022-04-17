package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return;
        }
        Path to = Paths.get(pathTo);
        if (Files.isDirectory(from)) {
            Files.createDirectories(to);
            try (Stream<Path> walk = Files.walk(from)) {
                walk.forEach(departure -> {
                    Path destination = Paths.get(pathTo, String.valueOf(from.relativize(departure)));
                    copy(departure, destination);
                });
            }
            return;
        }
        copy(from, to);

    }

    private static void copy(Path departure, Path destination) {
        try {
            if (Files.isDirectory(departure)) {
                Files.createDirectories(destination);
                return;
            }
            Files.createDirectories(destination.getParent());
            Files.createFile(destination);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (InputStream input = Files.newInputStream(departure); OutputStream output = Files.newOutputStream(destination)) {
            byte[] buffer = new byte[128];
            while (input.read(buffer) != -1) {
                output.write(buffer);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
