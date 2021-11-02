package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return null;
        }
        Path to = Paths.get(pathTo);
        try {
            Files.createDirectories(to.getParent());
            if (Files.isRegularFile(from)) {
                copyFile(from, to);
                return null;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
            Files.createDirectory(to);
            for (Path path : directoryStream) {
                copyFiles(path.toString(), to.resolve(path.getFileName()).toString());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream inputStream = Files.newInputStream(from);
             OutputStream outputStream = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int blockSize = inputStream.read(buffer);
            while (blockSize > 0) {
                outputStream.write(buffer, 0, blockSize);
                blockSize = inputStream.read(buffer);
            }
        }
    }

}
