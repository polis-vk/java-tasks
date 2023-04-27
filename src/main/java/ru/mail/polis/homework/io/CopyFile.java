package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path source = Paths.get(pathFrom);

        if (!Files.exists(source)) {
            return;
        }

        Path dest = Paths.get(pathTo);

        try {
            if (Files.isRegularFile(source)) {
                Files.createDirectories(dest.getParent());
            }
            copyDirectory(source, dest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDirectory(Path source, Path dest) throws IOException {
        try {
            if (Files.isRegularFile(source)) {
                Files.createFile(dest);
                copyFile(source, dest);
            } else {
                if (Files.notExists(dest)) {
                    Files.createDirectories(dest);
                }
                try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)) {
                    for (Path dir : directoryStream) {
                        copyDirectory(dir, dest.resolve(dir.getFileName()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private static void copyFile(Path source, Path dest) {
        try (InputStream inputStream = Files.newInputStream(source)) {
            try (OutputStream outputStream = Files.newOutputStream(dest)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
