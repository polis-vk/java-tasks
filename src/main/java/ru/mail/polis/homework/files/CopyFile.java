package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */

    public static String copySmallFiles(final String pathFrom, final String pathTo) {
        File from = new File(pathFrom);
        File to = new File(pathTo);
        if (!from.exists()) {
            return null;
        }


        if (!to.exists()) {
            if (from.listFiles() != null) {
                to.mkdirs();
            } else {
                try {
                    to.getParentFile().mkdirs();
                    to.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                copyFile(from, to);
                return pathTo;
            }
        }

        for (File fromSubFile : from.listFiles()) {
            File toSubFile = new File(
                to.getAbsolutePath() + File.separator + fromSubFile.getName()
            );
            copySmallFiles(fromSubFile.getAbsolutePath(), toSubFile.getAbsolutePath());
        }

        return pathTo;
    }

    private static void copyFile(final File from, final File to) {
        try (BufferedReader reader = new BufferedReader(new FileReader(from));
             BufferedWriter writer = new BufferedWriter(new FileWriter(to))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}