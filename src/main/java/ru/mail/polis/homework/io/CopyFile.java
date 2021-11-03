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

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

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

        if (!Files.isDirectory(from) && !checkDirectory(to.resolve(from.relativize(from)).getParent())) {
            return "error";
        }

        try {
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    try (InputStream input = Files.newInputStream(file);
                         OutputStream output = Files.newOutputStream(to.resolve(from.relativize(file)))) {
                        copyFileContent(input, output);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return TERMINATE;
                    }
                    return CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    return checkDirectory(to.resolve(from.relativize(dir))) ? CONTINUE : TERMINATE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "done";
    }

    private static void copyFileContent(InputStream input, OutputStream output) throws IOException {
        byte[] buffer = new byte[1024];
        int blockSize;

        while ((blockSize = input.read(buffer)) > 0) {
            output.write(buffer, 0, blockSize);
        }

        output.flush();
    }

    private static boolean checkDirectory(Path dir) {
        if (!Files.exists(dir)) {
            try {
                Files.createDirectories(dir);
            } catch (IOException e) {
                e.printStackTrace();
                return false;
            }
        }
        return true;
    }
}
