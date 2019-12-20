package ru.mail.polis.homework.files;

import java.io.*;
import java.util.Arrays;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File fileFrom = new File (pathFrom);
        File fileTo = new File(pathTo);
        copy(fileFrom,fileTo);
        return null;
    }

    private static void copy(File fileFrom, File fileTo) {
        if (fileFrom.exists() && fileFrom.isDirectory()){
            if (!fileTo.exists()) {
                fileTo.mkdirs();
            }
            Stream<File> files = Arrays.stream(fileFrom.listFiles());
            files.forEach(file -> {
                if (file.isDirectory()) {
                    copy(file, new File(fileTo.getAbsolutePath() + File.separator + file.getName()));
                } else {
                    try {
                        copyFile(file, new File(fileTo.getAbsolutePath() + File.separator + file.getName()));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            });
        } else {
            try {
                copyFile(fileFrom, fileTo);
            } catch (IOException e){
                e.printStackTrace();
            }
        }
    }
    private static void copyFile(File from, File to) throws IOException {
        File parentFile = to.getParentFile();
        if (!parentFile.exists()){
            parentFile.mkdirs();
            try {
                to.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
            }
        } else {
            try {
                parentFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (
                InputStream in = new BufferedInputStream(new FileInputStream(from));
                OutputStream out = new BufferedOutputStream(new FileOutputStream(to))) {
            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
            }
        }
    }
}
