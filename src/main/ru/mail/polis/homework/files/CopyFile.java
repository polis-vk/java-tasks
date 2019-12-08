package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) throws IOException {
       Path pFrom = Path.of(pathFrom).toAbsolutePath();
       Path pTo = Path.of(pathTo).toAbsolutePath();

       if (Files.isDirectory(pFrom)){
           try {
               System.out.println(Paths.get(pTo.toString()));
               Files.createDirectory(Paths.get(pTo.toString()));
               List<Path> s = Files.list(pFrom).collect(Collectors.toList());
               for (Path i: s){
                   System.out.println(Paths.get(pTo.toString(), i.getFileName().toString()).toString());
                  copySmallFiles(i.toString(), Paths.get(pTo.toString(), i.getFileName().toString()).toString());
               }
               System.out.println();
           } catch (IOException e) {
               e.printStackTrace();
           }


       }else{
           copyFile(pFrom, pTo);
       }
       return pathTo;
    }
    private static void copyFile(Path from, Path to){
        try {
            FileInputStream in = new FileInputStream(from.toString());
            FileOutputStream out = new FileOutputStream(to.toString());
            byte[] buffer = new byte[1024];
            int b = in.read(buffer);
            while (b > 0) {
                out.write(buffer, 0, b);
                b = in.read(buffer);
            }
            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    }
