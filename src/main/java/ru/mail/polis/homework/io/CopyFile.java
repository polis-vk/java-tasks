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
    public static String copyFiles(String pathFrom, String pathTo) {
        Path dirIn = Paths.get(pathFrom);
        Path dirOut = Paths.get(pathTo);

        if (Files.notExists(dirIn)) {
            return null;
        }

        try {
            if (Files.isRegularFile(dirIn)) {
                Files.createDirectories(dirOut.getParent());
                CopyFile(dirIn, dirOut);
            } else if (Files.isDirectory(dirIn)) {
                Files.createDirectories(dirOut);
                try (DirectoryStream<Path> paths = Files.newDirectoryStream(dirIn)) {
                    for (Path path : paths) {
                        copyFiles(path.toString(), dirOut.resolve(path.getFileName()).toString());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void CopyFile(Path dirIn, Path dirOut) throws IOException {
        try (InputStream fileIn = Files.newInputStream(dirIn)) {
            try (OutputStream fileOut = Files.newOutputStream(dirOut)) {
                byte[] buffer = new byte[1];
                int readLength;
                while ((readLength = fileIn.read(buffer)) > 0) {
                    fileOut.write(buffer, 0, readLength);
                }
            }
        }
    }
}
