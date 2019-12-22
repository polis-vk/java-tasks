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
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int count = 0;
        for (File removeFile : file.listFiles()) {
            count += removeWithFile(removeFile.getPath());
        }
        file.delete();
        return count + 1;
    }

    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path)
                ;
        if (!Files.exists(file)) {
            return 0;
        }

        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return 1;
        }

        AtomicInteger count = new AtomicInteger();
        try (Stream<Path> stream = Files.list(file)) {
            stream.forEach(curFile -> {
                try {
                    count.addAndGet(removeWithPath(curFile.toString()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.delete(file);
        return count.get() + 1;
    }
}
