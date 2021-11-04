package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path start = Path.of(pathFrom);
        Path finish = Path.of(pathTo);

        try {
            Files.createDirectories(Files.isDirectory(start) ? finish : finish.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            Files.walkFileTree(start, new FileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path concatenated = finish.resolve(start.relativize(dir));
                    if (!Files.exists(concatenated)) {
                        Files.createDirectory(concatenated);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path concatenated = finish.resolve(start.relativize(file));
                    if (!Files.exists(concatenated)) {
                        Files.createFile(concatenated);
                    }
                    try (InputStream in = Files.newInputStream(file);
                         OutputStream out = Files.newOutputStream(concatenated)) {
                        out.write(in.readAllBytes());
                        out.write(123);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                    return FileVisitResult.TERMINATE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}