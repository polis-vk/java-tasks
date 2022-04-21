package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
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
        File itemPath = new File(path);
        if (!itemPath.exists()) {
            return 0;
        }
        int count = 0;
        if (itemPath.isDirectory()) {
            for (File file : itemPath.listFiles()) {
                count += removeWithFile(file.getPath());
            }
        }
        itemPath.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path itemPath = Paths.get(path);
        if (Files.notExists(itemPath)) {
            return 0;
        }
        int count = 0;
        if (Files.isDirectory(itemPath)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(itemPath)) {
                for (Path file : stream) {
                    count += removeWithPath(file.toString());
                }
            } catch (NoSuchFileException e) {
                e.printStackTrace();
            }
        }
        try {
            Files.delete(itemPath);
        } catch (NoSuchFileException e) {
            e.printStackTrace();
            return 0;
        }
        return ++count;
    }
}
