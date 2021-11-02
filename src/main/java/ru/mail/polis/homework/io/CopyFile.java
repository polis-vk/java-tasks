package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.ByteBuffer;
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
        if (pathFrom.isEmpty() || pathTo.isEmpty() || pathFrom.equals(pathTo)) {
            return null;
        }

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (Files.notExists(from) || from.toAbsolutePath().equals(to.toAbsolutePath())) {
            return null;
        }

        try {
            deepCopy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathTo; // ????
    }

    private static void deepCopy(Path from, Path to) throws IOException {
        if (Files.isDirectory(from)) {
            Files.createDirectories(to);
            try (DirectoryStream<Path> dir = Files.newDirectoryStream(from)) {
                for (Path path : dir) {
                    deepCopy(path, to.resolve(path.getFileName()));
                }
            }
        } else {
            if (Files.notExists(to.getParent())) {
                Files.createDirectories(to.getParent());
            }
            // Files.copy(from, to);
            copyFile(from, to);
        }
    }
    
    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
            OutputStream out = Files.newOutputStream(to)) {

            byte[] buffer = new byte[1024];
            int size;
            while ((size = in.read(buffer)) > 0) {
                out.write(buffer, 0, size);
                size = in.read(buffer);
            }
            out.flush();
        }
    }

}
