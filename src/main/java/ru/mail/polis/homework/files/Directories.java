package ru.mail.polis.homework.files;

import java.io.File;

public class Directories {
    public static void main(String[] args) {
        int a = Directories.removeWithFile("C:\\Users\\maksim\\Desktop\\q\\qwe");
        System.out.println(a);
    }

    private static int deleted = 0;
    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директорий по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) return 0;

        if (file.isFile()) {
            if (file.delete()) deleted++;
        } else if (file.isDirectory())
            removeWithFile(file);

        return deleted;
    }

    private static void removeWithFile(File file) {

        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    if (child.delete()) deleted++;
                } else if (child.isDirectory()) {
                    removeWithFile(child);
                }
            }
        }

        if(file.delete()) deleted++;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        return 0;
    }

    private static void removeWithPath(File file) {

    }

}
