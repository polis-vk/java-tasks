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

        copySmallFiles(from, to);

        return pathTo;
    }

    private static void copySmallFiles(File from, File to) {
        if (from.isDirectory()) {
            if (!to.exists()) {
                to.mkdirs();
            }
            for (File childFrom : from.listFiles()) {
                String childToPath = to.getAbsolutePath() + File.separator + childFrom.getName();
                File childTo = new File(childToPath);

                if (childFrom.isDirectory()) {
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
            if (from.isFile()) {
                try {
                    if (!to.exists()) {
                        File parent = new File(to.getParent());
                        parent.mkdirs();
                        to.createNewFile();
                    }
                    copyFile(from, to);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void copyFile(File from, File to) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(from))) {
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(to))) {
                byte[] buffer = new byte[1024];
                int lengthRead;

                while ((lengthRead = in.read(buffer)) > 0) {
                    out.write(buffer, 0, lengthRead);
                }
            }
        }
    }
}