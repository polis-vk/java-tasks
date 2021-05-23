package ru.mail.polis.homework.io;


import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path to = Paths.get(pathTo);
        Path from = Paths.get(pathFrom);
        try {
            if (!Files.isDirectory(from)) {
                Files.createDirectories(to.getParent());
                copy(from, to);
                return pathTo;
            }
            Files.createDirectories(to);
            DirectoryStream<Path> stream = Files.newDirectoryStream(from);
            for (Path element : stream) {
                Path newTo = to.resolve(element.getFileName());
                if (Files.isDirectory(element)) {
                    copyFiles(element.toString(), newTo.toString());
                } else {
                    copy(element, newTo);
                }
            }
            stream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }


    private static void copy(Path from, Path to) throws IOException {
        try (InputStream is = new FileInputStream(from.toString()); OutputStream os = new FileOutputStream(to.toString())) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        }
    }
}
