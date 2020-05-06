package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFromString, String pathToString) {
        final Path source = Paths.get(pathFromString);
        final Path dest = Paths.get(pathToString);
    
        if (Files.notExists(source)) {
            return pathToString;
        }
        try {
            Files.createDirectories(dest.getParent());
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.copy(dir, dest.resolve(source.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }
            
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    copyFileData(file, dest.resolve(source.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathToString;
    }
    
    private static void copyFileData(Path pathFrom, Path pathTo) {
        byte[] bytes = new byte[4096];
        try (InputStream in = Files.newInputStream(pathFrom)) {
            try (OutputStream out = Files.newOutputStream(pathTo)) {
                while (in.read(bytes) != -1) {
                    out.write(bytes);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
