package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

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
        for (File subFile : file.listFiles()) {
            count += removeWithFile(subFile.getPath());
        }
        file.delete();
        return count + 1;
    }


    public static int removeWithPath(String path) throws IOException {
        Path rootPath = Paths.get(path);
        if (!Files.exists(rootPath)) {
            return 0;
        }
        final int[] count = {0};
        try (Stream<Path> stream = Files.walk(rootPath)) {
            stream.sorted((p1, p2) -> p2.getNameCount() - p1.getNameCount())
                    .forEach(p -> {
                        try {
                            Files.delete(p);
                            count[0]++;
                        } catch (IOException e) {
                            // do nothing, file or directory not deleted
                        }
                    });
        }
        return count[0];
    }
}
