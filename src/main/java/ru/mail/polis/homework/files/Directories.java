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
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        return remove(new File(path));
    }

    private static int remove(File file) {
        if(file == null || !file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int result = 0;
        for (File tmp: file.listFiles()) {
            result += remove(tmp);
        }
        file.delete();
        return result + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);

        if(!Files.exists(file)) {
            return 0;
        }

        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return 1;
        }
        AtomicInteger counter = new AtomicInteger(0);
        try (Stream<Path> paths = Files.list(file)) {
            paths.forEach(nextPath -> {
                try {
                    counter.addAndGet(removeWithPath(nextPath.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }

        Files.delete(file);
        return counter.get() + 1;

    }

}
