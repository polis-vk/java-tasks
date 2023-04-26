package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.util.Objects;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */
    public static int removeWithFile(String path) {
        int result = 0;
        File file = new File(path);

        if (!file.exists()) {
            return 0;
        }

        if (file.isDirectory()) {
            for (File current : Objects.requireNonNull(file.listFiles())) {
                result += removeWithFile(current.getPath());
            }
        }

        return file.delete() ? result + 1 : result;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);

        if (Files.notExists(file)) {
            return 0;
        }

        FileVisitorWithDelete visitor = new FileVisitorWithDelete();
        Files.walkFileTree(file, visitor);
        return visitor.getCountOfDeletedFilesAndDirectories();
    }
}
