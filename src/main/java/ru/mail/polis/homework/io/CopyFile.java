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
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path fromDir = Path.of(pathFrom);
        Path toDir = Path.of(pathTo);
        try {
            if (Files.notExists(fromDir)) {
                throw new NoSuchFileException("Path does not exist");
            }

            if (Files.isDirectory(fromDir)) {
                Files.createDirectories(toDir);
                Files.walkFileTree(fromDir, new SimpleFileVisitor<>() {
                    @Override
                    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                        Files.createDirectories(toDir.resolve(fromDir.relativize(dir)));
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                        copy(file, toDir.resolve(fromDir.relativize(file)));
                        return FileVisitResult.CONTINUE;
                    }
                });
            } else {
                Files.createDirectories(toDir.getParent());
                copy(fromDir, toDir);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copy(Path from, Path to) {
        final int SIZE_OF_BUFFER = 1024;
        final byte[] BUFFER = new byte[SIZE_OF_BUFFER];

        try (InputStream fis = Files.newInputStream(from);
             OutputStream fos = Files.newOutputStream(to)) {
            int input = fis.read(BUFFER);
            while (input != -1) {
                fos.write(input);
                input = fis.read(BUFFER);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
