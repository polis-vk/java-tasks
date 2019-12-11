package ru.mail.polis.homework.files;

import java.io.File;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        //Пока сдаю толко Directories, для этой ещё надо написать тесты :))))
        File files = new File(pathFrom);
        return files.getAbsolutePath();
    }
}
