package ru.mail.polis.homework.files;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;

public class Directories {

    private static int result = 0;

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File dirFile = new File(path);
        if (!dirFile.exists()) return 0;
        removeFile(dirFile);
        result++;
        return result;
    }

    private static boolean removeFile(File path){
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                result++;
                ret = ret && removeFile(f);
            }
        }
        return ret && path.delete();
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
                    .forEach(file -> {
                        result++;
                        file.delete();
                    });
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return result;
    }
}
