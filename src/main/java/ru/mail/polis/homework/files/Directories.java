package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        return remove(file);
    }

    private static int remove(File file){
        int result = 0;
        if(file == null || !file.exists()){
            return -1;
        }

        if (file.isFile()){
            file.delete();
            return 1;
        }

        for(File t : file.listFiles()){
            result += remove(t);
        }

        file.delete();
        return result + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        return pathRemove(filePath);
    }

    private static int pathRemove(Path path) throws IOException {
        if (!Files.exists(path)) {
            return 0;
        }

        AtomicInteger atomicInteger = new AtomicInteger(0);
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                atomicInteger.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                atomicInteger.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return atomicInteger.get();
    }
}
