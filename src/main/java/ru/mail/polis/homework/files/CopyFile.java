package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Comparator;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File srcFile = new File(pathFrom);
        File dstFile = new File(pathTo);
        if (!srcFile.exists() || dstFile.exists()) {
            return null;
        }
        copyDir(srcFile, dstFile);
        return null;
    }

    private static void copyDir(File srcFile, File dstFile) {
        if (srcFile.isDirectory()) {
            dstFile.mkdirs();
            Arrays.stream(srcFile.listFiles())
                    .sorted(Comparator.reverseOrder())
                    .forEachOrdered(file -> {
                        File newDstFile = new File(dstFile.getAbsolutePath() + File.separator + file.getName());
                        if (file.isDirectory()) {
                            copyDir(file, newDstFile);
                        } else {
                            copyFile(file, newDstFile);
                        }
                    });
        } else {
            File dstParentFile = dstFile.getParentFile();
            if (!dstParentFile.exists()) {
                dstParentFile.mkdirs();
            }
            copyFile(srcFile, dstFile);
        }
    }

    private static void copyFile(File srcFile, File dstFile) {
        if (!srcFile.exists() && !srcFile.isFile() && dstFile.exists()) {
            return;
        }
        try (BufferedReader readFile = Files.newBufferedReader(srcFile.toPath())) {
            try (BufferedWriter writeFile = Files.newBufferedWriter(dstFile.toPath())) {
                readFile.lines()
                        .forEachOrdered(line -> {
                            try {
                                writeFile.write(line + "\n");
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        });
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
