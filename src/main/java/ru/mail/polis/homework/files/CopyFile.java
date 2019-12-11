package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    private static final String success = "successful: ";
    private static final String failure = "failure: ";
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File folder_in = new File(pathFrom);
        if(!folder_in.isDirectory() && !folder_in.exists()) {
            return failure+"Нет такой папки";
        }
        for (String fileName : folder_in.list()) {
            try (FileInputStream inputStream = new FileInputStream(folder_in + File.separator + fileName)) {
                File folder_out = new File(pathTo);
                if (!folder_out.exists()) {
                    folder_out.mkdir();
                }

                try (FileOutputStream outputStream = new FileOutputStream(new File(folder_out + File.separator + fileName))) {
                    byte[] bytes = new byte[inputStream.available()];
                    int length;

                    while ((length = inputStream.read(bytes)) > 0) {
                        outputStream.write(bytes, 0, length);
                    }
                } catch (IOException e) {
                    return failure+"Output error";
                }
            } catch (IOException e) {
                return failure+"Input error";
            }
        }

        return success+pathTo;
    }

    public static void main(final String[] arg) {
        try (PrintWriter out = new PrintWriter(System.out)) {
            out.println(
                    copySmallFiles(
                    "src/main/java/ru/mail/polis/homework/files/sample_folder",
                    "src/main/java/ru/mail/polis/homework/files/out_folder")
            );
        }
    }
}
