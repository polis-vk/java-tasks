package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Arrays;

public class Directories {

    private static class MyFileVisitor extends SimpleFileVisitor<Path> {

        int count = 0;

        @Override
        public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
            Files.delete(file);
            count++;
            return FileVisitResult.CONTINUE;
        }



        @Override
        public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
            Files.delete(dir);
            count++;
            return FileVisitResult.CONTINUE;
        }

        public int getCount() {
            return count;
        }
    }

    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        return remove(new File(path));
    }

    private static int remove(File file) {
        int count = 0;
        try {
            for (File f : file.listFiles()) {
                if (f.isDirectory()) {
                    count += remove(f);
                } else {
                    f.delete();
                    count++;
                }
            }
            if (file.delete())
                count++;
            return count;
        } catch (NullPointerException ex) {
            try {
                if (file.delete())
                    count++;
                return count;
            } catch (SecurityException e) {
                return 0;
            }
        }
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        try {
            return remove(Paths.get(path));
        } catch (IOException e) {
            return 0;
        }
    }

    private static int remove(Path path) throws IOException {
        MyFileVisitor fileVisitor = new MyFileVisitor();
        Files.walkFileTree(path, fileVisitor);
        return fileVisitor.getCount();
    }
}
