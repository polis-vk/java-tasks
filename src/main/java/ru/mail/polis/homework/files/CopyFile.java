package ru.mail.polis.homework.files;

import java.io.*;
import java.util.Arrays;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        copy(fileFrom, fileTo);
        return null;
    }

    private static void copy(File fileFrom, File fileTo) {
        if (fileFrom.exists()) {
            if (fileFrom.isDirectory()) {
                fileTo.mkdirs();
                Arrays.stream(Objects.requireNonNull(fileFrom.listFiles())).forEach(file ->
                        copy(file, new File(fileTo.getAbsolutePath() + File.separator + file.getName())));
            } else {
                try {
                    copyFile(fileFrom, fileTo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void copyFile(File fileFrom, File fileTo) throws IOException {
        File parentFile = fileTo.getParentFile();
        parentFile.mkdirs();
        fileTo.createNewFile();
        try (InputStream in = new BufferedInputStream(new FileInputStream(fileFrom));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(fileTo))) {
            byte[] buffer = new byte[4096];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
            }
        }
    }
}