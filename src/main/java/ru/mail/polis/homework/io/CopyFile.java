package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
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
    public static String copyFiles(String pathFrom, String pathTo) {
        Path dirIn = Paths.get(pathFrom);
        Path dirOut = Paths.get(pathTo);

        if (Files.notExists(dirIn)) {
            return null;
        }

        try {
            if (Files.isRegularFile(dirIn)) {
                copyFile(dirIn, dirOut);
                return null;
            }
            if (Files.notExists(dirOut)) {
                Files.createDirectories(dirOut);
            }
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(dirIn)) {
                for (Path path : paths) {
                    copyFiles(path.toString(), dirOut.resolve(path.getFileName()).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void copyFile(Path dirIn, Path dirOut) throws IOException {
        if (Files.notExists(dirOut)) {
            Files.createDirectories(dirOut.getParent());
        }
        Files.createFile(dirOut);
        try (BufferedInputStream fileIn = new BufferedInputStream(Files.newInputStream(dirIn))) {
            try (BufferedOutputStream fileOut = new BufferedOutputStream(Files.newOutputStream(dirOut))) {
                byte[] buffer = new byte[512];
                int readLength = 0;
                while ((readLength = fileIn.read(buffer)) > 0) {
                    fileOut.write(buffer, 0, readLength);
                }
            }
        }
    }
}
