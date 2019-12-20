package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(final String pathFrom, final String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        if (fileFrom.exists()) {
            if (fileFrom.isDirectory()) {
                if (!fileTo.exists()) {
                    fileTo.mkdirs();
                } else if (fileTo.isFile()) {
                    return null;
                }

                for (final File fileName : fileFrom.listFiles()) {
                    String subFileTo = fileTo.getAbsolutePath() + File.separator + fileName.getName();
                    File subFile = new File(subFileTo);
                    if (subFile.isDirectory() || !subFile.getName().contains(".")) {
                        copySmallFiles(fileName.getAbsolutePath(), subFileTo);
                    } else {
                        copyFile(fileName, subFile);
                    }

                }
                return pathTo;
            }
            File parentOfTo = fileTo.getParentFile();
            parentOfTo.mkdirs();
            copyFile(fileFrom, fileTo);
            return pathTo;
        }
        return null;
    }

    private static void copyFile(final File from, final File to) {
        if (from.exists() && from.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(from))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(to))) {

                    String line;
                    while ((line = reader.readLine()) != null) {
                        writer.write(line);
                        writer.newLine();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
