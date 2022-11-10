package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;
import java.nio.file.FileVisitResult;
import java.nio.file.NoSuchFileException;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    private static final int BUFFER_SIZE = 1024;
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        try {
            if (Files.notExists(from)) {
                throw new NoSuchFileException("pathFrom does not exist");
            } else if (Files.isDirectory(from)) {
                Files.createDirectories(to);
                Files.walkFileTree(from, new SimpleFileVisitor<Path>() {

                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Files.createDirectories(to.resolve(from.relativize(dir)));
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                        copyFile(file, to.resolve(from.relativize(file)));
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                Files.createDirectories(to.getParent());
                copyFile(from, to);
            }
        } catch (IOException err) {
            err.printStackTrace();
        }
    }

    private static void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream in = Files.newInputStream(pathFrom);
        OutputStream out = Files.newOutputStream(pathTo)) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int inp = in.read(buffer);
            while (inp > 0) {
                out.write(inp);
                inp = in.read(buffer);
            }
        }
    }

}
