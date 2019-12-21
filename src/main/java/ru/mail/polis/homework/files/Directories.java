package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {

    private int deleted;

    private Directories() {
        deleted = 0;
    }
    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директорий по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        Directories instance = new Directories();
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }

        if (file.isFile()) {
            if (file.delete()) {
                instance.deleted++;
            }
        } else if (file.isDirectory()) {
            removeWithFile(file, instance);
        }

        return instance.deleted;
    }

    private static void removeWithFile(File file, Directories instance) {

        File[] children = file.listFiles();
        if (children != null) {
            for (File child : children) {
                if (child.isFile()) {
                    if (child.delete()) {
                        instance.deleted++;
                    }
                } else if (child.isDirectory()) {
                    removeWithFile(child, instance);
                }
            }
        }

        if (file.delete()) {
            instance.deleted++;
        }
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Directories instance = new Directories();
        Path p = Paths.get(path);
        if (Files.notExists(p)) {
            return 0;
        }

        if (Files.isRegularFile(p)) {
            deleteFile(p);
            instance.deleted++;
        } else if (Files.isDirectory(p)) {
            removeWithPath(p, instance);
        }

        return instance.deleted;
    }

    private static void removeWithPath(Path p, Directories instance) {
        try (DirectoryStream<Path> ds = Files.newDirectoryStream(p)) {
            for (Path child : ds) {
                if (Files.isRegularFile(child)) {
                    Files.delete(child);
                    instance.deleted++;
                } else if (Files.isDirectory(child)) {
                    removeWithPath(child, instance);
                }
            }
            deleteFile(p);
            instance.deleted++;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deleteFile(Path path) {
        try {
            Files.delete(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
