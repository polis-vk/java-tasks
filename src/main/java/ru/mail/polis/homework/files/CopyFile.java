package ru.mail.polis.homework.files;

import java.io.*;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        final File copyDir = new File(pathFrom);
        final File pastDir = new File(pathTo);

        if (!copyDir.exists()) {
            return null;
        }

        if (copyDir.isDirectory()) {

            if (!pastDir.exists()) {
                pastDir.mkdirs();
            } else if (pastDir.isFile()) {
                return null;
            }

            for (File nextFile : Objects.requireNonNull(copyDir.listFiles())) {
                if (nextFile.isDirectory()) {
                    copySmallFiles(nextFile.getAbsolutePath(),
                            pastDir + File.separator + nextFile.getName());
                } else {
                    copyFile(nextFile.getAbsolutePath(),
                            pastDir + File.separator + nextFile.getName());
                }
            }

        } else {

            if (!pastDir.exists()) {
                new File(pastDir.getParent()).mkdirs();
            }
            copyFile(pathFrom, pathTo);
        }

        return pathTo;
    }

    private static void copyFile(final String pathFrom, final String pathTo) {
        try (BufferedReader in = new BufferedReader(new FileReader(new File(pathFrom)))) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(pathTo))) {
                String next;

                while ((next = in.readLine()) != null) {
                    out.write(next);
                }
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
