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

    public static int count;

    public static int removeWithFile(String path) {
        File sourcePath = new File(path);
        if (!sourcePath.exists()) {
            return 0;
        }
        count = 0;
        deleteWithFile(sourcePath);
        return count;
    }

    private static void deleteWithFile(File path) {
        File[] files = path.listFiles();
        if (files != null) {
            for (File file : files) {
                deleteWithFile(file);
            }
        }
        if (path.delete()) {
            count++;
        }
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path sourcePath = Paths.get(path);
        if (Files.notExists(sourcePath)) {
            return 0;
        }
        count = 0;
        deleteWithPath(sourcePath);
        return count;
    }

    private static void deleteWithPath(Path path) throws IOException {
        if (Files.isDirectory(path)) {
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(path)) {
                for (Path file : stream) {
                    deleteWithPath(file);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Files.delete(path);
        count++;
    }
}
