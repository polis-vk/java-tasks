package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicReference;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File currentFile = new File(path);
        if (!currentFile.exists()) {
            return 0;
        }
        if (currentFile.isFile()) {
            currentFile.delete();
            return 1;
        }
        int counter = 0;
        for (File childFile : currentFile.listFiles()) {
            if (childFile.isDirectory()) {
                counter += removeWithFile(childFile.toString());
            }
            else {
                childFile.delete();
                counter++;
            }
        }
        currentFile.delete();
        return counter + 1;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path removingPath = Paths.get(path);
        if (Files.notExists(removingPath)) {
            return 0;
        }
        AtomicReference<Integer> counter = new AtomicReference<>(0);
        Files.walk(removingPath)
                .sorted(Comparator.reverseOrder())
                .forEachOrdered(file -> {
                    try {
                        Files.delete(file);
                        counter.getAndSet(counter.get() + 1);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
        return counter.get();
    }
}
