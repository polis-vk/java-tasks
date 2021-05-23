package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            return 0;
        }
        return 1 + removeRecursiveFile(dir);
    }

    public static int removeRecursiveFile(File dir) {
        int n = 0;
        if (dir.isDirectory()) {
            for (File element : dir.listFiles()) {
                n += removeRecursiveFile(element) + 1;
            }
        }
        dir.delete();
        return n;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        if (!Files.exists(Paths.get(path))) {
            return 0;
        }
        return 1 + removeRecursivePath(path);
    }

    public static int removeRecursivePath(String dir) throws IOException {
        Path path = Paths.get(dir);
        int n = 0;
        if (Files.isDirectory(path)) {
            DirectoryStream<Path> stream = Files.newDirectoryStream(path);
            for (Path line : stream) {
                n += removeRecursivePath(line.toString()) + 1;
            }
            stream.close();
        }
        Files.delete(path);
        return n;
    }
}
