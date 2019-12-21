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
                    try {
                        InputStream is = new FileInputStream(child);
                        OutputStream os = new FileOutputStream(newFile);
                        writeToFile(is, os);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (!from.exists()) {
                return;
            }
            if (!to.getParentFile().exists()) {
                to.getParentFile().mkdirs();
            }
            InputStream is = null;
            try {
                is = new FileInputStream(from);
                OutputStream os = new FileOutputStream(to);
                writeToFile(is, os);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private static void writeToFile(InputStream is, OutputStream os) throws IOException {
        int a;
        while ((a = is.read()) != -1) {
            os.write(a);
        }
    }
}
