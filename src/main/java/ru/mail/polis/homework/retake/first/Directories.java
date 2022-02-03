package ru.mail.polis.homework.retake.first;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * 6 баллов
 */
public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 3 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int count = 0;
        if (file.isDirectory()) {
            File[] filesInDir = file.listFiles();
            if (filesInDir != null) {
                for (File fileInDir : filesInDir) {
                    count += removeWithFile(fileInDir.getPath());
                }
            }
            if (file.delete()) {
                count += 1;
            }
        } else {
            if (file.delete()) {
                count = 1;
            }
        }
        return count;
    }

    /**
     * С использованием Path
     * 3 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (Files.notExists(dir)) {
            return 0;
        }
        int count;
        if (Files.isDirectory(dir)) {
            count = Files.list(dir).mapToInt(file -> {
                try {
                    return removeWithPath(file.toString());
                } catch (IOException e) {
                    return 0;
                }
            }).sum();
        } else {
            Files.delete(dir);
            return 1;
        }
        Files.delete(dir);
        return ++count;
    }
}
