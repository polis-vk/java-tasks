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
        File filePath = new File(path);
        int result = 0;

        if (filePath.isDirectory()) {
            for (File subfile : filePath.listFiles()) {
                result += removeWithFile(subfile.getPath());
            }
        }

        if (filePath.delete()) {
            result++;
        }

        return result;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger result = new AtomicInteger(0);

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.sorted(Comparator.reverseOrder()).forEach(subFile ->{
                try {
                    Files.delete(subFile);
                    result.getAndIncrement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result.get();
    }
}
