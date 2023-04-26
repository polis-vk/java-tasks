package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path copyFrom = Paths.get(pathFrom);
        if (Files.notExists(copyFrom)) {
            return;
        }
        Path copyTo = Paths.get(pathTo);
        if (Files.notExists(copyTo)) {
            Files.createDirectories(Files.isDirectory(copyTo) ? copyTo : copyTo.getParent());
        }
        Files.walkFileTree(copyFrom, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                if (Files.notExists(copyTo.resolve(copyFrom.relativize(dir)))) {
                    Files.createDirectory(copyTo.resolve(copyFrom.relativize(dir)));
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copyOneFile(file, copyTo.resolve(copyFrom.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyOneFile(Path copyFrom, Path copyTo) throws IOException {
        try (BufferedInputStream inStream = new BufferedInputStream(Files.newInputStream(copyFrom))) {
            try (BufferedOutputStream outStream = new BufferedOutputStream(Files.newOutputStream(copyTo))) {
                byte[] bytes = new byte[inStream.available()];
                int length;
                while ((length = inStream.read(bytes)) != -1) {
                    outStream.write(bytes, 0, length);
                }
            }
        }
    }
}
