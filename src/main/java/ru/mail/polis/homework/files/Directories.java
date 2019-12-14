package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Directories {

    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        final File directory;
        if (path == null // метод removeWithFile() не бросает исключения, а new File() бросит, если path==null
                || !(directory = new File(path)).exists()/* || directory.isFile()*/) {
            return 0;
        }
        // В задании сказано "Реализовать рекурсивное удаление всех файлов и дерикторий из ДИРЕКТОРИИ по заданному пути",
        // но в тесте filesOneFile() считается, что при аргументе-файле функция должна удалить его и вернуть 1.
        // Подогнал под тесты, но здесь явно противоречие заданию.
        return directory.isDirectory()
                ? recursivelyRemove(directory)
                : (directory.delete() ? 1 : 0);
        // return recursivelyRemove(directory);
    }

    // дополнительный метод сделан для избежания постоянных проверок на null
    private static int recursivelyRemove(final File directory) {
        int result = 0;
        for (final File file : directory.listFiles()) {
            result += file.isDirectory() ? recursivelyRemove(file) : 0;
            result += file.delete() ? 1 : 0;
        }
        result += directory.delete() ? 1 : 0;
        return result;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        final AtomicInteger count = new AtomicInteger(0);
        // считается ли использование статического метода walkFileTree() как "рекурсивный обход ручками"?)
        try {
            final Path filePath = Paths.get(path);
            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Objects.requireNonNull(file);
                    Objects.requireNonNull(attrs);
                    Files.delete(file);
                    count.incrementAndGet();
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                    Objects.requireNonNull(dir);
                    if (exc != null) {
                        throw exc;
                    } else {
                        Files.delete(dir);
                        count.incrementAndGet();
                        return FileVisitResult.CONTINUE;
                    }
                }
            });
        } catch (IOException | NullPointerException ignored) { }
        return count.intValue();
    }
}
