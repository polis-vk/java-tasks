package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) throws IOException {
        File copyFrom = Paths.get(pathFrom).toFile();
        File copyTo = Paths.get(pathTo).toFile();

        if (!copyFrom.exists() && !copyFrom.isDirectory() && copyTo.isFile()) return null;
        if (!copyTo.exists()) copyTo.mkdir();

        BufferedReader reader;
        BufferedWriter writer;

        File[] files = copyFrom.listFiles();

        for (File f : files) {
            if (f.isFile()) {
                String buff;
                try {
                    reader = new BufferedReader(new FileReader(f));
                    writer = new BufferedWriter(new FileWriter(copyTo));
                    while ((buff = reader.readLine()) != null) writer.write(buff);
                    reader.close();
                    writer.close();
                } catch (IOException e) {}
            } else if (f.isDirectory()) {
                String nameDir = f.getName();
                File dirTo = new File(copyTo.getAbsolutePath() + File.separator + nameDir);
                copySmallFiles(dirTo.getParent(), dirTo.getAbsolutePath());
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException {
        copySmallFiles("C:\\Users\\null\\Desktop\\java-tasks\\src\\main\\java\\ru\\mail\\polis\\homework\\files\\cp", "C:\\Users\\null\\Desktop\\java-tasks\\src\\main\\java\\ru\\mail\\polis\\homework\\files\\cpTo");

    }

}
