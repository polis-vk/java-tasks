package ru.mail.polis.homework.io;

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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return;
        }
        Path to = Paths.get(pathTo);
        if (!Files.isDirectory(from)) {
            try {
                copyFile(from, to);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try (Stream<Path> walk = Files.walk(from)) {
            Files.createDirectories(to);
            walk.forEach(departure -> {
                Path destination = to.resolve(from.relativize(departure));
                try {
                    if (Files.isDirectory(departure)) {
                        copyDirectory(destination);
                    } else {
                        copyFile(departure, destination);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDirectory(Path destination) throws IOException {
        if (Files.exists(destination)) {
            return;
        }
        Files.createDirectory(destination);
    }

    private static void copyFile(Path departure, Path destination) throws IOException {
        if (!Files.exists(destination.getParent())) {
            Files.createDirectories(destination.getParent());
        }
        try (InputStream input = Files.newInputStream(departure); OutputStream output = Files.newOutputStream(destination)) {
            byte[] buffer = new byte[128];
            while (input.read(buffer) != -1) {
                output.write(buffer);
            }
        }
    }
}
