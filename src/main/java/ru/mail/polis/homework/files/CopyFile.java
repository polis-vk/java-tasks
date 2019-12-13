package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File dirFrom = new File(pathFrom);
        File dirTo = new File(pathTo);
        if (dirFrom.exists()) {
            File[] files = dirFrom.listFiles();
            if (files != null) {
                if (!dirTo.exists()) {
                    dirTo.mkdirs();
                }
                for (File fileFrom : files) {
                    File fileTo = new File(dirTo + File.separator + fileFrom.getName());
                    copySmallFiles(fileFrom.getAbsolutePath(), fileTo.getAbsolutePath());
                }
                return pathTo;
            } else {
                try {
                    createFile(dirTo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                copyFile(dirFrom, dirTo);
            }
        }
        return null;
    }

    private static void createFile(File file) throws IOException {
        File parent = file.getParentFile();
        if (!parent.exists()) {
            parent.mkdirs();
            file.createNewFile();
        } else if (file.exists()) {
            file.createNewFile();
        }
    }

    private static void copyFile(File fileFrom, File fileTo) {
        try (BufferedReader from = Files.newBufferedReader(fileFrom.toPath());
             BufferedWriter to = Files.newBufferedWriter(fileTo.toPath())) {
            from.lines()
                    .forEach(str -> {
                        try {
                            to.write(str);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });

        } catch (IOException ignored) {
        }
    }
}
