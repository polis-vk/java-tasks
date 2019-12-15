package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File dirFile = new File(path);
        if (dirFile.isDirectory()) {
            File[] children = dirFile.listFiles();
            for (File child : children) {
                int success = removeWithFile(child.getName());
                if (success == -1) {
                    return -1;
                }
            }
        }
        return 0;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path dirPath = Paths.get(path);
        try {
            Files.walk(dirPath)
                    .sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(File::delete);
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return 0;
    }
}
