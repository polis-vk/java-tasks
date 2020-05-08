package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File directory = new File(path);
        int count = 0;

        if (!directory.exists()) {
            return 0;
        }

        if (directory.isFile() && directory.delete()) {
            return 1;
        }

        File[] listFiles = directory.listFiles();
        if (listFiles == null) {
            return count;
        }

        for (File file : listFiles) {
            count += removeWithFile(file.toString());
        }

        return directory.delete() ? ++count : count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        int count = 0;

        if (Files.notExists(directory)) {
            return 0;
        }

        if (Files.isDirectory(directory)) {
            try (DirectoryStream<Path> entries = Files.newDirectoryStream(directory)) {
                for (Path entry : entries) {
                    count += removeWithPath(entry.toString());
                }
            }
        }

        Files.delete(directory);

        return ++count;
    }
}
