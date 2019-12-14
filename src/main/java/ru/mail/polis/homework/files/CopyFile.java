package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     *
     * modified by БорискинМА
     * 14.12.19
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
            return null;
        }
        File outputFolder = new File(pathTo);
        if (outputFolder.exists()) {
            if (outputFolder.isFile() && inputFolder.isFile()) {
                copyFile(pathFrom,pathTo);
                return pathTo;
            } else if (outputFolder.isFile() && inputFolder.isDirectory()) {
                return null;
            }
        } else {
            if (inputFolder.isFile()) {
                File parentPath = new File(outputFolder.getParent());
                if (!parentPath.exists()) {
                    parentPath.mkdirs();
                }
                pathTo = parentPath.toString();
            } else {
                outputFolder.mkdirs();
            }
        }
        System.out.println("Начинаем копирование...");
        System.out.println((int) Files.walk(Paths.get(pathFrom)).count());
        File[] sourceArray = new File[1];
        sourceArray[0] = inputFolder;
        copyFilesAndDirs(sourceArray, pathTo, inputFolder.getAbsolutePath());
        System.out.println((int) Files.walk(Paths.get(pathTo)).count());
        return pathFrom;
    }

    private static boolean isFolderName(File name, String inputDir) {
        return name.getAbsolutePath().equals(inputDir);
    }

    private static void copyFilesAndDirs(File[] sourceArray, String parentDir, String inputDir) {
        for (int i = 0; i <= sourceArray.length - 1; i++) {
            File outputFolder = new File(parentDir + "/" + sourceArray[i].getName());
            if (sourceArray[i].isDirectory()) {
                if (isFolderName(sourceArray[i], inputDir)) {
                    outputFolder = new File(parentDir);
                } else {
                    outputFolder.mkdir();
                }
                File[] newPaths = sourceArray[i].listFiles();
                copyFilesAndDirs(newPaths, outputFolder.getAbsolutePath(), inputDir);
            }
            else if (sourceArray[i].isFile()) {
                copyFile(sourceArray[i].getAbsolutePath(), outputFolder.getAbsolutePath());
            }
            else {
                System.out.println("Ни такой папки, ни такого файла здесь нет: " + sourceArray[i].getAbsolutePath());
            }
        }
    }

    private static void copyFile(String pathFrom, String pathTo) {
        try (BufferedInputStream bufferedSource = new BufferedInputStream(new FileInputStream(new File(pathFrom)), 8192);
             BufferedOutputStream bufferedDestination = new BufferedOutputStream(new FileOutputStream(new File(pathTo)), 8192)) {
            int transfer;
            while ((transfer = bufferedSource.read()) != -1) {
                bufferedDestination.write(transfer);
            }
        } catch (IOException e) {
            e.printStackTrace();
            Logger.getLogger(CopyFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }

    public static void main(final String[] arg) {
        try {
            System.out.println(
                    copySmallFiles(
                            "src/main/java/ru/mail/polis/homework/files/sample_folder.txt",
                            "src/main/java/ru/mail/polis/homework/sample_folder.txt"));
        } catch (Exception e) {
            Logger.getLogger(CopyFile.class.getName()).log(Level.SEVERE, null, e);
        }
    }
}
