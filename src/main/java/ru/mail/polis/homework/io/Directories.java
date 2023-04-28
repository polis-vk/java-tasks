package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */

    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        if (file.isFile()) {
            file.delete();
            return 1;
        }
        int counter = 0;
        for (File f : file.listFiles()) {
            counter += removeWithFile(f.getPath());
        }
        file.delete();
        return counter + 1;

    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        if (!Files.exists(directory)) {
            return 0;
        }
        AtomicInteger atomicCounter = new AtomicInteger(0);
        Files.walkFileTree(directory, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                int counter = atomicCounter.getValue();
                Files.delete(file);
                atomicCounter.setValue(++counter);
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                int counter = atomicCounter.getValue();
                Files.delete(dir);
                atomicCounter.setValue(++counter);
                return FileVisitResult.CONTINUE;
            }
        });
        return atomicCounter.getValue();
    }
}

