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
        File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        int result = 0;
        if (!file.isFile()) {
            for (File temp : Objects.requireNonNull(file.listFiles())) {
                result += removeWithFile(temp.getPath());
            }
        }
        file.delete();
        return ++result;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);

        if (!Files.exists(dir)) {
            return 0;
        }

        AtomicInteger result = new AtomicInteger();
        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
                    @Override
                    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                        result.incrementAndGet();
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }

                    @Override
                    public FileVisitResult visitFile(Path dir, BasicFileAttributes attrs) throws IOException {
                        result.incrementAndGet();
                        Files.delete(dir);
                        return FileVisitResult.CONTINUE;
                    }
                }
        );
        return result.get();
    }
}
