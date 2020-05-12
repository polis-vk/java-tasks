package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;

import static java.nio.file.Files.newInputStream;
import static java.nio.file.Files.newOutputStream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path sourceDirectory = Paths.get(pathFrom);
        Path destinationDirectory = Paths.get(pathTo);

        if (Files.notExists(sourceDirectory)) {
            return null;
        }

        try {
            Files.createDirectories(destinationDirectory.getParent());

            if (Files.isRegularFile(sourceDirectory)) {
                filesCopy(sourceDirectory, destinationDirectory);
                return "";
            }

            Files.createDirectories(destinationDirectory);

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(sourceDirectory)) {
                for (Path path : paths) {
                    copyFiles(path.toString(), destinationDirectory.resolve(path.getFileName()).toString());
                }
            }

        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return "";
    }

    private static void filesCopy(Path source, Path destination) throws IOException {
        try (InputStream inputStream = newInputStream(source)) {
            try (OutputStream outputStream = newOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length = 0;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }
}


