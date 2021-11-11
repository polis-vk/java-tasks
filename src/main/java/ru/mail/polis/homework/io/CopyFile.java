package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. �?сползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo){
        if((pathFrom == null) || (pathTo == null) || (Files.notExists(Paths.get(pathFrom)))){
            return null;
        }
        Path to = Paths.get(pathTo);
        Path from = Paths.get(pathFrom);
        try{
            if(Files.notExists(to.getParent())){ Files.createDirectories(to.getParent());}
            if(Files.isRegularFile(from)){ copyFile(from, to); return null;}

            Files.createDirectory(to);
            DirectoryStream<Path> dstream = Files.newDirectoryStream(from);
            dstream.forEach(path -> copyFiles(path.toString(), to.resolve(path.getFileName()).toString()));
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFile(Path from, Path to) throws IOException{
        try(
                InputStream in   = Files.newInputStream(from);
                OutputStream out = Files.newOutputStream(to)){
            byte[] buffer = new byte[1024];
            int buff2;
            while ((buff2 = in.read(buffer)) > 0) {
                out.write(buffer, 0, buff2);
                out.flush();
            }
        }
    }
}
