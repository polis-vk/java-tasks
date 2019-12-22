package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File from = new File(pathFrom);
        File to = new File(pathTo);

        if (pathFrom.equals(pathTo) || !from.exists()) {
            return null;
        }

        if (from.isDirectory()) {
            if (!to.exists()) {
                to.mkdirs();
            } else if (to.isFile()) {
                return null;
            }
            Arrays.stream(Objects.requireNonNull(from.listFiles()))
                    .forEach(file -> {
                        String pathOfNewFile = Paths.get(to.getAbsolutePath() + File.separator + file.getName()).toString();
                        File newFile = new File(pathOfNewFile);
                        if (file.isDirectory()) {
                            copySmallFiles(file.getAbsolutePath(), newFile.getAbsolutePath());
                        } else {
                            copyFiles(file, newFile);
                        }
                    });

        } else {
            if (!to.exists()) {
                new File(to.getParent()).mkdirs();
            }
            copyFiles(from, to);
        }

        return pathTo;
    }

    private static void copyFiles(File from, File to) {
        if (from.exists() && from.isFile()) {
            try (BufferedReader reader = new BufferedReader(new FileReader(from))) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(to))) {
                    reader.lines()
                            .forEach(line -> {
                                try {
                                    writer.write(line + "\n");
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            });
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
