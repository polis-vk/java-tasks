package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 тугрика
     */

    public static int removeWithFile(String path) {
        return recursiveFileDeletion(new File(path));
    }

    private static int recursiveFileDeletion(File file) {
        if (file.isFile() && file.delete()) {
            return 1;
        }

        int result = 0;
        if (file.isDirectory()) {
            File[] files = file.listFiles();
            if (files != null) {
                for (File f : files) {
                    result += recursiveFileDeletion(f);
                }
            }
        }

        return file.delete() ? result + 1 : result;
    }
    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        Path directory = Paths.get(path);
        if (!Files.isRegularFile(directory) && !Files.isDirectory(directory)) {
            return 0;
        }

        return recursiveFileDeletion(directory);
    }

    private static int recursiveFileDeletion(Path file) throws IOException {
        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return Files.notExists(file) ? 1 : 0;
        }

        int result = 0;

        if (Files.isDirectory(file)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(file)) {
                for (Path pathFile : directoryStream) {
                    result += recursiveFileDeletion(pathFile);
                }
            } catch (Exception err) {
                err.printStackTrace();
            }
        }

        Files.delete(file);
        return Files.notExists(file) ? result + 1 : result;
    }
}
