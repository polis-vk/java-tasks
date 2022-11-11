package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class CopyFile {

    private static final int BUFFER_SIZE = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path pathF = Path.of(pathFrom);
        Path pathT = Path.of(pathTo);
        copyFiles(pathF, pathT);
    }

    private static void copyFiles(Path pathF, Path pathT) {
        if (Files.notExists(pathF)) {
            return;
        }

        try {
            if (Files.isRegularFile(pathF)) {
                Files.createDirectories(pathT.getParent());
                copyFile(pathF, pathT);
                return;
            } else {
                Files.createDirectories(pathT);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (DirectoryStream<Path> files = Files.newDirectoryStream(pathF)) {
            for (Path path : files) {
                copyFiles(path, pathT.resolve(path.getFileName())); // append new dirs to pathT
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFile(Path pathF, Path pathT) {
        try (InputStream in = Files.newInputStream(pathF)) {
            try (OutputStream out = Files.newOutputStream(pathT)) {
                byte[] buffer = new byte[BUFFER_SIZE];
                while (in.read(buffer) > 0) {
                    out.write(buffer);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
