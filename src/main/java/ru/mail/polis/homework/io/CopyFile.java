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
        if (pathFrom == null || pathTo == null || pathFrom.isEmpty() || pathTo.isEmpty()) {
            return;
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        try {
            Path to = Paths.get(pathTo);
            Path dir;
            if (Files.isDirectory(to)) {
                dir = to;
            } else {
                dir = to.getParent();
            }
            if (Files.notExists(dir)) {
                Files.createDirectories(dir);
            }
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copyContent(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path path = to.resolve(from.relativize(dir));
                    if (Files.notExists(path)) {
                        Files.createDirectories(path);
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyContent(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            byte[] buffer = new byte[1024];
            int len;
            while ((len = in.read(buffer)) != -1) {
                out.write(buffer, 0, len);
            }
        }
    }
}
