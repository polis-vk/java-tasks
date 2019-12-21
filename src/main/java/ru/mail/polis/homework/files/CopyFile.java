package ru.mail.polis.homework.files;

import java.io.*;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File fFrom = new File(pathFrom);
        File fTo = new File(pathTo);

        if (!fFrom.exists()) {
            return null;
        }

        if (fFrom.isFile()) {
            File pFile = fTo.getParentFile();
            pFile.mkdirs();

            copyFile(fFrom, fTo);

            return pathTo;
        }

        if (!fTo.exists()) {
            fTo.mkdirs();
        }

        for (File file: Objects.requireNonNull(fFrom.listFiles())) {
            final File child = new File(fTo.getAbsolutePath() + File.separator + file.getName());

            if (file.isDirectory()) {
                copySmallFiles(file.getAbsolutePath(), child.getAbsolutePath());
                continue;
            }

            copyFile(file, child);
        }

        if (fFrom.listFiles().length == 0) {
            new File(fTo.toString()).mkdirs();
        }

        return pathTo;
    }

    private static void copyFile(File from, File to) {
        try {
            final BufferedReader br = new BufferedReader(new FileReader(from));
            final BufferedWriter bw = new BufferedWriter(new FileWriter(to));

            String line;
            while ((line = br.readLine()) != null) {
                bw.write(line);
                bw.newLine();
            }

            bw.close();
            br.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
