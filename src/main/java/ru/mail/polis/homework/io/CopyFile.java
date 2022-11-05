package ru.mail.polis.homework.io;

import java.io.IOException;
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
    public static void copyFiles(String pathFromStr, String pathToStr) {
        Path pathFrom = Paths.get(pathFromStr);
        if (Files.notExists(pathFrom)) {
            return;
        }
        Path pathTo = Paths.get(pathToStr);

        try {
            Files.createDirectories(pathTo.getParent());
            Files.walkFileTree(pathFrom, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path pathDir, BasicFileAttributes attrs) throws IOException {
                    Files.createDirectory(pathTo.resolve(pathFrom.relativize(pathDir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path pathFile, BasicFileAttributes attrs) throws IOException {
                    Files.copy(pathFile, pathTo.resolve(pathFrom.relativize(pathFile)));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
