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
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (Files.notExists(from)) {
            return null;
        }
        try {
            Files.createDirectories(to.getParent());
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    copySingleFile(file, to.resolve(from.relativize(file)));
                    return FileVisitResult.CONTINUE;
                }
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Files.copy(dir, to.resolve(from.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException e) {

        }
        return pathTo;
    }

    private static void copySingleFile(Path in, Path out) {
        try {
            Files.createFile(out);
            try (InputStream inputStream = Files.newInputStream(in)) {
                try (OutputStream outStream = Files.newOutputStream(out)) {
                    /* Не плагиат, а использование материала из лекции */
                    byte[] buffer = new byte[1024];
                    int blockSize;
                    while((blockSize = inputStream.read(buffer))>0) {
                        outStream.write(buffer, 0, blockSize);
                    }
                    /* Альтернативный вариант без присвоения в while, но с двумя read в коде
                    int blockSize = inputStream.read(buffer);
                    while((blockSize>0) {
                        outStream.write(buffer, 0, blockSize);
                        blockSize= inputStream.read(buffer);
                    }
                     */
                }
            }
        }
        catch (IOException e) {
        }
    }

}
