package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и директорий из директории по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File dirFile = new File(path);
        if (!dirFile.exists()) {
            return 0;
        }
        return removeFile(dirFile, 1);
    }

    private static int removeFile(File path, int result) {
        int ret = result;
        if (path.isDirectory()) {
            for (File f : Objects.requireNonNull(path.listFiles())) {
                ret = removeFile(f, ++ret);
            }
        }
        path.delete();
        return ret;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path dirPath = Paths.get(path);
        AtomicInteger result = new AtomicInteger(0);
        try (Stream<Path> paths = Files.walk(dirPath)) {
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        file.delete();
                        result.getAndIncrement();
                    });
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return result.get();
    }
}
