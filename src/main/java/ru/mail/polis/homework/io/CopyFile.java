package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
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

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(directoryIn)) {
            for (Path entry : entries) {
                copyFiles(entry.toString(), directoryOut.resolve(entry.getFileName()).toString());
            }
        }

        return null;
    }

    private static void copyFile(Path fileIn, Path fileOut) throws IOException {
        if (Files.notExists(fileOut)) {
            Files.createDirectories(fileOut.getParent());
        }
        Files.createFile(fileOut);

        try (InputStream inputStream = new BufferedInputStream(new FileInputStream(fileIn.toFile()));
             OutputStream outputStream = new BufferedOutputStream(new FileOutputStream(fileOut.toFile()))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = inputStream.read(buffer)) > 0) {
                outputStream.write(buffer, 0, lengthRead);
            }
            outputStream.flush();
        }
    }

}
