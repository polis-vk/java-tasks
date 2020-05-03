package ru.mail.polis.homework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
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
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path dirIn = Paths.get(pathFrom);
        Path dirOut = Paths.get(pathTo);
        if (Files.notExists(dirIn)) {
            return null;
        }
        if (Files.isRegularFile(dirIn)) {
            copyFile(dirIn, dirOut);
            return null;
        }
        if (Files.notExists(dirOut)) {
            Files.createDirectory(dirOut);
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(dirIn)) {
            for (Path path : files) {
                copyFiles(dirIn.toString(), dirOut.resolve(path.getFileName()).toString());
            }
        }
        return null;
    }

    private static void copyFile(Path dirIn, Path dirOut) throws IOException {
        if (Files.notExists(dirOut)) {
            Files.createDirectory(dirOut.getParent());
        }
        Files.createDirectory(dirOut);

        try(BufferedInputStream bufferedInputStream = new BufferedInputStream(Files.newInputStream(dirIn));
            BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(Files.newOutputStream(dirOut))) {
            byte[] buffer = new byte[1024];
            int lengthRead = 0;
            while((lengthRead = bufferedInputStream.read(buffer)) > 0) {
                bufferedOutputStream.write(buffer, 0, lengthRead);
            }
            bufferedOutputStream.flush();
        }
    }
}
