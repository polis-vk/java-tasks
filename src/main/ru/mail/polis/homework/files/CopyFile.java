package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.channels.Channels;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo){
       final Path directoryFrom = Paths.get(pathFrom).toAbsolutePath();
       final Path directoryTo = Paths.get(pathTo).toAbsolutePath();
        if (!Files.exists(directoryFrom)) {
            return null;
        }
       copySmallFiles(directoryFrom, directoryTo);

       return pathTo;
    }

    private static void copySmallFiles(Path pathFrom, Path pathTo){
        if (!Files.exists(pathFrom)){
            return;
        }
        if (Files.isDirectory(pathFrom)) {
            try {
                Files.createDirectory(Paths.get(pathTo.toString()));
                Files.list(pathFrom)
                        .forEach(
                                path-> copySmallFiles(path, Paths.get(pathTo.toString(), path.getFileName().toString()))
                        );
            }
            catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            copyFile(pathFrom, pathTo);
        }
    }

    private static void copyFile(Path from, Path to){
        try (FileChannel fileFrom = new FileInputStream(from.toString()).getChannel();
              FileChannel fileTo = new FileOutputStream(to.toString()).getChannel())
        {
            fileTo.transferFrom(fileFrom, 0, fileFrom.size());
        }
        catch (IOException e) {
            e.printStackTrace();
        }
    }
}
