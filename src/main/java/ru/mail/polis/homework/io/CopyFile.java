package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;


public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {

        File fromDirectory = new File(pathFrom);
        File toDirectory = new File(pathTo);
        if (fromDirectory.exists()) {
            if (fromDirectory.isDirectory()) {
                if (!toDirectory.exists()) {
                    Files.createDirectories(Paths.get(toDirectory.toString()));
                }

                String[] files = fromDirectory.list();

                for (String file : files) {
                    String fromFile = new File(fromDirectory, file).toString();
                    String toFile = new File(toDirectory, file).toString();
                    copyFiles(fromFile, toFile);
                }

            } else {
                Files.createDirectories(Paths.get(toDirectory.getParent()));
                Files.createFile(toDirectory.toPath());
                copyFileStream(fromDirectory, toDirectory);
            }
        }
        return null;
    }




   public static void copyFileStream(File fromFile, File toFile) throws IOException {

        try (InputStream is = new FileInputStream(fromFile); OutputStream os = new FileOutputStream(toFile)) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }

}
