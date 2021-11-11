package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {
    
    public static final int BYTE_BUFFER_SIZE = 1024;

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

        if (Files.notExists(from)) {
            return null;
        }

        try {
            // создаем все родительские папки, один раз
            Files.createDirectories(to.getParent()); 
            deepCopy(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return pathTo; // ????
    }

    private static void deepCopy(Path from, Path to) throws IOException {
        // вместо !Files.isDirectory(from) для наглядности
        // хотя в общем случае это не одно и то же,
        // но для задачи копируются только файлы и папки
        if (Files.isRegularFile(from)) {
            // Files.copy(from, to);
            copyFile(from, to);
            return;
        }
        // для копирования подпапок и пустых папок
        if (Files.notExists(to)) {
            Files.createDirectory(to);
        }

        try (DirectoryStream<Path> dir = Files.newDirectoryStream(from)) {
            for (Path path : dir) {
                deepCopy(path, to.resolve(path.getFileName()));
            }
        }
    }
    
    
    private static void copyFile(Path from, Path to) throws IOException {
        try (InputStream in = Files.newInputStream(from);
            OutputStream out = Files.newOutputStream(to)) {

            byte[] buffer = new byte[BYTE_BUFFER_SIZE];
            int size;
            while ((size = in.read(buffer)) > 0) {
                out.write(buffer, 0, size);
                size = in.read(buffer);
            }
        }
    }

}
