package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        int count = 0;
        if(file.isDirectory()){
            count += clearDirectory(file);
            file.delete();
            count++;
        }
        else{
            if (file.isFile()){
                file.delete();
                count++;
            }
        }
        return count;
    }

    private static  int clearDirectory(File file){
        int count = 0;
        for (File cur :
                file.listFiles()) {
            if (cur.isDirectory()){
                count += clearDirectory(cur);
            }
            cur.delete();
            count++;
        }
        return count;
    }
    /**
     * С использованием Path
     */
    public static int removeWithPath(String path){
        AtomicInteger count = new AtomicInteger(0);
        try (Stream<Path> pathStream = Files.walk(Paths.get(path))) {
            pathStream.sorted(Comparator.reverseOrder()).forEach(
                    curPath -> {
                        try {
                            Files.delete(curPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        count.getAndIncrement();
                    }
            );
        } catch (IOException e) {
            e.printStackTrace();
        }
        return count.get();
    }
}