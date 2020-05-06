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
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
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
            } else if (Files.notExists(dirOut)) {
                Files.createDirectory(dirOut);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dirIn)) {
            for (Path path : files) {
                copyFiles(dirIn.toString(), dirOut.resolve(path.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path dirIn, Path dirOut) throws IOException {
        if (Files.notExists(dirOut)) {
            Files.createFile(dirOut);
        }

        try (BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(dirIn))) {
            try (BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(dirOut))) {
                byte[] buffer = new byte[1024];
                int lengthRead = 0;
                while ((lengthRead = bufferedInputStream.read(buffer)) > 0) {
                    bufferedOutputStream.write(buffer, 0, lengthRead);
                }
            }
        }
    }
}
