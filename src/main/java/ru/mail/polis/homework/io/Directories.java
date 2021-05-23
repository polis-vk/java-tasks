package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        int count = 0;

        if (!file.exists()) {
            return count;
        }

        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                if (f.isFile()) {
                    f.delete();
                    count++;
                } else if (f.isDirectory()) {
                    count += removeWithFile(f.getPath());
                }
            }
        }

        file.delete();
        return ++count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        int count = 0;

        if (Files.notExists(filePath)) {
            return count;
        }

        if (Files.isDirectory(filePath)) {
            try (DirectoryStream<Path> dirStream = Files.newDirectoryStream(filePath)) {
                for (Path fPath : dirStream) {
                    if (Files.isRegularFile(fPath)) {
                        Files.delete(fPath);
                        count++;
                    } else if (Files.isDirectory(fPath)) {
                        count += removeWithPath(fPath.toString());
                    }
                }
            }
        }

        Files.delete(filePath);
        return ++count;
    }
}
