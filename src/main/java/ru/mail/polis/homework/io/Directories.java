package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файлов и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     * 2 балла
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        return removeFiles(file);
    }

    private static int removeFiles (File file) {
        if (!file.exists()) {
            return 0;
        } else if (file.isFile()) {
            return file.delete() ? 1 : 0;
        }else {
            int result = 1;
            for (File f : Objects.requireNonNull(file.listFiles())) {
                result += removeFiles(f);
            }
            file.delete();
            return result;
        }
    }

    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path filePath = Paths.get(path);
        return removeFiles(filePath);
    }

    private static int removeFiles (Path path) throws IOException {
        if (Files.notExists(path)) {
            return 0;
        } else if (Files.isRegularFile(path)) {
            return Files.deleteIfExists(path) ? 1 : 0;
        }else {
            int result = 1;
            for (Path p : Objects.requireNonNull(Files.newDirectoryStream(path))) {
                result += removeFiles(p);
            }
            Files.delete(path);
            return result;
        }
    }
}
