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

        File files = new File(path);
        if (!files.exists()) {
            return 0;
        } else if (files.isFile()) {
            return files.delete() ? 1 : 0;
        }
        int count = 0;
        for (File file : files.listFiles()) {
            count += removeWithFile(file.getPath());
        }

        return files.delete() ? ++count : count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (Files.notExists(filePath)) {
            return 0;
        }
        if (Files.isRegularFile(filePath)) {
            Files.delete(filePath);
            return 1;
        }
        int count = 0;
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath)) {
            for (Path pathFile : directoryStream) {
                count += removeWithPath(pathFile.toString());
            }
        }
        Files.delete(filePath);
        return ++count;
    }
}
