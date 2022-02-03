package ru.mail.polis.homework.retake.first;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 5 баллов
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int countDeleted = 0;


        if (!file.isFile()) {
            for (File f : Objects.requireNonNull(file.listFiles())) {
                countDeleted += removeWithFile(f.getPath());
            }

        }
        if (!file.delete()) {
            throw new RuntimeException();
        }
        ++countDeleted;
        return countDeleted;
    }


    /**
     * С использованием Path
     * 5 баллов
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (!Files.exists(dir)) {
            return 0;
        }

        if (!Files.isDirectory(dir)) {
            Files.delete(dir);
            return 1;
        }

        int countDeleted = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                countDeleted += removeWithPath(entry.toString());
            }

            Files.delete(dir);
            return ++countDeleted;
        }
    }
}
