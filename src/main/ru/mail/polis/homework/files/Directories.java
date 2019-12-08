package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */
    public static int removeWithFile(String path) {
        File file = new File(path);
        int res = 0;
        if (file.isDirectory()){
            for (File f: file.listFiles()){
                res+= removeWithFile(f.getPath());
                //System.out.println(res);
            }
        }
        if (file.delete()) {
            res+= 1;
        }
        else{
            System.out.println("Не удалось удалить файл "+path);
        }
        return res;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        Path p = Path.of(path);
        int res = 0;
        if (Files.isDirectory(p)) {
            try {
                List<Path> s = Files.list(p).collect(Collectors.toList());
                for (Path i: s){
                    res+=removeWithPath(i.toString());
                }
            } catch (IOException e) {
                e.printStackTrace();
            }

        }
        if (p.toFile().delete()){
            res+=1;
        }else{
            System.out.println("Не удалось удалить файл "+path);
        }
        return res;
    }
}
