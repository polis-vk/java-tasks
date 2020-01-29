package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        int deletedCounter = 0;
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    if (file.isDirectory()) {
                        deletedCounter += removeWithFile(file.getPath());
                    } else {
                        file.delete();
                        deletedCounter++;
                    }
                }
            }
            dir.delete();
            return ++deletedCounter;
        }
        return 0;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger deletedCounter = new AtomicInteger();
        Path dir = Paths.get(path);
        if (Files.exists(dir)) {
            if (Files.isDirectory(dir)) {
                try {
                    Files.list(dir).forEach(file -> {
                        if (Files.isDirectory(file)) {
                            deletedCounter.addAndGet(removeWithFile(file.toString()));
                        } else {
                            try {
                                Files.delete(file);
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                            deletedCounter.getAndIncrement();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.delete(dir);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return deletedCounter.incrementAndGet();
        }
        return 0;
    }
}
