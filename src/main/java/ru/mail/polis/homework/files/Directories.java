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

    private static int result;

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
        result = 1;
        removeFile(dirFile);
        return result;
    }

    private static boolean removeFile(File path){
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : Objects.requireNonNull(path.listFiles())){
                result++;
                ret = ret && removeFile(f);
            }
        }
        return ret && path.delete();
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path dirPath = Paths.get(path);
        AtomicInteger result = new AtomicInteger(0);
        try (Stream<Path> paths = Files.walk(dirPath)){
            paths.sorted(Comparator.reverseOrder())
                    .map(Path::toFile)
                    .forEach(file -> {
                        result.incrementAndGet();
                        file.delete();
                    });
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return result.get();
    }
}
