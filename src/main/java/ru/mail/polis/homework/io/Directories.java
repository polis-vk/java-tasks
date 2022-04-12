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
        return removeFile(new File(path));
    }

    private static int removeFile(File file) {
        if (file.isFile()) {
            file.delete();
            return 1;
        }

        File[] files = file.listFiles();
        if (files == null) {
            return 0;
        }
        int result = 0;
        for (File current : files) {
            result += removeFile(current);
        }
        file.delete();
        return result + 1;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        int result = 0;
        if (Files.isRegularFile(directory)) {
            Files.delete(directory);
            return 1;
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(Paths.get(path))) {
            for (Path file : directoryStream) {
                if (Files.isDirectory(file)) {
                    result += removeWithPath(file.toString());
                } else {
                    Files.delete(file);
                    result++;
                }
            }
        } catch (NoSuchFileException e) {
            return 0;
        }
        Files.delete(directory);
        return result + 1;
    }
}
