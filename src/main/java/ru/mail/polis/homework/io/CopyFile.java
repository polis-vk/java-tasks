package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    private static final int CAPACITY = 1024;

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (pathFrom.isEmpty() && pathTo.isEmpty()) {
            return;
        }
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (Files.notExists(from)) {
            return;
        }
        try {
            if (Files.isRegularFile(from)) { //check a file is a regular file with opaque content
                Files.createDirectories(to.getParent());
            } else {
                Files.createDirectories(to);
            }
            if (Files.exists(from)) {
                deepCopy(from, to);
            }
            if (!Files.isDirectory(from)) {
                copy(from, to);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
             OutputStream out = Files.newOutputStream(to)) {
            byte[] buffer = new byte[CAPACITY];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void deepCopy(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            copy(from, to);
        }
        if (Files.isDirectory(from)) {
            try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
                for (Path file : directoryStream) {
                    if (Files.isDirectory(file)) {
                        Files.createDirectory(Path.of(to + File.separator + file.getFileName()));
                    }
                    deepCopy(file, Paths.get(to + File.separator + file.toString().substring(to.toString().length())));
                }
            }
        }
    }
}
