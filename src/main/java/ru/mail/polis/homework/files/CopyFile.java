package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File dirFrom = new File(pathFrom);
        File dirTo = new File(pathTo);
        if (dirFrom.isDirectory() && dirFrom.exists())
            if (!dirTo.exists()) {
                dirTo.mkdirs();
            }
        for (String file : dirFrom.list()) {
            File fileFrom = new File(dirFrom + File.separator + file);
            File fileTo = new File(dirTo + File.separator + file);
            if (fileFrom.isDirectory()) {
                copySmallFiles(fileFrom.getAbsolutePath(), fileTo.getAbsolutePath());
            } else {
                InputStream inputStream = null;
                OutputStream outputStream = null;
                try {
                    inputStream = new FileInputStream(fileFrom);
                    outputStream = new FileOutputStream(fileTo);
                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = inputStream.read(buffer)) > 0) {
                        outputStream.write(buffer, 0, length);
                    }
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } finally {
                    try {
                        inputStream.close();
                        outputStream.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return pathTo;
        }
        return null;
    }
}
