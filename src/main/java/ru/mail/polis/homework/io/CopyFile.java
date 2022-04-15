package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
        if (Files.exists(from)) {
            Path to = Paths.get(pathTo);
            if (Files.isDirectory(from)) {
                Files.createDirectories(to);
                try (Stream<Path> walk = Files.walk(from)) {
                    walk.forEach(departure -> {
                        Path destination = Paths.get(pathTo, departure.toString().substring(pathFrom.length()));
                        copy(departure, destination);
                    });
                }
                return;
            }
            copy(from, to);
        }
    }

    private static void copy(Path departure, Path destination) {
        if (Files.isDirectory(departure)) {
            try {
                Files.createDirectories(destination);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return;
        }
        try (BufferedReader reader = Files.newBufferedReader(departure)) {
            Files.createDirectories(destination.getParent());
            Files.createFile(destination);
            try (BufferedWriter writer = Files.newBufferedWriter(destination)) {
                char[] buffer = new char[128];
                while (reader.read(buffer) != -1) {
                    writer.write(buffer);
                }
                writer.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
