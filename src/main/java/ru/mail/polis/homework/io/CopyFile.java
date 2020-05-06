package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;


public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {

        Path fromDirectory = Paths.get(pathFrom);
        Path toDirectory = Paths.get(pathTo);

        if (!Files.exists(fromDirectory)) {
            return null;
        }

        try {
            Files.createDirectories(toDirectory.getParent());

            if (Files.isRegularFile(fromDirectory)) {
                Files.createFile(toDirectory);
                copyFileStream(fromDirectory, toDirectory);
                return pathTo;
            }

            Files.createDirectory(toDirectory);
            try (DirectoryStream<Path> files = Files.newDirectoryStream(fromDirectory)) {
                for (Path path : files) {
                    copyFiles(path.toString(), toDirectory.resolve(path.getFileName()).toString());
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }


    public static void copyFileStream(Path fromFile, Path toFile) throws IOException {

        try (InputStream inputStream = Files.newInputStream(fromFile)) {
            try (OutputStream outputStream = Files.newOutputStream(toFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }
}
