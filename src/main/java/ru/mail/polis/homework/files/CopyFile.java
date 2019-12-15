package ru.mail.polis.homework.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        Path src = Paths.get(pathFrom);
        Path dest = Paths.get(pathTo);
        try {
            Files.walk(src)
                    .forEach(source -> copy(source, dest.resolve(src.relativize(source))));
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

    private static void copy(Path source, Path dest){
        try {
            Files.copy(source, dest, REPLACE_EXISTING);
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage(), e);
        }
    }

}
