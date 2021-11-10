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

        try {
            checkPathFrom(from, to);
            visitFiles(from, to);
        } catch (IOException e) {
            e.printStackTrace();
            return "error";
        }
        return "done";
    }

    private static void visitFiles(Path from, Path to) throws IOException {
        Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copyFileContent(file, to.resolve(from.relativize(file)));
                return CONTINUE;
            }

            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path current = to.resolve(from.relativize(dir));
                if (!Files.exists(current)) {
                    Files.createDirectory(current);
                }
                return CONTINUE;
            }
        });
    }

    private static void copyFileContent(Path in, Path out) throws IOException {
        try (InputStream input = Files.newInputStream(in);
             OutputStream output = Files.newOutputStream(out)) {
            byte[] buffer = new byte[1024];
            int blockSize;

            while ((blockSize = input.read(buffer)) > 0) {
                output.write(buffer, 0, blockSize);
            }
        }
    }

    private static void checkPathFrom(Path from, Path to) throws IOException {
        if (!Files.isDirectory(from)) {
            checkAndCreateDirectories(to.getParent());
            return;
        }

        checkAndCreateDirectories(to);
    }

    private static void checkAndCreateDirectories(Path dir) throws IOException {
        if (!Files.exists(dir)) {
            Files.createDirectories(dir);
        }
    }
}
