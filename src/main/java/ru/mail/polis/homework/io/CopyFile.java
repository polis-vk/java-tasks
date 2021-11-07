package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.FileVisitor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

import static java.nio.file.FileVisitResult.CONTINUE;
import static java.nio.file.FileVisitResult.TERMINATE;

public class CopyFile {

    public final static String ERROR = "Error";
    public final static String COMPLETED = "Done";
    private final static int BUFFER_SIZE = 1024;
    private final static byte[] BUFFER = new byte[BUFFER_SIZE];

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (!Files.exists(from)) {
            return ERROR;
        }
        Path to = Paths.get(pathTo);
        if (Files.isRegularFile(from) && !createDirIfPossible(to.getParent())) {
            return ERROR;
        }
        try {
            Files.walkFileTree(from, createCopyVisitor(from, to));
        } catch (IOException e) {
            e.printStackTrace();
            return ERROR;
        }
        return COMPLETED;
    }

    private static void copyFileContent(InputStream input, OutputStream output) throws IOException {
        int readSize;
        while ((readSize = input.read(BUFFER)) > 0) {
            output.write(BUFFER, 0, readSize);
        }
        output.flush();
    }

    private static FileVisitor<Path> createCopyVisitor(Path from, Path to) {
        return new SimpleFileVisitor<Path>() {
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
                return createDirIfPossible(to.resolve(from.relativize(dir))) ? CONTINUE : TERMINATE;
            }
        };
    }

    private static boolean createDirIfPossible(Path dir) {
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