package ru.mail.polis.homework.io;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
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
        if (pathFrom == null || pathTo == null) {
            return;
        }
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }
        try {
            Path to = Paths.get(pathTo);
            Path directory = Files.isDirectory(to) ? to : to.getParent();
            if (!Files.exists(directory)) {
                Files.createDirectories(directory);
            }
            fileVisitor(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileVisitor(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copy(file, to.resolve(from.relativize(file)));
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attributes)
                    throws IOException {
                Path path = to.resolve(from.relativize(dir));
                if (!Files.exists(path)) {
                    Files.createDirectories(path);
                }
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream iStream = Files.newInputStream(from)){
            try (OutputStream oStream = Files.newOutputStream(to)){
                byte[] buff = new byte[1024];
                int length;
                while((length = iStream.read(buff)) > 0){
                    oStream.write(buff, 0, length);
                }
            }
        }
    }
}
