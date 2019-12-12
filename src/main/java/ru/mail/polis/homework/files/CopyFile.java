package ru.mail.polis.homework.files;

import java.io.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class CopyFile {

    private static final String success = "Успех: ";
    private static final String failure = "Неудача: ";
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     *
     * modified by БорискинМА
     * 12.12.19
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        try {
            return dirs(pathFrom, pathTo);
        } catch (Exception e) {
            Logger.getLogger(CopyFile.class.getName()).log(Level.SEVERE, null, e);
        }
        return null;
    }

    private static String dirs(String pathFrom, String pathTo) throws IOException {
        File inputFolder = new File(pathFrom);
        if (!inputFolder.exists()) {
            return failure+"нет такой папки";
        }
        File outputFolder = new File(pathTo);
        if (!outputFolder.exists()) {
            outputFolder.mkdir();
        }
        System.out.println("Начинаем копирование...");
        File[] sourceArray = new File[1];
        sourceArray[0] = inputFolder;
        copyFilesAndDirs(sourceArray, pathTo);
        return success+pathFrom;
    }

    private static void copyFilesAndDirs(File[] sourceArray, String pathTo) throws IOException{
        for (int i = 0; i <= sourceArray.length - 1; i++) {
            // в pathTo папке
            File outputFolder = new File(pathTo + "/" + sourceArray[i].getName());
            // если это папка, уходим в рекурсию
            if (sourceArray[i].isDirectory()) {
                outputFolder.mkdir();
                File[] newPaths = sourceArray[i].listFiles();
                copyFilesAndDirs(newPaths, outputFolder.getAbsolutePath());
            }
            else if (sourceArray[i].isFile()) {
                copyFile(sourceArray[i].getAbsolutePath(), outputFolder.getAbsolutePath());
            }
            else {
                System.out.println("Ни такой папки, ни такого файла здесь нет: " + sourceArray[i].getAbsolutePath());
            }
        }
    }

    private static void copyFile(String pathFrom, String pathTo) throws IOException {
        File pathFromFile = null;
        File pathToFile = null;
        FileInputStream inputStream = null;
        FileOutputStream outputStream = null;
        BufferedInputStream bufferedSource = null;
        BufferedOutputStream bufferedDestination = null;
        try {
            pathFromFile = new File(pathFrom);
            pathToFile = new File(pathTo);
            inputStream = new FileInputStream(pathFromFile);
            outputStream = new FileOutputStream(pathToFile);
            bufferedSource = new BufferedInputStream(inputStream, 8192);
            bufferedDestination = new BufferedOutputStream(outputStream, 8192);
            int transfer;
            while ((transfer = bufferedSource.read()) != -1) {
                bufferedDestination.write(transfer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(CopyFile.class.getName()).log(Level.SEVERE, null, e);
        } finally {
            if (bufferedSource != null)
                bufferedSource.close();
            if (bufferedDestination != null)
                bufferedDestination.close();
        }
    }

    public static void main(final String[] arg) throws Exception {
        try {
            System.out.println(
                    copySmallFiles(
                            "src/main/java/ru/mail/polis/homework/files/sample_folder",
                            "src/main/java/ru/mail/polis/homework/files/out_folder"));
        } catch (Exception e) {
            Logger.getLogger(CopyFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
