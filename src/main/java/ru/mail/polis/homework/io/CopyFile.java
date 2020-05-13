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
        Path fromDirectory = Paths.get(pathFrom);
        Path toDirectory = Paths.get(pathTo);

        if (Files.notExists(fromDirectory)) {
            return null;
        }

        try {
            Files.createDirectories(toDirectory.getParent());

            if (Files.isRegularFile(fromDirectory)) {
                copyFile(fromDirectory, toDirectory);
                return "";
            }

            Files.createDirectories(toDirectory);
            DirectoryStream<Path> files = Files.newDirectoryStream(fromDirectory);
            for (Path file : files) {
                copyFiles(file.toString(), toDirectory.resolve(file.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "";
    }

    private static void copyFile(Path fileFrom, Path fileTo) {
        try {
            Files.createFile(fileTo);
            try (InputStream inputStream = Files.newInputStream(fileFrom)) {
                try (OutputStream outputStream = Files.newOutputStream(fileTo)) {
                    byte[] buffer = new byte[1024];
                    int blockSize = inputStream.read(buffer);

                    while (blockSize > 0) {
                        outputStream.write(buffer, 0, blockSize);
                        blockSize = inputStream.read(buffer);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
