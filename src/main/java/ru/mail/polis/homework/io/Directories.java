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
        return distr(new File(path));
    }

    private static int distr(File file){
        if (!file.exists()){
            return 0;
        }
        int result = 1;
        if(file.isDirectory()){
            File[] files = file.listFiles();
            for (File tmp : files) {
                result += distr(tmp);
            }
        }
        file.delete();
        return result;
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        if (Files.notExists(filePath)) {
            return 0;
        }

        int count = 1;
        if (Files.isDirectory(filePath)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(filePath)) {
                for (Path pathFile : directoryStream) {
                    count += removeWithPath(pathFile.toString());
                }
            }
        }
        Files.delete(filePath);
        return count;
    }
}
