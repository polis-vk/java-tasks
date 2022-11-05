package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Deque;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String pathStr) {
        File file = new File(pathStr);
        if (!file.exists()) {
            return 0;
        }

        int count = 0;
        Deque<File> filesToVisit = new ArrayDeque<>();
        Deque<File> dirsToDelete = new ArrayDeque<>();

        filesToVisit.offerLast(file);
        while (filesToVisit.size() > 0) {
            File curFile = filesToVisit.pollFirst();
            if (curFile.isDirectory()) {
                Arrays.stream(curFile.listFiles()).forEach(filesToVisit::offerLast);
                dirsToDelete.offerFirst(curFile);
            } else {
                curFile.delete();
            }
            count++;
        }
        dirsToDelete.stream().forEach(File::delete);

        return count;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String pathStr) throws IOException {
        Path path = Paths.get(pathStr);
        if (Files.notExists(path)) {
            return 0;
        }

        AtomicInteger atomicCount = new AtomicInteger();
        Files.walkFileTree(path, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult postVisitDirectory(Path pathDir, IOException e) throws IOException {
                Files.delete(pathDir);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFile(Path pathFile, BasicFileAttributes attrs) throws IOException {
                Files.delete(pathFile);
                atomicCount.incrementAndGet();
                return FileVisitResult.CONTINUE;
            }
        });

        return atomicCount.get();
    }
}
