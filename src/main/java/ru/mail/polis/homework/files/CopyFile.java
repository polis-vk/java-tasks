package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File from = new File(pathFrom);
        File to = new File(pathTo);

        if (from.isDirectory()) {
            if (!to.exists() && to.isDirectory()) {
                to.mkdirs();
            } else {
                for (File file: from.listFiles()) {
                    if (file.isDirectory()) {
                        File newTo = new File(to + File.pathSeparator + from.getName());
                        copySmallFiles(from.getAbsolutePath(), newTo.getAbsolutePath());
                    }

                    copyFile(file.getAbsolutePath(), to.getAbsolutePath());
                }
            }

        }

        return pathTo;
    }

    private static void copyFile(String from, String to) {
        try (InputStream in = new BufferedInputStream(new FileInputStream(from));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(to))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
