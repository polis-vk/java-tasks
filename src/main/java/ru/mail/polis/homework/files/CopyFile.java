package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom).toAbsolutePath();
        Path to = Paths.get(pathTo).toAbsolutePath();
        if (Files.exists(from)){
            copyFiles(from, to);
            return pathTo;
        }
        return null;
    }

    private static void copyFiles(Path from, Path to) {
        if (Files.isDirectory(from)){
            try {
                Files.createDirectories(to);
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                Files.list(from).forEach(
                        itemPath -> copyFiles(itemPath, Paths.get(to.toString(), itemPath.getFileName().toString()))
                );
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else {
            try{
                Files.createDirectories(to.getParent());
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                copy(from, to);
            }
            catch (IOException e){
                e.printStackTrace();
            }
        }
    }

    private static void copy(Path from, Path to) throws IOException{
        try(FileChannel oldFile = new FileInputStream(from.toString()).getChannel()){
            try(FileChannel newFile = new FileOutputStream(to.toString()).getChannel()){
                newFile.transferFrom(oldFile, 0, oldFile.size());
            }
        }
    }
}