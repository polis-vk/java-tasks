package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        File dir = new File(path);

        if (!dir.exists()) {
            return 0;
        }

        int count = 1;
        if (dir.isFile() && dir.delete()) {
            return 1;
        }

        for (File file : dir.listFiles()) {
            if (file.isFile() && file.delete()) {
                count++;
            }
            count += removeWithFile(String.valueOf(file));
        }
        dir.delete();
        return count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */

    public static int removeWithPath(String path) throws IOException {
        Path source = Paths.get(path);
        if (!Files.exists(source)) {
            return 0;
        }
        AtomicInteger atomicCount = new AtomicInteger();

        Files.walkFileTree(source, new SimpleFileVisitor<>() {

            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                Files.delete(file);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                Files.delete(dir);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });
        return atomicCount.get();
    }


  /*  Я запуталась к тем, какое решение имеется в виду. Но вот это работает тоже.
  (почти, если не вклюсать все тесты сразу).

  private static AtomicInteger atomicCount = new AtomicInteger();

    public static int removeWithPath(String path) throws IOException {
        DirectoryStream<Path> directoryStream = null;
        Path dir = Paths.get(path);

        if (!Files.exists(dir)) {
            return 0;
        }
        if (!Files.isDirectory(dir)) {
            Files.delete(dir);
            return atomicCount.incrementAndGet();
        }

        atomicCount.incrementAndGet();
        try {
            directoryStream = Files.newDirectoryStream(dir);
            for (Path directory : directoryStream) {
                try {
                    Files.delete(directory);
                    atomicCount.incrementAndGet();
                } catch (DirectoryNotEmptyException e) {
                    removeWithPath(directory.toString());
                }
            }
        } finally {
            if (directoryStream != null) {
                try {
                    Files.delete(dir);
                    directoryStream.close();
                } catch (IOException e) {
                }
            }
        }
        return atomicCount.get();
    }
   */
}

