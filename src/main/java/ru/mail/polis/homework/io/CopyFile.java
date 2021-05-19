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
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path copyFrom = Paths.get(pathFrom);
        Path copyTo = Paths.get(pathTo);

        if (Files.notExists(copyFrom)) {
            return null;
        }

        try {
            if (Files.isRegularFile(copyFrom)) {
                Files.createDirectories(copyTo.getParent());
                Files.createFile(copyTo);
                copyFile(copyFrom, copyTo);
                return null;
            }
            Files.createDirectories(copyTo);
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(copyFrom)) {
                for (Path directory : directoryStream) {
                    copyFiles(directory.toString(), copyTo.resolve(directory.getFileName()).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path copyFrom, Path copyTo) throws IOException {
        try (InputStream inputStream = Files.newInputStream(copyFrom);
             OutputStream outputStream = Files.newOutputStream(copyTo)) {
            byte[] buffer = new byte[1024];
            int blockSize = inputStream.read(buffer);
            while (blockSize > 0) {
                outputStream.write(buffer, 0, blockSize);
                blockSize = inputStream.read(buffer);
            }
            outputStream.flush();
        }
    }
}
