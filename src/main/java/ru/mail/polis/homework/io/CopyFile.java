package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        File source = new File(pathFrom);
        File destination = new File(pathTo);

        if (Files.notExists(destination.toPath().getParent())) {
            try {
                Files.createDirectories(destination.toPath().getParent());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if (source.isDirectory()) {
            if (!destination.exists()) {
                destination.mkdirs();
            }
            String[] files = source.list();
            for (String file : files) {
                String sourcePath = pathFrom + File.separator + file;
                String destinationPath = pathTo + File.separator + file;
                copyFiles(sourcePath, destinationPath);
            }
        } else {
            try (InputStream inputStream = Files.newInputStream(source.toPath())) {
                try (OutputStream outputStream = Files.newOutputStream(destination.toPath())) {
                    byte[] buffer = new byte[1000];
                    while (inputStream.available() > 0) {
                        int left = inputStream.read(buffer);
                        outputStream.write(buffer, 0, left);
                    }
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
            catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
