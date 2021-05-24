package ru.mail.polis.homework.io;


import java.io.*;
import java.nio.channels.FileChannel;
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
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(from)) {
                for (Path element : stream) {
                    Path newTo = to.resolve(element.getFileName());
                    if (Files.isDirectory(element)) {
                        copyFiles(element.toString(), newTo.toString());
                    } else {
                        copy(element, newTo);
                    }
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }


    private static void copy(Path from, Path to) throws IOException {
        try (FileChannel in = new FileInputStream(from.toString()).getChannel()) {
            try(FileChannel out = new FileOutputStream(to.toString()).getChannel()) {
                out.transferFrom(in,0,in.size());
                }
            }
        }
}
