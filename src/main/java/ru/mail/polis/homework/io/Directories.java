package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File dir = new File(path);
        if(!dir.exists()){
            return 0;
        }
        return 1 + removeRecursiveFile(dir);
    }

    public static int removeRecursiveFile(File dir){
        int n = 0;
        if(dir.isDirectory()){
        for (File element: dir.listFiles()){
            n++;
            if (element.isDirectory()){
                n += removeRecursiveFile(element);
            }
            else {
                element.delete();
            }
        }
    }
        dir.delete();
        return n;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            return 0;
        }

        return 1 + removeRecursivePath(path);
    }
    public static int removeRecursivePath(String dir) throws IOException {
        final Path[] path = {Paths.get(dir)};
        final int[] n = {0};
        if(Files.isDirectory(path[0])){
            DirectoryStream <Path> stream = Files.newDirectoryStream(path[0]);
            stream.forEach(x -> {
                n[0]++;
                if(Files.isDirectory(x)){
                    try {
                        n[0] += removeRecursivePath(x.toString());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
                else {
                    try {
                        Files.delete(x);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
    }
        Files.delete(path[0]);
        return n[0];
    }
}
