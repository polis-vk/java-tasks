package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    private static int counter = 0;

    public static int removeWithFile(String path) {
        File dirToBeRemoved = new File(path);
        if (dirToBeRemoved.isDirectory()) {
            if (dirToBeRemoved.list().length == 0) {
                dirToBeRemoved.delete();
                counter++;
            } else {
                String[] filesInside = dirToBeRemoved.list();
                for (String file : filesInside) {
                    removeWithFile(dirToBeRemoved + "\\" + file);
                    counter++;
                }
                dirToBeRemoved.delete();
                counter++;
            }
        }
        dirToBeRemoved.delete();
        return counter;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        return 0;
    }
}
