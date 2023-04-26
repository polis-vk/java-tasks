package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int deletedCounter = 0;
        if (file.isDirectory()) {
            for (File temp : file.listFiles()) {
                deletedCounter += removeWithFile(temp.getPath());
            }
        }
        file.delete();
        return ++deletedCounter;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        return 0;
    }
}
