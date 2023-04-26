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
        if (pathFrom == null || pathTo == null) {
            return;
        }

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (Files.notExists(from)) {
            return;
        }

        try {
            Files.createDirectories(to.getParent());
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(to.resolve(from.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    copy(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        try (BufferedReader input = new BufferedReader(new InputStreamReader(Files.newInputStream(from)));
             BufferedWriter output = new BufferedWriter(new OutputStreamWriter(Files.newOutputStream(to)))) {
            while (input.ready()) {
                output.write(input.readLine());
            }
        }
    }
}
