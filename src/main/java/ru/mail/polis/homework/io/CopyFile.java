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
    public static boolean copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return false;
        }

        Path dirFrom = Paths.get(pathFrom);
        Path dirTo = Paths.get(pathTo);

        if (Files.notExists(dirFrom)) {
            return false;
        }

        if (Files.isRegularFile(dirFrom)) {
            try {
                Files.createDirectories(dirTo.subpath(0, dirTo.getNameCount() - 1));
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
            copyFile(dirFrom, dirTo);
            return true;
        }

        try {
            Files.walkFileTree(dirFrom, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Files.createDirectories(getSamePathInTo(dir, dirFrom, dirTo));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs)
                        throws IOException {
                    copyFile(file, getSamePathInTo(file, dirFrom, dirTo));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    private static void copyFile(Path from, Path to) {
        try (
                BufferedInputStream fis = new BufferedInputStream(new FileInputStream(String.valueOf(from)), BUFFER_SIZE);
                BufferedOutputStream fos = new BufferedOutputStream(new FileOutputStream(String.valueOf(to)), BUFFER_SIZE);
        ) {
            int dataByte = fis.read();
            while (dataByte != END_OF_STREAM) {
                fos.write(dataByte);
                dataByte = fis.read();
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }
    }

    private static Path getSamePathInTo(Path needPathFr, Path dirFrom, Path dirTo) {
        if (dirFrom.getNameCount() == needPathFr.getNameCount()) {
            return dirTo;
        }

        return dirTo.resolve(needPathFr.subpath(dirFrom.getNameCount(), needPathFr.getNameCount()));
    }
}
