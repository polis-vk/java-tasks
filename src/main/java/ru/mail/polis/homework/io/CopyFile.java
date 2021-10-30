package ru.mail.polis.homework.io;

import java.io.File;
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
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);

        if (!Files.exists(from)) {
            return "error";
        }

        Path to = Paths.get(pathTo);

        if (!Files.isDirectory(from)) {
            Path targetDir = to.resolve(from.relativize(from)).getParent();
            if (!Files.exists(targetDir)) {
                try {
                    Files.createDirectories(targetDir);
                } catch (IOException e) {
                    e.printStackTrace();
                    return "error";
                }
            }
        }

        try {
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try (InputStream input = Files.newInputStream(file);
                         OutputStream output = Files.newOutputStream(to.resolve(from.relativize(file)))) {

                        byte[] buffer = new byte[1024];
                        int blockSize = input.read(buffer);

                        while (blockSize > 0) {
                            output.write(buffer, 0, blockSize);
                            blockSize = input.read(buffer);
                        }

                        output.flush();
                    } catch (IOException e) {
                        e.printStackTrace();
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    if (!Files.exists(dir)) {
                        return FileVisitResult.CONTINUE;
                    }
                    Path targetDir = to.resolve(from.relativize(dir));
                    if (!Files.exists(targetDir)) {
                        try {
                            Files.createDirectories(targetDir);
                        } catch (IOException e) {
                            e.printStackTrace();
                            return FileVisitResult.TERMINATE;
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "done";
    }
}
