package ru.mail.polis.homework.retake.first;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 6 баллов
 */
public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 3 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        return deleteDirectory(file);
    }

    public static int deleteDirectory(File file) {
        int sum = 0;
        if (!file.exists()) {
            return 0;
        }

        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    sum += deleteDirectory(f);
                }
            }
        }

        if (file.delete()) {
            sum++;
        }
        return sum;
    }

    /**
     * С использованием Path
     * 3 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path pathFile = Paths.get(path);
        if (!Files.exists(pathFile)) {
            return 0;
        }
        return deleteDirectory(pathFile);
    }

    public static int deleteDirectory(Path path) throws IOException {
        int sum = 0;
        if (!Files.exists(path)) {
            return 0;
        }

        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path filePath : stream) {
                    sum += deleteDirectory(filePath);
                }
            }
        }

        Files.delete(path);
        sum++;
        return sum;
    }
}
