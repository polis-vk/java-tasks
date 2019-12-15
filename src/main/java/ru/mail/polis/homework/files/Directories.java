package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        return removeFile(file);
    }

    private static int removeFile(File file)
    {
        if(file==null||!file.exists())
            return 0;
        if(file.isFile())
        {
            file.delete();
            return 1;
        }
        int numDeleteFiles=0;
        for (File temp:file.listFiles())
        {
            numDeleteFiles+=removeFile(temp);
        }
        file.delete();
        return numDeleteFiles+1;
    }

    /**
     * С использованием Path
     */
    private static int removeFileWithPath(Path file) throws IOException {
        if (!Files.exists(file)) {
            return 0;
        }
        if (Files.isRegularFile(file)) {
            Files.delete(file);
            return 1;
        }
        AtomicInteger count = new AtomicInteger(0);
        ArrayList<IOException> ioException = new ArrayList<>();
        try (Stream<Path> paths = Files.list(file)) {
            paths.forEach(tempPath -> {
                try {
                    count.addAndGet(removeFileWithPath(tempPath));
                } catch (IOException e) {
                    ioException.add(e);
                }
            });
            if (ioException.size() != 0) {
                throw ioException.get(0);
            }
        }
        Files.delete(file);
        return count.get() + 1;
    }

    public static int removeWithPath(String path) throws IOException {
        Path file = Paths.get(path);
        return removeFileWithPath(file);
    }
}
