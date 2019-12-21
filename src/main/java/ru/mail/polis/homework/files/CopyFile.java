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
        final File from = new File(pathFrom);
        final File to = new File(pathTo);

        if (!from.exists()) {
            return null;
        }

        if (from.isFile()) {
            File parent = to.getParentFile();
            parent.mkdirs();
            copyFile(from, to);

            return pathTo;
        }

        if (!to.exists()) {
            to.mkdirs();
        }

        for (final File file : from.listFiles()) {
            final File sub = new File(to.getAbsolutePath() + File.separator + file.getName());

            if (sub.isFile()) {
                copyFile(file, sub);
            } else {
                copySmallFiles(file.getAbsolutePath(), sub.getAbsolutePath());
            }
        }

        return pathTo;
    }

    private static void copyFile(final File from, final File to) {
        if (!from.exists() || !from.isFile()) {
            return;
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(from));
            final BufferedWriter writer = new BufferedWriter(new FileWriter(to));

            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line + "\n");
            }

            reader.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
