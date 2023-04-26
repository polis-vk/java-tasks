package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (path == null || !file.exists()) {
            return 0;
        }
        int count = 0;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                count += removeWithFile(f.getPath());
            }
        }
        file.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path curPath = Paths.get(path);
        if (Files.notExists(curPath)) {
            return 0;
        }
        int count = 0;
        if (Files.isDirectory(curPath)) {
            for (Path file : Files.newDirectoryStream(curPath)) {
                if (Files.isDirectory(file)) {
                    count += removeWithPath(file.toString());
                } else {
                    Files.delete(file);
                    count += Files.exists(curPath) ? 1 : 0;
                }
            }
        }
        try {
            Files.delete(curPath);
        } catch (NoSuchFileException e) {
            System.out.println("NoSuchFileException");
            return 0;
        }
        return ++count;
    }
}
