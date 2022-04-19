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
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("The path is incorrect");
        }
        File filePath = new File(path);
        if (!filePath.exists()) {
            return 0;
        }
        int countDeletedElements = 0;
        if (filePath.isDirectory()) {
            for (File currentFilePath : filePath.listFiles()) {
                countDeletedElements += removeWithFile(currentFilePath.getPath());
            }
        }
        return filePath.delete() ? ++countDeletedElements : countDeletedElements;
    }

    /**
     * С использованием Path
     * 2 тугрика
     */
    public static int removeWithPath(String path) throws IOException {
        if (path == null || path.isEmpty()) {
            throw new IllegalArgumentException("The path is incorrect");
        }
        Path pathFile = Paths.get(path);
        if (!Files.exists(pathFile)) {
            return 0;
        }
        int countDeletedElements = 0;
        if (Files.isDirectory(pathFile)) {
            try (DirectoryStream<Path> pathStream = Files.newDirectoryStream(pathFile)) {
                for (Path currentPath : pathStream) {
                    countDeletedElements += removeWithPath(currentPath.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        try {
            Files.delete(pathFile);
            ++countDeletedElements;
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return countDeletedElements;
    }
}
