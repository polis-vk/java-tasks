package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    /*public static String copySmallFiles(String pathFrom, String pathTo) {
        Path src = Paths.get(pathFrom);
        Path dest = Paths.get(pathTo);
        try {
            Files.walk(src).forEach(s -> {
                try {
                    Path d = dest.resolve(src.relativize(s));
                    if( Files.isDirectory(s)) {
                        if( !Files.exists(d)) {
                            Files.createDirectories(d);
                        }
                    }
                    else if (Files.isRegularFile(s)){
                        Path parent = d.getParent();
                        if (!Files.exists(parent)){
                            Files.createDirectories(parent);
                        }
                        Files.createFile(d);
                    }
                    Files.copy(s, d, StandardCopyOption.REPLACE_EXISTING);
                } catch(Exception e) {
                    e.printStackTrace();
                }
            });
        }
        catch (IOException ex){
            ex.printStackTrace();
        }
        return null;
    }*/

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
        }
        else{
            InputStream in = new FileInputStream(src);
            File parent = dst.getParentFile();
            if (!parent.exists()){
                parent.mkdirs();
                dst.createNewFile();
            }
            OutputStream out = new FileOutputStream(dst);

            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0){
                out.write(buffer, 0, length);
            }
            in.close();
            out.close();
        }
    }
}
