package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File dirToRemove = new File(path);
        if (!dirToRemove.exists()) {
            return 0;
        }
        if (dirToRemove.isFile()) {
            dirToRemove.delete();
            return 1;
        }
        int count = 0;
        for (File file : dirToRemove.listFiles()) {
            if (file.isFile()) {
                file.delete();
                count++;
            } else {
                count += removeWithFile(file.toString());
            }
        }
        dirToRemove.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path pathToRemove = Paths.get(path);
        if (Files.notExists(pathToRemove)) {
            return 0;
        }
        if (Files.isRegularFile(pathToRemove)) {
            Files.delete(pathToRemove);
            return 1;
        }
        AtomicInteger count = new AtomicInteger();
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(pathToRemove)) {
            directoryStream.forEach(currentPath -> {
                try {
                    if (Files.isRegularFile(currentPath)) {
                        Files.delete(currentPath);
                        count.getAndIncrement();
                    } else {
                        count.addAndGet(removeWithPath(currentPath.toString()));
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.delete(pathToRemove);
        return count.incrementAndGet();
    }
}
