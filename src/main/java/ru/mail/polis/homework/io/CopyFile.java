package ru.mail.polis.homework.io;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    private static final int BUFFER_SIZE = 1024;

    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path source = Path.of(pathFrom);
        Path distance = Path.of(pathTo);

        if (!Files.isDirectory(source)) {
            copyFile(source, distance);
            return;
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            if (Files.notExists(distance.getParent())) {
                Files.createDirectories(distance.getParent());
            }

            Files.createDirectory(distance);
            for (Path path : stream) {
                copyFiles(path.toString(), distance.resolve(path.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path source, Path distance) {
        try (FileInputStream in = new FileInputStream(String.valueOf(source));
             FileOutputStream out = new FileOutputStream(String.valueOf(distance))) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int size;
            while ((size = in.read(buffer)) != -1) {
                out.write(buffer, 0, size);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
