package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.DirectoryStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.Files;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */

    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        detourAllFoldersAndCopyFiles(Paths.get(pathFrom), Paths.get(pathTo));
    }

    private static void detourAllFoldersAndCopyFiles(Path source, Path destination) throws IOException {
        if (!Files.exists(source)){
            return;
        }
        if (Files.isRegularFile(source)) {
            copyFileIncludes(source, destination);
            return;
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(source)) {
            if (Files.isDirectory(source)) {
                Files.createDirectories(destination);
            }
            for (Path path : files) {
                if (Files.isRegularFile(path)) {
                    destination = destination.resolve(path.getFileName());
                    copyFileIncludes(path, destination);
                } else {
                    destination = destination.resolve(path.getFileName());
                    detourAllFoldersAndCopyFiles(path, destination);
                }
                destination = destination.getParent();
            }
        } catch (IOException e) {
            System.out.println("Tried copy non-existent directory");
        }
    }

    private static void copyFileIncludes(Path source, Path destination) throws IOException {
        Files.createDirectories(destination.getParent());
        try (InputStream is = Files.newInputStream(source)) {
            try (OutputStream os = Files.newOutputStream(destination)) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = is.read(buffer)) > 0) {
                    os.write(buffer, 0, length);
                }
            }
        }
    }

}
