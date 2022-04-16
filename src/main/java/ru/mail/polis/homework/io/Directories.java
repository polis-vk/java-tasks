package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.NoSuchFileException;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File item = new File(path);
        if (!item.exists()) {
            return 0;
        }
        int count = 0;
        if (item.isDirectory()) {
            for (File entry : item.listFiles()) {
                count += removeWithFile(entry.getPath());
            }
        }
        item.delete();
        return count + 1;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path item = Paths.get(path);
        int count = 0;
        if (Files.isDirectory(item)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(item)) {
                for (Path entry : stream) {
                    count += removeWithPath(entry.toString());
                }
            } catch (NoSuchFileException e) {
                return 0;
            }
        }
        try {
            Files.delete(item);
        } catch (NoSuchFileException e) {
            return 0;
        }
        return count + 1;
    }
}
