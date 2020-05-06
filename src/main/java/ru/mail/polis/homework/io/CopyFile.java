package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);

        if (fileFrom.isFile() && !fileTo.getParentFile().exists()) {
            fileTo.getParentFile().mkdirs();
        }

        try {
            copyOneFile(fileFrom, fileTo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
            return "¯\\_(ツ)_/¯";
        }

        return null;
    }

    private static void copyOneFile(File fileFrom, File fileTo) throws IOException {
        if (!fileFrom.exists()) {
            return;
        }
        if (fileFrom.isDirectory()) {
            if (!fileTo.exists()) {
                fileTo.mkdirs();
            }

            String[] files = fileFrom.list();
            if (files == null) {
                return;
            }
            for (String file : files) {
                File tempFileFrom = new File(fileFrom, file);
                File tempFileTo = new File(fileTo, file);

                copyOneFile(tempFileFrom, tempFileTo);
            }
        } else {
            InputStream streamIn = new FileInputStream(fileFrom);
            OutputStream streamOut = new FileOutputStream(fileTo);

            byte[] byteBuffer = new byte[1024];
            int length = 0;
            while ((length = streamIn.read(byteBuffer)) > 0) {
                streamOut.write(byteBuffer, 0, length);
            }

            streamIn.close();
            streamOut.close();
        }
    }
}
