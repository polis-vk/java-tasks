package ru.mail.polis.homework.io;

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
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(directoryFrom)) {
                if (Files.isRegularFile(directoryFrom)) {
                    copyFile(directoryFrom, directoryTo);
                    return null;
                }
                if (Files.notExists(directoryTo)) {
                    Files.createDirectories(directoryTo);
                }
                for (Path path : paths) {
                    copyFiles(path.toString(), directoryTo.resolve(path.getFileName()).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path fileFrom, Path fileTo) {
        try {
            if (Files.notExists(fileTo)) {
                Files.createDirectories(fileTo.getParent());
            }
            Files.createFile(fileTo);
            try (InputStream inputStream = Files.newInputStream(fileFrom)) {
                try (OutputStream outputStream = Files.newOutputStream(fileTo)) {
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
