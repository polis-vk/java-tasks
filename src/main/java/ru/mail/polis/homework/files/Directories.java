package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {

    private static int deleted = 0;
    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директорий по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        deleted = 0;
        File file = new File(path);
        if (!file.exists()) return 0;

        if (file.isFile()) {
            if (file.delete()) deleted++;
        } else if (file.isDirectory())
            removeWithFile(file);

        return deleted;
    }

    private static void removeWithFile(File file) {

        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    if (child.delete()) deleted++;
                } else if (child.isDirectory()) {
                    removeWithFile(child);
                }
            }
        }

        if (file.delete()) deleted++;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        deleted = 0;
        Path p = Paths.get(path);
        if (Files.notExists(p)) return 0;

        try {
            if (Files.isRegularFile(p)) {
                Files.delete(p);
                deleted++;
            } else if (Files.isDirectory(p))
                removeWithPath(p);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return deleted;
    }

    private static void removeWithPath(Path p) throws IOException {
            DirectoryStream<Path> ds = Files.newDirectoryStream(p);
            for (Path child : ds) {
                if (Files.isRegularFile(child)) {
                    Files.delete(child);
                    deleted++;
                } else if (Files.isDirectory(child)) {
                    removeWithPath(child);
                }
            }
            ds.close();

            Files.delete(p);
            deleted++;
    }

}
