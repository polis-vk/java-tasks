package ru.mail.polis.homework.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(final String pathFrom, final String pathTo) throws IOException {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);
        if (fileFrom.exists() && fileFrom.isDirectory() && !fileTo.exists()) {
            fileTo.mkdirs();
            for (final String fileName : fileFrom.list()) {
                String subFileFrom = fileFrom.getAbsolutePath() + File.separator + fileName;
                String subFileTo = fileTo.getAbsolutePath() + File.separator + fileName;
                if (Files.isDirectory(Paths.get(subFileFrom))) {
                    copySmallFiles(subFileFrom, subFileTo);
                } else {
                    copyFile(subFileFrom, subFileTo);
                }
            }
            return pathTo;
        }
        return null;
    }

    private static void copyFile(final String fileFrom, final String pathTo) throws IOException {
        final File from = new File(fileFrom);
        final File to = new File(pathTo);
        if (from.exists() && from.isFile()) {
            try (InputStream inputStream = new FileInputStream(from);
                 OutputStream outputStream = new FileOutputStream(to)) {

                byte[] buffer = new byte[1024];
                int bytes;
                if ((bytes = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, bytes);
                }
            }
        }
    }
}
