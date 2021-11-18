package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    private final static int BUFFER_SIZE = 1024;

    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }
        if (pathFrom.isEmpty() || pathTo.isEmpty()) {
            return null;
        }
        if (pathFrom.equals(pathTo)) {
            return null;
        }
        Path src = Paths.get(pathFrom);
        Path dest = Paths.get(pathTo);
        try {
            Files.createDirectories(dest.getParent());
            Files.walkFileTree(Paths.get(pathFrom), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    Path dirDest = dest.resolve(src.relativize(dir));
                    try {
                        Files.createDirectory(dirDest);
                    } catch (IOException e) {
                        if (Files.exists(dirDest)) {
                            return FileVisitResult.CONTINUE;
                        } else {
                            return FileVisitResult.SKIP_SUBTREE;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path fileSrc, BasicFileAttributes attrs) throws IOException {
                    Path fileDest = dest.resolve(src.relativize(fileSrc));
                    copyFile(fileSrc, fileDest);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return null;
        }
        return null;
    }

    private static void copyFile(Path src, Path dest) throws IOException {
        byte[] buffer = new byte[BUFFER_SIZE];
        InputStream input = Files.newInputStream(src);
        OutputStream output = Files.newOutputStream(dest);
        int read = input.read(buffer);
        while (read != -1) {
            output.write(buffer, 0, read);
            read = input.read(buffer);
        }
        input.close();
        output.close();
    }

}
