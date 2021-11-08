package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFile {

    private static final int BUFF_SIZE = 8192;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null || pathFrom.equals(pathTo)) {
            return null;
        }
        Path pathFromDirectory = Path.of(pathFrom);
        Path pathToDirectory = Path.of(pathTo);
        if (Files.notExists(pathFromDirectory)) {
            return null;
        }

        try {
            if (Files.isRegularFile(pathFromDirectory)) {
                if (Files.notExists(pathToDirectory.getParent())) {
                    Files.createDirectories(pathToDirectory.getParent());
                }
                Files.createFile(pathToDirectory);
                copyFile(pathFromDirectory, pathToDirectory);
                return null;
            }

            if (Files.exists(pathToDirectory.getParent())) {
                Files.createDirectory(pathToDirectory);
            } else {
                Files.createDirectories(pathToDirectory);
            }

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(pathFromDirectory)) {
                for (Path path : paths) {
                    copyFiles(path.toString(), pathToDirectory.resolve(path.getFileName()).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static int copyFile(Path fromFile, Path toFile) throws IOException {
        if (fromFile == null || toFile == null) {
            throw new IllegalArgumentException();
        }

        int totalBytes = 0;
        try (InputStream inputStream = Files.newInputStream(toFile)) {
            try (OutputStream outputStream = Files.newOutputStream(fromFile)) {
                byte[] buffer = new byte[BUFF_SIZE];
                int blockSize;
                while ((blockSize = inputStream.read(buffer)) > 0) {
                    totalBytes += blockSize;
                    outputStream.write(buffer, 0, blockSize);
                }
            }
        }

        return totalBytes;
    }

}
