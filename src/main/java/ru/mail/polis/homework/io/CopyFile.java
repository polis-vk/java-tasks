package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path pathSrc = Paths.get(pathFrom);
        if (Files.notExists(pathSrc)) {
            return;
        }
        Path pathDest = Paths.get(pathTo);
        try {
            Path theNearestDirectoryToDest = !Files.isDirectory(pathDest) ? pathDest.getParent() : pathDest;
            if (Files.notExists(theNearestDirectoryToDest)) {
                Files.createDirectories(theNearestDirectoryToDest);
            }
            copyFolder(pathSrc, pathDest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyFolder(Path pathFrom, Path pathTo) throws IOException {
        Files.walkFileTree(pathFrom, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Path pathToCopyOfDirectory = pathTo.resolve(pathFrom.relativize(dir));
                if (Files.notExists(pathToCopyOfDirectory)) {
                    Files.createDirectory(pathToCopyOfDirectory);
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                copyFile(file, pathTo.resolve(pathFrom.relativize(file)));
                return FileVisitResult.CONTINUE;
            }
        });
    }

    private static void copyFile(Path from, Path to) throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(from), StandardCharsets.UTF_8))) {
            try (BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(to), StandardCharsets.UTF_8))) {
                while (input.ready()) {
                    output.write(input.readLine());
                }
                output.flush();
            }
        }
    }
}
