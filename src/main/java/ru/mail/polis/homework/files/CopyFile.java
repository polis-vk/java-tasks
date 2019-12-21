package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    public static String copySmallFiles(String pathFrom, String pathTo) {
        File copyFrom = new File(pathFrom);
        File copyTo = new File(pathTo);

        try {
            if (copyFrom.isFile()) {
                if (!copyTo.exists()) {
                    new File(copyTo.getParent()).mkdirs();
                    copyTo.createNewFile();
                }
                copy(copyFrom, copyTo);
            } else if (copyFrom.isDirectory()) {
                if (!copyTo.exists()) {
                    copyTo.mkdirs();
                }
                File[] files = copyFrom.listFiles();
                for (File f : files) {
                    File cpTo = new File(copyTo.getAbsolutePath() + File.separator + f.getName());
                    if (f.isFile()) {
                        copy(f, cpTo);
                    } else if (f.isDirectory()) {
                        copySmallFiles(f.getAbsolutePath(), cpTo.getAbsolutePath());
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }

    private static void copy(File from, File to) throws IOException {
        try (InputStream is = new BufferedInputStream(new FileInputStream(from))) {
            try (OutputStream os = new BufferedOutputStream(new FileOutputStream(to))) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
        }
    }

}