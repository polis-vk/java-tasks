package ru.mail.polis.homework.files;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

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
            Files.walk(src).forEach(s -> {
                try {
                    Path d = dest.resolve(src.relativize(s));
                    if( Files.isDirectory(s)) {
                        if( !Files.exists(d)) {
                            Files.createDirectories(d);
                        }
                    }
                    else if (Files.isRegularFile(s)){
                        Path parent = d.getParent();
                        if (!Files.exists(parent)){
                            Files.createDirectories(parent);
                        }
                        Files.createFile(d);
                    }
                    Files.copy(s, d, StandardCopyOption.REPLACE_EXISTING);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }

}
