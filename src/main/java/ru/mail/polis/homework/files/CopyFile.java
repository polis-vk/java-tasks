package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        final File from = new File(pathFrom);
        final File to = new File(pathTo);

        if (from.exists() && from.isDirectory()) {
            if (!to.exists()) {
                to.mkdirs();
            }
            for (File file : from.listFiles()) {
                String childFrom = file.getAbsolutePath();
                String childTo = to.getAbsolutePath() + File.separator + file.getName();

                if (file.isDirectory()) {
                    copySmallFiles(childFrom, childTo);
                } else {
                    try {
                        copyFile(childFrom, childTo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        } else {
            if (from.exists() && from.isFile()) {
                try {
                    if (!to.exists()) {
                        to.mkdirs();
                    }
                    copyFile(pathFrom, pathTo);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return pathTo;
    }

    private static void copyFile(String pathFrom, String pathTo) throws IOException {
        final File from = new File(pathFrom);
        final File to = new File(pathTo);

        try (InputStream in = new BufferedInputStream(new FileInputStream(from));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(to))
        ) {
            byte[] buffer = new byte[1024];
            int lengthRead;

            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }
}