package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        File fileFrom = new File(pathFrom);
        File fileTo = new File(pathTo);

        if (!fileFrom.exists()) {
            return null;
        }

        if (fileFrom.isFile()) {
            if (!fileTo.exists()) {
                new File(fileTo.getParent()).mkdirs();
            }
            copyFile(pathFrom, pathTo);
        } else {
            if (!fileTo.exists()) {
                fileTo.mkdirs();
            } else if (fileTo.isFile()) {
                return null;
            }
            for (File nextFile : fileFrom.listFiles()) {
                if (nextFile.isFile()) {
                    copyFile(nextFile.getAbsolutePath(),
                            fileTo + File.separator + nextFile.getName());
                } else {
                    copySmallFiles(nextFile.getAbsolutePath(),
                            fileTo + File.separator + nextFile.getName());
                }
            }
        }
        return pathTo;
    }

    public static void copyFile(String pathFrom, String pathTo) {
        File file = new File(pathTo);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (BufferedReader in = new BufferedReader(new FileReader(new File(pathFrom)))) {
            try (BufferedWriter out = new BufferedWriter(new FileWriter(file))) {
                String nextLine;
                while (null != (nextLine = in.readLine())) {
                    out.append(nextLine).append("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
