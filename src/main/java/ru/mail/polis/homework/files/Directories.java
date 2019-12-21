package ru.mail.polis.homework.files;

import java.io.File;

public class Directories {


    /**
     * Реализовать рекурсивное удаление всех файлов и дерикторий из директороии по заданному пути.
     * Метод должен возвращать количество удаленных файла и директорий.
     * Если директории по существующему пути нет, то возвращаем 0.
     * Написать двумя способами. С использованием File
     */

    public static int removeWithFile(String path) {
        File file = new File(path);
        if(!file.exists())
            return 0; //если пусто то false
        if(file.isDirectory()) //если имеем директорию, то рекурсивно идем в ее конец, чтобы удалять файлы
        {
            for(File f : file.listFiles())
                removeWithFile(f.getAbsolutePath());
            file.delete(); // удалям пустую директорию
        }
        else // если имеем файл, то удаляем
        {
            file.delete();
        }
        return 1;
    }

    /**
     * С использованием Path
     */
    public static int removeWithPath(String path) {
        return 0;
    }
}
