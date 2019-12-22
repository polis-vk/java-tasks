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
        File file = new File(pathFrom);
        File dir = new File(pathTo);
        if (file.exists()) {
            File[] files = file.listFiles();
            if (files != null) {
                if (!dir.exists()) {
                    dir.mkdirs();
                }
                for (File fileFrom : files) {
                    File fileTo = new File(dir + File.separator + fileFrom.getName());
                    copySmallFiles(fileFrom.getAbsolutePath(), fileTo.getAbsolutePath());
                }
                return pathTo;
            } else {
                try {
                    createFile(dir);
                    copyFile(file, dir);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }

    private static void createFile(File file) throws IOException {
        File f = file.getParentFile();
        if (!f.exists()) {
            f.mkdirs();
            file.createNewFile();
        } else if (file.exists()) {
            file.createNewFile();
        }
    }

    private static void copyFile(File fileFrom, File fileTo) {
        try (BufferedReader from = Files.newBufferedReader(fileFrom.toPath())) {
            try (BufferedWriter to = Files.newBufferedWriter(fileTo.toPath())) {
                from.lines().forEach(str -> {
                    write(to, str);
                });
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    private static void write(BufferedWriter to, String str) {
        try {
            to.write(str);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
