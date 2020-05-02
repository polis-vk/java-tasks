package ru.mail.polis.homework.io;

import java.io.*;
import java.util.Objects;

public class CopyFile {
    
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        File parent = new File(fileTo.getParent());
        if (!parent.exists()) {
            parent.mkdirs();
        }
        if (fileFrom.isDirectory()) {
            fileTo.mkdir();
            for (String tmpPath : Objects.requireNonNull(fileFrom.list())) {
                String addedPath = File.separator + tmpPath;
                copyFiles(pathFrom + addedPath, pathTo + addedPath);
            }
        } else if (fileFrom.isFile()) {
            fileTo.createNewFile();
            copyFileData(fileFrom, fileTo);
        }
        return pathTo;
    }
    
    private static void copyFileData(File fileFrom, File fileTo) {
        try (InputStream in = new FileInputStream(fileFrom);
             OutputStream out = new FileOutputStream(fileTo)) {
            byte[] bytes = new byte[4096];
            while (in.read(bytes) != -1) {
                out.write(bytes);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
