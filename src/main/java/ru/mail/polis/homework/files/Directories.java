package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {

    private static int count = 0;
    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        count = 0;

        File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        if (file.isDirectory()) {
            deleteInDirectory(file);
        }

        file.delete();
        count++;

        return count;
    }

    private static void deleteInDirectory(File file) {
        for (File child : Objects.requireNonNull(file.listFiles())) {
            if (child.isDirectory()) {
                deleteInDirectory(child);
            }

            child.delete();
            count++;
        }
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger cnt = new AtomicInteger();

        try (Stream<Path> route = Files.walk(Paths.get(path))) {
            route.sorted(Comparator.reverseOrder())
                    .forEach(currentPath -> {
                        try {
                            Files.delete(currentPath);
                            cnt.getAndIncrement();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return cnt.get();
    }
}
