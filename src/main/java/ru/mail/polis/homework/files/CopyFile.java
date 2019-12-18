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

//        if (copyFrom.isFile()) {
//            if (!copyTo.exists()) {
//                new File(copyTo.getParent()).mkdirs();
//                try {
//                    copyTo.createNewFile();
//                    copy(copyFrom, copyTo);
//                } catch (IOException e) {}
//
//            }
//        }

        if (copyFrom.isFile()) {
            if (!copyTo.exists()) {
                String cpt = copyTo.getParent();
                File f = new File(cpt);
                        f.mkdirs();
                try {
                    copyTo.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            try {
                copy(copyFrom, copyTo);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (copyFrom.isDirectory()) {
            if (!copyTo.exists()) {
                copyTo.mkdirs();
            }
            File[] files = copyFrom.listFiles();
            for (File f : files) {
                File cpTo = new File(copyTo.getAbsolutePath() + File.separator + f.getName());
                if (f.isFile()) {
                    try {
                        copy(copyFrom, copyTo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (f.isDirectory()) {
                    copySmallFiles(f.getAbsolutePath() + File.separator + f.getName(),
                            cpTo.getAbsolutePath() + File.separator + cpTo.getName());
                }
            }
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
