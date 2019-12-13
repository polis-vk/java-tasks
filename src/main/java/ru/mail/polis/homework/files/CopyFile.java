package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
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
    public static String copySmallFiles(final String pathFrom, final String pathTo) throws IOException {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        if (fileFrom.exists() && !fileTo.exists()) {
            if (fileFrom.isDirectory()) {
                fileTo.mkdirs();
                for (final File fileName : fileFrom.listFiles()) {
                    String subFileTo = fileTo.getAbsolutePath() + File.separator + fileName.getName();
                    if (Files.isDirectory(Paths.get(fileName.getAbsolutePath()))) {
                        copySmallFiles(fileName.getAbsolutePath(), subFileTo);
                    } else {
                        copyFile(fileName, new File(subFileTo));
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

    private static void copyFile(final File from, final File to) throws IOException {
        if (from.exists() && from.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(from));
                 BufferedWriter writer = new BufferedWriter(new FileWriter(to))) {

                String line;
                while ((line = reader.readLine()) != null) {
                    writer.write(line);
                    writer.newLine();
                }
            }
        }
    }
}
