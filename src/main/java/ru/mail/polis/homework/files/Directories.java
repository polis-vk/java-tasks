package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по
     * заданному пути. Метод должен возвращать количество удаленных файла и
     * директорий. Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(final String path) {
        final File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            file.delete();

            return 1;
        }

        int counter = 0;

        for (final File f : file.listFiles()) {
            counter += removeWithFile(f.getAbsolutePath());
        }

        file.delete();
        ++counter;

        return counter;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(final String path) {
        Path filePath = Paths.get(path);

        if (!Files.exists(filePath)) {
            return 0;
        }

        if (Files.isRegularFile(filePath)) {
            try {
                Files.delete(filePath);

                return 1;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        AtomicInteger counter = new AtomicInteger(0);

        try {
            Stream<Path> paths = Files.list(filePath);

            paths.forEach(
                p -> {
                    counter.addAndGet(removeWithPath(p.toString()));
                }
            );

            paths.close();
            Files.delete(filePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

        counter.incrementAndGet();

        return counter.get();
    }
}
