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
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        try {
            Path in = Paths.get(pathFrom);
            Path out = Paths.get(pathTo);

            if (Files.notExists(in)) {
                return null;
            }
            if (Files.isRegularFile(in)) {
                copyFile(in, out);
                return null;
            }
            if (Files.notExists(out)) {
                Files.createDirectories(out);
            }

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(in)) {
                for (Path path : paths) {
                    copyFiles(path.toString(), out.resolve(path.getFileName()).toString());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path in, Path out) throws IOException {
        if (Files.notExists(out)) {
            Files.createDirectories(out.getParent());
        }
        Files.createFile(out);

        try (BufferedInputStream inputStream = new BufferedInputStream(Files.newInputStream(in))) {
            try (BufferedOutputStream outputStream = new BufferedOutputStream(Files.newOutputStream(out))) {
                byte[] buffer = new byte[1024];
                int readingLength = 0;
                while ((readingLength = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, readingLength);
                }
            }
        }
    }

}
