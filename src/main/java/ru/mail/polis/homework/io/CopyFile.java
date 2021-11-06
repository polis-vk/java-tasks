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
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null || Files.notExists(Paths.get(pathFrom))) {
            return null;
        }
        try {
            Files.walkFileTree(Path.of(pathFrom), new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Path tmp = Paths.get(pathTo, String.valueOf((Paths.get(pathFrom).relativize(dir))));
                    Files.createDirectories(tmp);
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Path tmp = Paths.get(pathTo, String.valueOf((Paths.get(pathFrom).relativize(file))));
                    try {
                        Files.createFile(tmp);
                    } catch (IOException exception) {
                        Files.createDirectories(tmp.getParent());
                        Files.createFile(tmp);
                    }
                    copyFile(file, tmp);
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (InputStream input = Files.newInputStream(pathFrom);
             OutputStream output = Files.newOutputStream(pathTo)) {
            byte[] b = new byte[(int) Files.size(pathFrom)];
            input.read(b);
            output.write(b);
            output.flush();
        }
    }
}
