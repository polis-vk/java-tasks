package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
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
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path newFile = Paths.get(pathTo);
        Files.createDirectories(newFile.getParent());
        Files.createFile(newFile);
        copyFile(Paths.get(pathFrom), newFile);
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (BufferedWriter writer = Files.newBufferedWriter(to);
             BufferedReader reader = Files.newBufferedReader(from)) {
            while (reader.ready()) {
                writer.write(reader.readLine());
            }
        }
    }


}
