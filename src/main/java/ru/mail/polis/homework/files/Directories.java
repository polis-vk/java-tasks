package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        int count = 0;
        File dir = new File(path);
        if (dir.exists()) {
            if (dir.isDirectory()) {
                for (File file : dir.listFiles()) {
                    count += removeWithFile(file.getPath());
                }
            }
            dir.delete();
            count++;
        }
        return count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        int count = 0;
        Path dir = Path.of(path);
        if (Files.exists(dir)) {
            if (Files.isDirectory(dir)) {
                count = Files.list(dir).mapToInt(dirPath -> removeWithFile(dirPath.toString())).sum();
            }
            Files.delete(dir);
            count++;
        }
        return count;
    }
}
