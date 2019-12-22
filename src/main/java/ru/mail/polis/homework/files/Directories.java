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
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        int count = 0;
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                for (File f : file.listFiles()) {
                    count += removeWithFile(f.getPath());
                }
            }
            file.delete();
            count++;
        }
        return count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger count = new AtomicInteger();
        try (Stream<Path> tree = Files.walk(Paths.get(path))) {
            tree.sorted(Comparator.reverseOrder())
                    .forEach(p -> {
                        try {
                            Files.deleteIfExists(p);
                            count.getAndIncrement();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return count.get();
    }
}
