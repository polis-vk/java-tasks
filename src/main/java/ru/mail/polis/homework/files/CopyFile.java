package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */

    public static String copySmallFiles(String pathFrom, String pathTo) {
        File src = new File(pathFrom);
        File dst = new File(pathTo);
        if (src.exists()) {
            try {
                copy(src, dst);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    private static void copy(File src, File dst) throws IOException{
        if (src.isDirectory()){
            if (!dst.exists()){
                dst.mkdirs();
            }
            String[] files = src.list();
            for (String file : files){
                File srcFile = new File(src, file);
                File dstFile = new File(dst, file);
                copy(srcFile, dstFile);
            }
        } else{
            File parent = dst.getParentFile();
            if (!parent.exists()){
                parent.mkdirs();
                dst.createNewFile();
            }
            BufferedInputStream in = new BufferedInputStream(new FileInputStream(src));
            BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream(dst));
            while (in.available() > 0){
                int length = in.read();
                out.write(length);
                out.flush();
            }
            in.close();
            out.close();
        }
    }
}
