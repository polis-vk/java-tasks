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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path dirFrom = Paths.get(pathFrom);
        Path dirTo = Paths.get(pathTo);
        try {
            if (!Files.exists(dirTo)) {
                Files.createDirectories(Files.isDirectory(dirTo) ? dirTo : dirTo.getParent());
            }
            Files.walkFileTree(dirFrom, new SimpleFileVisitor<Path>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(dirTo.resolve(dirFrom.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path copiedFile = Files.createFile(dirTo.resolve(dirFrom.relativize(file)));
                    copyFileContent(file, copiedFile);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.err.println(e.getMessage());
        }
    }

    public static void copyFileContent(Path fileFrom, Path fileTo) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(fileFrom)) {
            try (BufferedWriter bw = Files.newBufferedWriter(fileTo)) {
                String line;
                while ((line = br.readLine()) != null) {
                    bw.write(line);
                }
            }
        }
    }

}
