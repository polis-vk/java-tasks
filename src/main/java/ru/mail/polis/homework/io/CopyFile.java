package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.FileVisitResult;
import java.nio.file.Path;
import java.nio.file.Files;
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
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path srcDir = Paths.get(pathFrom).getParent();
        Path destDir = Paths.get(pathTo).getParent();
        if (Files.notExists(destDir)) {
            Files.createDirectories(destDir);
        }
        Files.walkFileTree(srcDir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                try {
                    Files.createDirectory(destDir.resolve(srcDir.relativize(dir)));
                } catch (FileAlreadyExistsException ignored) {}
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Path newFile = Files.createFile(destDir.resolve(srcDir.relativize(file)));
                copyFileContent(file, newFile);
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyFileContent(Path fromFile, Path toFile) {
        try (InputStream inputStream = Files.newInputStream(fromFile);
             OutputStream outputStream = Files.newOutputStream(toFile)) {
            byte[] buffer = new byte[1000];
            while (inputStream.available() > 0) {
                int left = inputStream.read(buffer);
                outputStream.write(buffer, 0, left);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
