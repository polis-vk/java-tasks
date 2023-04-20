package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int count = 0;
        for (File tempFile : file.listFiles()) {
            count += removeWithFile(tempFile.getPath());
        }
        file.delete();
        count++;
        return count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (!Files.exists(dir)) {
            return 0;
        }
        return Files.walk(dir)
                .sorted(Comparator.reverseOrder())
                .map(Path::toFile)
                .mapToInt(f -> {
                    if (!f.exists()) {
                        return 0;
                    }
                    f.delete();
                    return 1;
                })
                .sum();
    }
}
