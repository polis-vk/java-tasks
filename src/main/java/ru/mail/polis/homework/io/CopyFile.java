package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.util.stream.Collectors;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return null;
        }
        try {
            if (Files.isDirectory(from)) {
                Files.createDirectories(Paths.get(pathTo));
                for (Path temp : Files.list(from).collect(Collectors.toList())) {
                    copyFiles(pathFrom + File.separator + temp.getFileName(), pathTo + File.separator + temp.getFileName());
                }
            } else {
                copyFile(pathFrom, pathTo);
            }
        } catch (IOException ex) {
        }
        return pathTo;
    }
    
    public static void copyFile(String from, String to) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(Paths.get(from))) {
            try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(to))) {
                reader.lines().forEach(line -> {
                    try {
                        writer.write(line + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
