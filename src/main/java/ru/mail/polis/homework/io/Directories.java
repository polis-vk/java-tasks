package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.File;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        int numberDeletedFiles = 0;
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        File[] files = file.listFiles();
        if (files != null) {
            for (File descendant : files) {
                numberDeletedFiles += removeWithFile(descendant.getPath());
            }
        }
        file.delete();
        numberDeletedFiles++;
        return numberDeletedFiles;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        return 0;
    }
}
