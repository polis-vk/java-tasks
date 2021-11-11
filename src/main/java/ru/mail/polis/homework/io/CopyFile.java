package ru.mail.polis.homework.io;

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

    private static final String SUCCESSFUL_MESSAGE = "Success";
    private static final String SOURCE_ERROR = "Source path does not exist";

    private static final int BUFFER_SIZE = 8192;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */

    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (!Files.exists(from)) {
            return SOURCE_ERROR;
        }

        try {
            if (!Files.exists(to)) {
                if (Files.isRegularFile(from)) {
                    Files.createDirectories(to.getParent());
                    copyFile(from, to);
                } else if (Files.isDirectory(from)) {
                    Files.createDirectories(to);
                }
            }

            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = to.resolve(from.relativize(dir));
                    if (!Files.exists(targetDir)) {
                        Files.createDirectory(targetDir);
                        Files.walkFileTree(dir, this);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyFile(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return e.getLocalizedMessage();
        }

        return SUCCESSFUL_MESSAGE;
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream input = Files.newInputStream(from);
             OutputStream output = Files.newOutputStream(to)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int readSize = input.read(buffer);
            while (readSize > 0) {
                output.write(buffer, 0, readSize);
                readSize = input.read(buffer);
            }
        }
    }

}
