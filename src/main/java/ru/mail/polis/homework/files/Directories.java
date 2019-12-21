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
        File forDelete = new File(path);

        if (forDelete.isFile()) {
            forDelete.delete();
            return 1;
        }

        int res = 0;

        if (forDelete.isDirectory()) {
            for (File item: forDelete.listFiles()) {
                res += removeWithFile(item.getPath());
            }
        } else {
            return 0;
        }

        forDelete.delete();
        return res + 1;

    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path forDelete = Paths.get(path);

        if (Files.isRegularFile(forDelete)) {
            Files.delete(forDelete);
            return 1;
        }

        AtomicInteger res = new AtomicInteger();

        if (Files.isDirectory(forDelete)) {
            try (Stream<Path> walk = Files.walk(forDelete)) {
                walk.sorted(Comparator.reverseOrder())
                        .map(Path::toFile)
                        .forEach(x -> {
                            x.delete();
                            res.incrementAndGet();
                        });
            }
        }else {
            return 0;
        }

        return res.get();

    }
}
