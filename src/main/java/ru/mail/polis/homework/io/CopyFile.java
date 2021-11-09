package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return null;
        }
        Path from = Path.of(pathFrom);
        Path to = Path.of(pathTo);
        try {
            Files.createDirectories(to.getParent());
            copyFilesRecursively(from, to);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFilesRecursively(Path from, Path to) throws IOException {
        if (Files.isRegularFile(from)) {
            copyOneFile(from, to);
            return;
        }
        if (!Files.exists(to)) {
            Files.createDirectory(to);
        }
        try (DirectoryStream<Path> folder = Files.newDirectoryStream(from)) {
            for (Path path : folder) {
                copyFilesRecursively(path, to.resolve(path.getFileName()));
            }
        }
    }

    private static void copyOneFile(Path from, Path to) throws IOException {
        byte[] buffer = new byte[1024];
        int size;
        try (FileInputStream in = new FileInputStream(String.valueOf(from));
             FileOutputStream out = new FileOutputStream(String.valueOf(to))) {
            while ((size = in.read(buffer)) > 0) {
                out.write(buffer, 0, size);
            }
        }
    }
}
