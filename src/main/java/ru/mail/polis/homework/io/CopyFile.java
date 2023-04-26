package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path dirFrom = Paths.get(pathFrom);
        Path dirTo = Paths.get(pathTo);
        try {
            if (!Files.exists(dirTo)) {
                Files.createDirectories(Files.isDirectory(dirTo) ? dirTo : dirTo.getParent());
            }
            Files.walkFileTree(dirFrom, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(dirTo.resolve(dirFrom.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path copiedFile = Files.createFile(dirTo.resolve(dirFrom.relativize(file)));
                    copyFileContent(file, copiedFile);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void copyFileContent(Path fileFrom, Path fileTo) throws IOException {
        try (InputStream is = Files.newInputStream(fileFrom)) {
            try (OutputStream os = Files.newOutputStream(fileTo)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
        }
    }

}
