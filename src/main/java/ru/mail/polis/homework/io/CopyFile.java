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
        if (Files.isDirectory(fromDirectory)) {
            if (!Files.exists(toDirectory)) {
                try {
                    Files.createDirectories(Paths.get(toDirectory.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            try (DirectoryStream<Path> files = Files.newDirectoryStream(fromDirectory)) {
                for (Path path : files) {
                    Path toFile = Paths.get(toDirectory.toString(), path.getFileName().toString());
                    copyFiles(String.valueOf(path), toFile.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        } else {
            try {
                Files.createDirectories(toDirectory.getParent());
                Files.createFile(toDirectory);
                copyFileStream(fromDirectory.toString(), toDirectory.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    public static void copyFileStream(String fromFile, String toFile) throws IOException {

        try (InputStream inputStream = new FileInputStream(fromFile)) {
            try (OutputStream outputStream = new FileOutputStream(toFile)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, length);
                }
            }
        }
    }
}
