package ru.mail.polis.homework.files;

import java.io.*;
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
        if(!fileFrom.exists())
            return null;
        if (fileFrom.isDirectory()) {
            if (!fileTo.exists()) {
                fileTo.mkdirs();
            } else if (fileTo.isFile()) {
                return null;
            }

            for (File file : fileFrom.listFiles()) {
                String subFileTo = fileTo.getAbsolutePath() + File.separator + file.getName();
                if (Files.isDirectory(Paths.get(file.getAbsolutePath()))) {
                    copySmallFiles(file.getAbsolutePath(), subFileTo);
                } else {
                    copyFile(file, new File(subFileTo));
                }
            }
            return pathTo;
        }
        File parentOfTo = fileTo.getParentFile();
        parentOfTo.mkdirs();
        copyFile(fileFrom, fileTo);
        return pathTo;

    }

    private static void copyFile(File fileFrom, File fileTo) {
        String buf;
        if (fileFrom.exists() && fileFrom.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(fileFrom))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileTo))) {
                    while ((buf = reader.readLine()) != null) {
                        writer.write(buf);
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

