package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String pathStr) {
        if (pathStr == null) {
            return 0;
        }

        File file = new File(pathStr);
        if (!file.exists()) {
            return 0;
        }

        List<File> filesToDelete = new ArrayList<>();
        filesToDelete.add(file);
        for (int i = 0; i < filesToDelete.size(); i++) {
            File curFile = filesToDelete.get(i);
            if (curFile.isDirectory()) {
                filesToDelete.addAll(Arrays.asList(curFile.listFiles()));
            }
        }

        for (int i = filesToDelete.size() - 1; i > -1; i--) {
            filesToDelete.get(i).delete();
        }

        return filesToDelete.size();
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String pathStr) throws IOException {
        if (pathStr == null) {
            return 0;
        }

        Path path = Paths.get(pathStr);
        if (Files.notExists(path)) {
            return 0;
        }

        AtomicInteger atomicCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path pathDir, IOException e) throws IOException {
                Files.delete(pathDir);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path pathFile, BasicFileAttributes attrs) throws IOException {
                Files.delete(pathFile);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });

        return atomicCount.get();
    }
}
