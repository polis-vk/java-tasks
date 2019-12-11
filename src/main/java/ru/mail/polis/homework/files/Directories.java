package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
        int total_f = 0;
        if (file.isDirectory()) {
            for (File f : file.listFiles()) {
                total_f += recursiveDelete(f);
            }
        }
        file.delete();
        return total_f + 1;
    }

    private static int recursiveDelete(Path file) throws IOException {
        if (!Files.exists(file)) {
            return 0;
        }
        int total_p = 0;
        if (Files.exists(file)) {
            if (Files.isDirectory(file)) {
                total_p = Files.list(file).mapToInt(Directories::removeFolder).sum();
            }
            Files.delete(file);
            total_p++;
        }
        return total_p;
    }

    private static int removeFolder(Path tempPath) {
        return removeWithFile(tempPath.toString());
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        try {
            return recursiveDelete(Paths.get(path));
        } catch (IOException e) {
            e.printStackTrace();
            return 0;
        }
    }
}
