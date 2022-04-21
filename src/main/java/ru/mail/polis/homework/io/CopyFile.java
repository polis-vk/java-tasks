package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
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
        if (pathFrom == null || pathFrom.isEmpty()) {
            throw new IllegalArgumentException("The copy source is incorrect");
        }
        if (pathTo == null || pathTo.isEmpty()) {
            throw new IllegalArgumentException("The recipient directory is not correct");
        }
        Path source = Paths.get(pathFrom);
        if (Files.notExists(source)) {
            return;
        }
        Path recipient = Paths.get(pathTo);
        try {
            if (Files.notExists(recipient)) {
                if (Files.isRegularFile(source)) {
                    Files.createDirectories(recipient.getParent());
                } else {
                    Files.createDirectories(recipient);
                }

            }
            copyPaths(source, recipient);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void copyPaths(Path source, Path recipient) throws IOException {
        if (Files.isRegularFile(source)) {
            copyFiles(source, recipient);
        } else {
            if (Files.notExists(recipient)) {
                Files.createDirectory(recipient);
            }
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(source)) {
                for (Path currentPath : directoryStream) {
                    copyPaths(currentPath, recipient.resolve(currentPath.getFileName()));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

    }

    private static void copyFiles(Path source, Path recipient) {
        try (InputStream in = new FileInputStream(String.valueOf(source))) {
            try (OutputStream out = new FileOutputStream(String.valueOf(recipient))) {
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
