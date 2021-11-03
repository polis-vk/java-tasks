package ru.mail.polis.homework.io;

import java.io.EOFException;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path source = Path.of(pathFrom);
        Path distance = Path.of(pathTo);
        try {
            Files.createDirectories(distance.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
            if (!Files.isDirectory(source)) {
                copyFile(source, distance);
                return;
            }

            Files.createDirectories(distance);
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
            byte[] buffer = new byte[(int) Files.size(source)];
            if (in.read(buffer) == -1) {
                throw new EOFException();
            }
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
