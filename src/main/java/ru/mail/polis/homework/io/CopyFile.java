package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path fromFilePath = Paths.get(pathFrom);
        Path toFilePath = Paths.get(pathTo);

        if (Files.exists(fromFilePath) && Files.isDirectory(fromFilePath)) {
            Files.walkFileTree(fromFilePath, new SimpleFileVisitor<Path>() {
                Path currentFilePath = toFilePath.getParent();

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.createFile(currentFilePath.resolve(file.getFileName()));
                    FileInputStream fromFileInputStream = new FileInputStream(file.toFile());
                    FileOutputStream toFileOutputStream = new FileOutputStream(currentFilePath.resolve(file.getFileName()).toFile());
                    fromFileInputStream.transferTo(toFileOutputStream);
                    fromFileInputStream.close();
                    toFileOutputStream.close();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    currentFilePath = currentFilePath.resolve(dir.getFileName());
                    Files.createDirectories(currentFilePath);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    currentFilePath = currentFilePath.getParent();
                    return FileVisitResult.CONTINUE;
                }
            });
        } else if (Files.exists(fromFilePath)) {
            Files.createDirectories(toFilePath.getParent());
            Files.createFile(toFilePath);
        }
        return null;
    }

}
