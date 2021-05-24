package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static final int BUFF_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        try {
            Path directoryIn = Paths.get(pathFrom);
            Path directoryOut = Paths.get(pathTo);

            if (Files.notExists(directoryIn)) {
                return null;
            }

            if (Files.isRegularFile(directoryIn)) {
                copyFile(directoryIn, directoryOut);
                return null;
            }

            if (Files.notExists(directoryOut)) {
                Files.createDirectories(directoryOut);
            }

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(directoryIn)) {
                for (Path path : paths) {
                    copyFiles(path.toString(), directoryOut.resolve(path.getFileName()).toString());
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }


    private static void copyFile(Path fileIn, Path fileOut) throws IOException {
        if (Files.notExists(fileOut)) {
            Files.createDirectories(fileOut.getParent());
        }
        Files.createFile(fileOut);

        InputStream inputStream = Files.newInputStream(fileIn);
        try (OutputStream outputStream = Files.newOutputStream(fileOut)) {
            byte[] buffer = new byte[BUFF_SIZE];
            int lengthRead;
            while ((lengthRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lengthRead);
            }
        }
    }

}
