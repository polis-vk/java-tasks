package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
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
        Path start = Path.of(pathFrom);
        Path finish = Path.of(pathTo);
        try {
            Files.createDirectories(Files.isDirectory(start) ? finish : finish.getParent());
            Files.walkFileTree(start, new SimpleFileVisitor<>() {
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
                    transferContent(file, concatenated);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void transferContent(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            in.transferTo(out);
        }
    }
}