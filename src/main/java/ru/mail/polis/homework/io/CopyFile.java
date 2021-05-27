package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class CopyFile {


    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        try {
            Path directoryFrom = Paths.get(pathFrom);
            Path directoryTo = Paths.get(pathTo);
            if (Files.notExists(directoryFrom)) {
                return null;
            }
            if (Files.isRegularFile(directoryFrom)) {
                copyFile(directoryFrom, directoryTo);
                return null;
            }
            if (Files.notExists(directoryTo)) {
                Files.createDirectories(directoryTo);
            }
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(directoryFrom)) {
                paths.forEach(path -> copyFiles(path.toString(), directoryTo.resolve(path.getFileName()).toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path from, Path to) throws IOException {
        if (Files.notExists(to.getParent())) {
            Files.createDirectories(to.getParent());
        }
        Files.createFile(to);
        try (BufferedInputStream input = new BufferedInputStream(Files.newInputStream(from))) {
            try (BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(to))) {
                int blockSize;
                byte[] buffer = new byte[1024];
                while ((blockSize = input.read(buffer)) > 0) {
                    output.write(buffer, 0, blockSize);
                }
            }
        }
    }

        }
