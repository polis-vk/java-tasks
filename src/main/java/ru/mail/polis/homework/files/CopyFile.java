package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        try {
            if (Files.isDirectory(Paths.get(pathFrom))) {
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
        final File srcDir = new File(pathFrom);

        Files.createDirectories(Paths.get(pathTo));

        for (String fileName : srcDir.list()) {
            copySmallFiles(pathFrom + srcDir.separator + fileName, pathTo + srcDir.separator + fileName);
        }
    }

    private static void copyFile(String pathFrom, String pathTo) throws IOException {
        Path dstPath = Paths.get(pathTo);

        if (!Files.exists(dstPath.getParent())) {
            Files.createDirectories(dstPath.getParent());
        }

        try (FileInputStream inputStream = new FileInputStream(pathFrom);
             FileOutputStream outputStream = new FileOutputStream(pathTo)) {
            byte[] buffer = new byte[1000];
            while (inputStream.available() > 0) {
                int count = inputStream.read(buffer);
                outputStream.write(buffer, 0, count);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

}
