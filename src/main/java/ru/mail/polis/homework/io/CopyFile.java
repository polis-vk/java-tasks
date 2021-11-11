package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return "Path can't be null";
        }

        Path pFrom = Paths.get(pathFrom);
        Path pTo = Paths.get(pathTo);

        if (!Files.exists(pFrom)) {
            return "pathFrom doesn't exist";
        }

        try {
            Files.createDirectories(Files.isRegularFile(pFrom)? pTo.getParent() : pTo);

            Files.walkFileTree(pFrom, new SimpleFileVisitor<Path>() {
                private final int BUFFER_SIZE = 4096;

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path targetDir = pTo.resolve(pFrom.relativize(dir));
                    if (!Files.exists(targetDir)) {
                        Files.createDirectory(targetDir);
                        Files.walkFileTree(dir, this);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    InputStream input = Files.newInputStream(file);
                    OutputStream output = Files.newOutputStream(pTo.resolve(pFrom.relativize(file)));

                    byte[] buf = new byte[BUFFER_SIZE];
                    while (true) {
                        int readSize = input.read(buf);
                        if (readSize <= 0) {
                            break;
                        }
                        output.write(buf, 0, readSize);
                    }

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            return e.getMessage();
        }

        return "ok";
    }
}
