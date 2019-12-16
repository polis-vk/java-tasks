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
        File file = new File(path);
        if (file.exists()) {
            if (file.isDirectory()) {
                int amount = 1;
                for (File innerFile : file.listFiles()) {
                    amount += removeWithFile(innerFile.getAbsolutePath());
                }
                file.delete();
                return amount;
            } else {
                file.delete();
                return 1;
            }
        }
        return 0;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path file = Paths.get(path);
        if (Files.exists(file)) {
            AtomicInteger amount = new AtomicInteger(1);
            if (Files.isDirectory(file)) {
                try (Stream<Path> paths = Files.list(file)) {
                    paths.forEach(nextPath -> {
                        try {
                            if (Files.isDirectory(nextPath)) {
                                amount.addAndGet(removeWithPath(nextPath.toString()));
                            } else {
                                amount.incrementAndGet();
                            }
                            Files.deleteIfExists(nextPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                Files.delete(file);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return amount.get();
        } else {
            return 0;
        }
    }
}
