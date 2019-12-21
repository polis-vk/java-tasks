package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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
        if (!file.exists()){
            return 0;
        }
        if (file.isDirectory()) {
            count+=enterDirectory(file);
        }
        file.delete();
        count++;
        return count;
    }

    private static int enterDirectory(File directory) {
        int count = 0;
        for (File subFile : directory.listFiles()) {
            if (subFile.isDirectory()) {
                count += enterDirectory(subFile);
            }
            subFile.delete();
            count++;
        }
        return count;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        AtomicInteger count = new AtomicInteger();
        Path file = Paths.get(path);
        if (Files.notExists(file)){
            return 0;
        }
        if (Files.isRegularFile(file)){
            Files.delete(file);
            return 1;
        }
        try (Stream<Path> paths = Files.list(file)){
            paths.forEach(subFile -> {
                try{
                    count.addAndGet(removeWithPath(subFile.toString()));
                }
                catch (Exception e){
                    e.printStackTrace();
                }
            });
        }
        catch (Exception e){
            e.printStackTrace();
        }
        Files.delete(file);
        return count.get() + 1;
    }
}
