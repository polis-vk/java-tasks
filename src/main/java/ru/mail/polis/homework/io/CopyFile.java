package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        if (!Files.exists(source)) {
            return null;
        }
        Path target = Paths.get(pathTo);
        if (!Files.isDirectory(target)) {
            Files.createDirectories(target.getParent());
        }

        Files.walkFileTree(source, new SimpleFileVisitor<>() {

                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        if (target.equals(dir)) {
                            return FileVisitResult.CONTINUE;
                        }
                        Path newDir = target.resolve(source.relativize(dir));
                        if (Files.exists(newDir.getParent())) {
                            Files.createDirectory(newDir);
                        } else {
                            Files.createDirectories(newDir);
                        }
                        return FileVisitResult.CONTINUE;
                    }

                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        Path newFile = target.resolve(source.relativize(file));
                        try {
                            copyContent(file, newFile);
                            return FileVisitResult.CONTINUE;
                        } catch (Exception e) {
                            return FileVisitResult.CONTINUE;
                        }
                    }
                }
        );
        return null;
    }


    public static void copyContent(Path source, Path target) throws Exception {
        FileInputStream inputStream = new FileInputStream(source.toFile());
        FileOutputStream outputStream = new FileOutputStream(target.toFile());
        byte[] buffer = new byte[1024];

        while (inputStream.available() > 0) {
            int count = inputStream.read(buffer);
            outputStream.write(buffer, 0, count);
        }
        inputStream.close();
        outputStream.close();
    }
}
