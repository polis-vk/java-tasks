package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        if (Files.notExists(fromDirectory)) {
            return pathTo;
        }
        if (Files.isRegularFile(fromDirectory)) {
            copyFile(fromDirectory, toDirectory);
            return pathTo;
        }
        try {
            Files.createDirectories(toDirectory.getParent());
            DirectoryStream<Path> files = Files.newDirectoryStream(fromDirectory);
            for (Path file : files) {
                copyFiles(file.toString(), toDirectory.resolve(file.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }


    private static void copyFile(Path fileFrom, Path fileTo) {
        try (InputStream inputStream = Files.newInputStream(fileFrom)) {
            try (OutputStream outputStream = Files.newOutputStream(fileTo)) {
                byte[] buffer = new byte[1024];
                int lengthRead;
                while ((lengthRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, lengthRead);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
