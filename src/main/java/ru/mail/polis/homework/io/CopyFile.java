package ru.mail.polis.homework.io;

import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (Files.notExists(from)) {
            return "";
        }

        return null;
    }

}
