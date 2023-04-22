package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        AtomicInteger deletedAmount = new AtomicInteger();
        File fileForDeleting = new File(path);
        if (fileForDeleting.exists()) {
            if (fileForDeleting.isDirectory()) {
                File[] content = fileForDeleting.listFiles();
                Arrays.stream(content).forEach(file -> deletedAmount.addAndGet(removeWithFile(file.getPath())));
            }

            deletedAmount.getAndIncrement();
            fileForDeleting.delete();
        }

        return deletedAmount.get();
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        AtomicInteger deletedAmount = new AtomicInteger();

        Path pathForDeleting = Paths.get(path);
        if (Files.isDirectory(pathForDeleting)) {
            try (Stream<Path> content = Files.list(pathForDeleting)) {
                content.forEach(filePath ->
                {
                    try {
                        deletedAmount.addAndGet(removeWithPath(filePath.toString()));
                    } catch (IOException e) {
                        System.out.println("Input|Output exception");
                    }
                });
            }
        }

        try {
            Files.delete(pathForDeleting);
            deletedAmount.getAndIncrement();
        } catch (NoSuchFileException exception) {
            System.out.println("No such file");
        }
        return deletedAmount.get();
    }
}
