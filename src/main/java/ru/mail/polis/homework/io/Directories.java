package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {
    
    
    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        if (path == null) {
            return 0;
        }
        File file = new File(path);
        
        if (!file.exists()) {
            return 0;
        } else if (file.isDirectory()) {
            int count = 0;
            for (String tmpPath : Objects.requireNonNull(file.list())) {
                count += removeWithFile(path + File.separator + tmpPath);
            }
            file.delete();
            return ++count;
        } else {
            file.delete();
            return 1;
        }
    }
    
    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String stringPath) throws IOException {
        if (stringPath == null) {
            return 0;
        }
        AtomicInteger count = new AtomicInteger(0);
        Files.walkFileTree(Paths.get(stringPath), new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
            
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                count.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return count.intValue();
    }
}
