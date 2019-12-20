package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
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
        return removeFiles(file);
    }

    private static int removeFiles(File file) {
        if (file==null || !file.exists()){
            return 0;
        }
        if (file.isFile()){
            file.delete();
            return 1;
        }
        int result =0;
        for (File temp : file.listFiles()){
            result+=removeFiles(temp);
        }
        file.delete();
        return result + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        AtomicInteger atomicCount = new AtomicInteger();
        try(Stream<Path> walk = Files.walk(Paths.get(path))){
            walk
                    .sorted(Comparator.reverseOrder())
                    .forEachOrdered(file -> {
                        atomicCount.incrementAndGet();
                        try {
                            Files.deleteIfExists(file);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        } catch (IOException e){
            e.printStackTrace();
        }
        return atomicCount.get();
    }
}
