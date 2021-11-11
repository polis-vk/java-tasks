package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return "No such source path";
        }
        try {
            prepareFiles(from, to);
            return recursiveCopy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void prepareFiles(Path from, Path to) throws IOException {
        if (!Files.isReadable(from)) {
            throw new InvalidPathException(from.toString(), "Path point to unreadable source");
        }

        if (Files.exists(to) && !Files.isWritable(to)) {
            throw new InvalidPathException(from.toString(), "Path point to unwritable destination");
        }

        if (!Files.exists(to.getParent())) {
            Files.createDirectories(to.getParent());
        }

        if (!Files.isRegularFile(from)) {
            recursiveCreateDirectories(from, to);
        }
    }

    private static String recursiveCopy(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            return copyFile(from, to);
        }

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
            for (Path file: directoryStream) {
                recursiveCopy(file, to.resolve(file.getFileName()));
            }
        }
        return null;
    }

    private static String copyFile(Path from, Path to) throws IOException {
        try (InputStream fin = Files.newInputStream(from);
             OutputStream fout = Files.newOutputStream(to)) {
            byte[] bytes = new byte[fin.available()];
            int sizeOfReadBytes;
            while ((sizeOfReadBytes = fin.read(bytes)) > 0) {
                fout.write(bytes, 0, sizeOfReadBytes);
            }
            return null;
        }
    }

    private static void recursiveCreateDirectories(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            return;
        }

        Files.createDirectory(to);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
            for (Path file: directoryStream) {
                recursiveCreateDirectories(file, to.resolve(file.getFileName()));
            }
        }
    }

}