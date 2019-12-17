package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        int countFilesDelete = 0;
        File dir = new File(path);

        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    countFilesDelete += removeWithFile(file.getPath());
                }
            }
            dir.delete();
            countFilesDelete++;
        }
        return countFilesDelete;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger countFilesDelete = new AtomicInteger(0);

        try (Stream<Path> tree = Files.walk(Paths.get(path))) {
            tree.sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                            countFilesDelete.getAndIncrement();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return countFilesDelete.get();
    }
}
