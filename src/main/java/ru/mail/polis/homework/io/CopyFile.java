package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        final Path source = Path.of(pathFrom);
        final Path destination = Path.of(pathTo);

        if (!Files.exists(source)) {
            return null;
        }

        if (Files.isRegularFile(source)) {
            if (!Files.exists(destination)) {
                try {
                    Files.createDirectories(destination.getParent());
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            copyFile(source, destination);
            return null;
        }

        if (!Files.exists(destination)) {
            try {
                Files.createDirectories(destination);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }

        try (DirectoryStream<Path> entries = Files.newDirectoryStream(source)) {
            for (Path entry : entries) {
                copyFiles(entry.toString(), destination.resolve(entry.getFileName()).toString());
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return null;
    }

    private static void copyFile(final Path pathFrom, final Path pathTo) {
        byte[] bytes = new byte[1024];
        try (InputStream inputStream = new FileInputStream(String.valueOf(pathFrom))) {
            try (OutputStream outStream = new FileOutputStream(String.valueOf(pathTo))) {
                while (inputStream.read(bytes) > 0) {
                    outStream.write(bytes);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
