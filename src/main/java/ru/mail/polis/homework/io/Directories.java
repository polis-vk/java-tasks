package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */

    public static int removeWithFile(String path) {
        File sourcePath = new File(path);
        if (!sourcePath.exists()) {
            return 0;
        }
        int count = 0;
        File[] files = sourcePath.listFiles();
        if (files != null) {
            for (File file : files) {
                count += removeWithFile(file.toString());
            }
        }
        if (sourcePath.delete()) {
            return ++count;
        }
        return count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) {
        Path sourcePath = Paths.get(path);
        if (Files.notExists(sourcePath)) {
            return 0;
        }
        int count = 0;
        if (Files.isDirectory(sourcePath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(sourcePath)) {
                for (Path file : stream) {
                    count += removeWithPath(file.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.delete(sourcePath);
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
        return ++count;
    }
}
