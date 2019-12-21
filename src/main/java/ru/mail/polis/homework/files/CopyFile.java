package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Collectors;

public class CopyFile {
    private final static String separator = System.getProperty("file.separator");


    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        Path srcPath = Paths.get(pathFrom);
        if (!Files.exists(srcPath)) {
            return null;
        }

        try {
            if (Files.isDirectory(srcPath)) {
                copyDirectory(pathFrom, pathTo);
            } else {
                copyFile(pathFrom, pathTo);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return pathTo;
    }

    private static void copyDirectory(String pathFrom, String pathTo) throws IOException {
        final Path srcDir = Paths.get(pathFrom);

        Files.createDirectories(Paths.get(pathTo));

        for (Path temp : Files.list(srcDir).collect(Collectors.toList())) {
            copySmallFiles(pathFrom + separator + temp.getFileName(), pathTo + separator + temp.getFileName());
        }
    }

    private static void copyFile(String pathFrom, String pathTo) throws IOException {
        Path dstPath = Paths.get(pathTo);

        if (!Files.exists(dstPath.getParent())) {
            Files.createDirectories(dstPath.getParent());
        }

        try (BufferedInputStream inputStream = new BufferedInputStream(new FileInputStream(pathFrom));
             BufferedOutputStream outputStream = new BufferedOutputStream(new FileOutputStream(pathTo))) {
            byte[] buffer = new byte[8192];
            while (inputStream.available() > 0) {
                int count = inputStream.read(buffer);
                outputStream.write(buffer, 0, count);
            }
        }
    }

}
