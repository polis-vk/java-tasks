package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.FileNotFoundException;

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
            copyFile(
                    nextFile.getAbsolutePath(),
                    fileTo + File.separator + nextFile.getName()
            );
          } else {
            copySmallFiles(
                    nextFile.getAbsolutePath(),
                    nextFile.getAbsolutePath() + File.separator + nextFile.getName()
            );
          }
        }
      }
      return pathTo;
    }

    public static void copyFile(String pathFrom, String pathTo) {
      try (BufferedReader in = new BufferedReader(new FileReader(new File(pathFrom)))) {
        File file = new File(pathTo);
        if (!file.exists()) {
          file.createNewFile();
        }
        try (BufferedWriter out = new BufferedWriter(new FileWriter(new File(pathTo)))) {
          String nextLine;
          while (null != (nextLine = in.readLine())) {
            out.append(nextLine).append("\n");
          }
        }
      } catch (FileNotFoundException e) {
        e.printStackTrace();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
}
