package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
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
        int count = 0;
        File file = new File(path);
        if (!file.exists()) {
            return count;
        }
        if (file.isDirectory()) {
            for (String str : file.list()) {
                count += removeWithFile(path + "/" + str);
            }
        }
        return file.delete() ? count + 1 : count;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        AtomicInteger atomicCount = new AtomicInteger();
        Path filePath = Paths.get(path);
        if (Files.notExists(filePath)) {
            return 0;
        }
        Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return atomicCount.intValue();
    }
}
