package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
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

        if (file == null || !file.exists()) return 0;
        if (file.isFile()) {
            file.delete();
            return 1;
        }

        int result = 0;
        for (File f : file.listFiles()) {
            result += removeWithFile(f.getPath());
        }

        file.delete();
        return result + 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(Path path) throws IOException {
        if (!Files.exists(path)) return 0;
        if (Files.isRegularFile(path)) Files.delete(path);

        AtomicInteger result = new AtomicInteger();
        try (Stream<Path> stream = Files.list(path)) {
            stream.forEach(file -> {
                try {
                    removeWithPath(file);
                    result.getAndIncrement();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
        Files.delete(path);
        return result.get() + 1;
    }

    public static void main(String[] args) throws IOException {
        Path p = Paths.get("C:\\Users\\null\\Desktop\\java-tasks\\src\\main\\java\\ru\\mail\\polis\\homework\\files\\test\\test.java");
        removeWithPath(p);
    }
}
