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
        Path fromFilePath = Paths.get(pathFrom);
        Path toFilePath = Paths.get(pathTo);

        if (!Files.exists(fromFilePath)) {
            return "Path you want to copy from doesn't exist";
        }

        try {
            Files.createDirectories(toFilePath.getParent());

            Files.walkFileTree(fromFilePath, new SimpleFileVisitor<Path>() {
                Path currentFilePath = toFilePath.getParent();

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Files.createFile(currentFilePath.resolve(file.getFileName()));
                    try (InputStream fromInputStream = Files.newInputStream(file);
                         OutputStream toOutputStream =
                                 Files.newOutputStream(currentFilePath.resolve(file.getFileName()))) {
                        fromInputStream.transferTo(toOutputStream);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    currentFilePath = currentFilePath.resolve(dir.getFileName());
                    Files.createDirectory(currentFilePath);
                    return super.preVisitDirectory(dir, attrs);
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) {
                    currentFilePath = currentFilePath.getParent();
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return null;
    }

}
