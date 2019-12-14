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
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами.
     * С использованием File
     *
     * modified by БорискинМА
     * 11.12.19
     */
    public static int removeWithFile(String path) {
         return recursiveDelete(new File(path));
    }

    private static int recursiveDelete(File file) {
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int total = 0;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                total += recursiveDelete(f);
            }
        }
        file.delete();
        return total + 1;
    }

    private static int recursiveDelete(Path file) throws IOException {
        if (!Files.exists(file)) {
            return 0;
        }
        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return 1;
        }
        AtomicInteger total = new AtomicInteger(0);
        Files.list(file).forEach(tempPath ->
                total.addAndGet(removeWithPath(tempPath.toString())));
        Files.delete(file);
        return total.get() + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        try {
            return recursiveDelete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return 0;
    }
}
