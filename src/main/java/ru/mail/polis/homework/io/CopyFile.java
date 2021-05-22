package ru.mail.polis.homework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
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

    private static int copyFile(Path from, Path to) throws IOException {
        if (Files.notExists(to)) {
            Files.createDirectories(to.getParent());
        }
        Files.createFile(to);
        try (BufferedInputStream input = new BufferedInputStream(Files.newInputStream(from));
             BufferedOutputStream output = new BufferedOutputStream(Files.newOutputStream(to))) {
            int totalBytes = 0;
            int blockSize;
            byte[] buffer = new byte[1024];
            while ((blockSize = input.read(buffer)) > 0) {
                output.write(buffer, 0, blockSize);
                totalBytes += blockSize;
            }
            return totalBytes;
        }
    }
}
