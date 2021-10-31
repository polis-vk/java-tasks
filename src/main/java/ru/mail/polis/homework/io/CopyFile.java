package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static void copyFile(Path from, Path to) {
        try (InputStream input = Files.newInputStream(from); OutputStream output = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int recorded = input.read(buffer);
            while (recorded > 0) {
                output.write(buffer, 0, recorded);
                recorded = input.read(buffer);
            }
            output.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return null;
        }

        Path to = Paths.get(pathTo);
        if (Files.isRegularFile(from)) {
            try {
                Files.createDirectories(to.getParent());
                copyFile(from, to);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
