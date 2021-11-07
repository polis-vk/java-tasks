package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    private static final int BUFFER_SIZE = 1024;
    private static final int END_OF_STREAM = -1;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }

        Path dirFrom = Paths.get(pathFrom);
        if (Files.notExists(dirFrom)) {
            return null;
        }
        Path dirTo = Paths.get(pathTo);

        try {
            Files.createDirectories(dirTo.subpath(0, dirTo.getNameCount() - 1));

            if (Files.isRegularFile(dirFrom)) {
                copyFile(dirFrom, dirTo);
                return null;
            }

            Files.walkFileTree(dirFrom, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Files.createDirectory(dirTo.resolve(dirFrom.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    copyFile(file, dirTo.resolve(dirFrom.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

    private static void copyFile(Path from, Path to) {
        try (
                BufferedInputStream fis = new BufferedInputStream(Files.newInputStream(from), BUFFER_SIZE);
                BufferedOutputStream fos = new BufferedOutputStream(Files.newOutputStream(to), BUFFER_SIZE);
        ) {
            int dataByte;
            while ((dataByte = fis.read()) != END_OF_STREAM) {
                fos.write(dataByte);
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }
}