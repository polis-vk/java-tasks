package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File copyFrom = new File(pathFrom);
        File copyTo = new File(pathTo);

        if (copyFrom.isFile()) {
            if (!copyTo.exists()) {
                new File(copyTo.getParent()).mkdirs();
                try {
                    copyTo.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            cp(copyFrom, copyTo);
        } else if (copyFrom.isDirectory()) {
            if (!copyTo.exists()) {
                copyTo.mkdirs();
            }
            File[] files = copyFrom.listFiles();
            for (File f : files) {
                File cpTo = new File(copyTo.getAbsolutePath() + File.separator + f.getName());
                if (f.isFile()) {
                    cp(f, copyTo);
                } else if (f.isDirectory()) {
                    copySmallFiles(f.getAbsolutePath() + File.separator + f.getName(),
                            cpTo.getAbsolutePath() + File.separator + cpTo.getName());
                }
            }
        }

        return pathTo;
    }

    private static void cp(File from, File to) {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(from);
            os = new FileOutputStream(to);
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                is.close();
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
