package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null || Files.notExists(Paths.get(pathFrom))) {
            return null;
        }
        Path toPath = Paths.get(pathTo);
        Path fromPath = Paths.get(pathFrom);
        try {
            Files.createDirectories(toPath.getParent());
            Files.walkFileTree(fromPath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(toPath.resolve(fromPath.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyFile(file, toPath.resolve(fromPath.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream input = Files.newInputStream(pathFrom);
             OutputStream output = Files.newOutputStream(pathTo)) {
            byte[] b = new byte[1024];
            int data;
            while ((data = input.read(b)) > 0) {
                output.write(b, 0, data);
            }
        }
    }
}
