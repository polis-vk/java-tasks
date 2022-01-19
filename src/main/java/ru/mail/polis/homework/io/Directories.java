package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.nio.file.DirectoryStream;
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
    //static int countDeleted;
    public static int removeWithFile(String path) {
        File file = new File(path);
        if (!file.exists()) {
            return 0;
        }
        int countDeleted = 0;


        if (file.isFile()) {
            if (!file.delete()) {
                throw new RuntimeException();
            }
            ++countDeleted;
        } else {
            for (File f : file.listFiles()) {
                countDeleted += removeWithFile(f.getPath());
            }
            // не работает при одновременном запуске тестов. если запускть тесты друг за другом - все ОК.
            //Для проверки удалть for выше и раскомментировать
            /*
            if (file.exists()&&file.isDirectory()){
                File[] tmp = file.listFiles();
                for (int i = 0; i < tmp.length; i++){
                    if (tmp[i].isDirectory()){
                        removeWithFile(path+"/"+tmp[i].getName());
                    } else {
                        tmp[i].delete();
                        ++countDeleted;
                    }
                }
                file.delete();
                ++countDeleted;
                }
            */
            if (!file.delete()) {
                throw new RuntimeException();
            }
            ++countDeleted;
        }
        return countDeleted;
    }


    /**
     * С использованием Path
     * 2 балла
     */
    public static int removeWithPath(String path) throws IOException {
        Path dir = Paths.get(path);
        if (!Files.exists(dir)) {
            return 0;
        }

        if (!Files.isDirectory(dir)) {
            Files.delete(dir);
            return 1;
        }

        int countDeleted = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir)) {
            for (Path entry : stream) {
                countDeleted += removeWithPath(entry.toString());
            }
        }

        Files.delete(dir);
        ++countDeleted;
        return countDeleted;
    }
}
