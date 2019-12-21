package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static void copySmallFiles(String pathFrom, String pathTo) {
        File from = new File(pathFrom);
        File to = new File(pathTo);

        if (from.isDirectory() && from.exists()) {
            if (!to.exists()) {
                to.mkdirs();
            }
            File[] children = from.listFiles();
            if (children == null) {
                return;
            }
            for(File child: children) {
                File newFile = new File(to, child.getName());
                if (child.isDirectory()) {
                    copySmallFiles(child.getAbsolutePath(), newFile.getAbsolutePath());
                } else {
                    writeToFile(child, newFile);
                }
            }
        } else {
            if (!from.exists()) {
                return;
            }
            if (!to.getParentFile().exists()) {
                to.getParentFile().mkdirs();
            }
            writeToFile(from, to);
        }
    }

    private static void writeToFile(File one, File two) {

        InputStream is;
        try {
            is = new FileInputStream(one);
            OutputStream os = new FileOutputStream(two);
            int a;
            while ((a = is.read()) != -1) {
                os.write(a);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
