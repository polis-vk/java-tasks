package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (Files.notExists(from)) {
            return null;
        }

        try {
            if (Files.isRegularFile(from)) {
                Files.createDirectories(to.getParent());
                copyFile(from, to);
            } else if (Files.isDirectory(from)) {
                if (Files.notExists(to)) {
                    Files.createDirectories(to);
                }

                try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(from)) {
                    for (Path path : dirStream) {
                        copyFiles(path.toString(), to.resolve(path.getFileName()).toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream fis = Files.newInputStream(from)) {
            try (OutputStream fos = Files.newOutputStream(to)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = fis.read(buffer)) > 0) {
                    fos.write(buffer, 0, length);
                }
            }
        }
    }

}
