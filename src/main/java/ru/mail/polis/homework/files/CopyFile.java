package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class CopyFile {

    private static Logger logger = Logger.getLogger(CopyFile.class.getName());

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     *
     * modified by БорискинМА
     * 14.12.19
     */
    public static String copySmallFiles(final String pathFrom, final String pathTo) {
        try {
            return dirs(pathFrom, pathTo);
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
        return null;
    }

    private static String dirs(final String pathFrom, final String pathTo) throws IOException {
        File inputFolder = new File(pathFrom);
        if (!inputFolder.exists()) {
            return null;
        }
        String newPathTo = pathTo;
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
                newPathTo = parentPath.toString();
            } else {
                outputFolder.mkdirs();
            }
        }
        System.out.println("Начинаем копирование...");
        logger.log(Level.INFO, String.valueOf((int) Files.walk(Paths.get(pathFrom)).count()));
        File[] sourceArray = new File[1];
        sourceArray[0] = inputFolder;
        copyFilesAndDirs(sourceArray, newPathTo, inputFolder.getAbsolutePath());
        logger.log(Level.INFO, String.valueOf((int) Files.walk(Paths.get(pathTo)).count()));
        return pathFrom;
    }

    private static boolean isFolderName(File name, final String inputDir) {
        return name.getAbsolutePath().equals(inputDir);
    }

    private static void copyFilesAndDirs(File[] sourceArray, String parentDir, final String inputDir) {
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
            } else if (sourceArray[i].isFile()) {
                copyFile(sourceArray[i].getAbsolutePath(), outputFolder.getAbsolutePath());
            } else {
                System.out.println("Ни такой папки, ни такого файла здесь нет: " + sourceArray[i].getAbsolutePath());
            }
        }
    }

    private static void copyFile(final String pathFrom, final String pathTo) {
        try (BufferedInputStream bufferedSource = new BufferedInputStream(new FileInputStream(new File(pathFrom)), 8192);) {
            try (BufferedOutputStream bufferedDestination = new BufferedOutputStream(new FileOutputStream(new File(pathTo)), 8192)) {
                int transfer;
                while ((transfer = bufferedSource.read()) != -1) {
                    bufferedDestination.write(transfer);
                }
            } catch (IOException e) {
                e.printStackTrace();
                logger.log(Level.SEVERE, "output error", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
            logger.log(Level.SEVERE, "input error", e);
        }
    }

    public static void main(final String[] arg) {
        try {
            System.out.println(
                    copySmallFiles(
                            "src/main/java/ru/mail/polis/homework/files/sample_folder.txt",
                            "src/main/java/ru/mail/polis/homework/sample_folder.txt"));
        } catch (Exception e) {
            logger.log(Level.SEVERE, null, e);
        }
    }
}
