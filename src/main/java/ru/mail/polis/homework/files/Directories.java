package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
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
        ArrayList<File> files = new ArrayList<>();
        files.add(dirFile);
        removeFile(dirFile, files);
        return files.size();
    }

    private static boolean removeFile(File path, ArrayList<File> files){
        boolean ret = true;
        if (path.isDirectory()){
            for (File f : path.listFiles()){
                files.add(f);
                ret = ret && removeFile(f, files);
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
                        result.getAndIncrement();
                        file.delete();
                    });
        }
        catch (IOException | IllegalStateException ex){
            ex.printStackTrace();
        }
        return result.get();
    }
}
