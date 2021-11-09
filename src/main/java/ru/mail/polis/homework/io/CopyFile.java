package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

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
            return null;
        }
        try {
            if (Files.isRegularFile(from)) {
                if (Files.isDirectory(to)) {
                    return null;
                }
                if (!Files.exists(to.getParent())) {
                    Files.createDirectories(to.getParent());
                }
                copyFile(from, to);
                return pathTo;
            }
            if (!Files.exists(to)) {
                Files.createDirectories(to);
            }
            copyDirectory(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return pathTo;
    }

    public static void copyDirectory(Path fromDirectory, Path toDirectory) {
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(fromDirectory)) {
            if (!Files.exists(toDirectory)) {
                Files.createDirectory(toDirectory);
            }
            for (Path path : directoryStream) {
                if (Files.isDirectory(path)) {
                    copyDirectory(path, Paths.get(toDirectory.toString(), path.getFileName().toString()));
                } else {
                    copyFile(path, Paths.get(toDirectory.toString(), path.getFileName().toString()));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void copyFile(Path fromFile, Path toFile) throws IOException {
        try (InputStream in = Files.newInputStream(fromFile);
             OutputStream out = Files.newOutputStream(toFile)) {
            byte[] buffer = new byte[1024];
            int blockSize = 0;
            do {
                blockSize = in.read(buffer);
                out.write(buffer, 0, blockSize == -1 ? 0 : blockSize);
            } while (blockSize > 0);
        }
    }

}
