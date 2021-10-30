package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    public static final short bufferSize = 1024;
    public static final byte[] buffer = new byte[bufferSize];


    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path source = Paths.get(pathFrom);
        Path destination = Paths.get(pathTo);
        if (!Files.exists(source)) {
            return;
        }
        try {
            if (!Files.isDirectory(source)) {
                fileCopy(source, destination);
            } else {
                deepDirectoryCopy(source, destination);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void fileCopy(Path source, Path destination) throws IOException {
        Path destinationDirectory = destination.endsWith(source.getFileName()) ? destination.getParent() : destination;
        if (!Files.exists(destinationDirectory)) {
            Files.createDirectories(destinationDirectory);
        }
        copyFileData(source, Paths.get(destinationDirectory.toString(), source.getFileName().toString()));
    }

    private static void deepDirectoryCopy(Path source, Path destination) throws IOException {
        if (Files.isDirectory(source)) {
            if (!Files.exists(destination)) {
                Files.createDirectories(destination);
            }
            try (DirectoryStream<Path> stream = Files.newDirectoryStream(source)) {
                String fileName;
                for (Path file : stream) {
                    fileName = file.getFileName().toString();
                    deepDirectoryCopy(Paths.get(source.toString(), fileName), Paths.get(destination.toString(), fileName));
                }
            }
        } else {
            copyFileData(source, destination);
        }
    }

    private static void copyFileData(Path source, Path destination) throws IOException {
        try (InputStream in = new FileInputStream(source.toFile());
             OutputStream out = new FileOutputStream(destination.toFile())
        ) {
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }
}

