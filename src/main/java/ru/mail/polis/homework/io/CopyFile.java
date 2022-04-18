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

    private static void copyFileIncludes(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        Path destination = Paths.get(pathTo);
        Files.createDirectories(destination.getParent());
        try (InputStream is = Files.newInputStream(source);
             OutputStream os = Files.newOutputStream(destination)) {
            byte[] buffer = new byte[1024];
            int length = is.read(buffer);
            while (length > 0) {
                os.write(buffer, 0, length);
                length = is.read(buffer);
            }
        }
    }

    private static void detourAllFoldersAndCopyFiles(String src, String dest) throws IOException {
        Path source = Paths.get(src);
        Path destination = Paths.get(dest);
        if (Files.isRegularFile(source)) {
            copyFileIncludes(src, dest);
            return;
        }
        try (DirectoryStream<Path> files = Files.newDirectoryStream(source)) {
            if (Files.isDirectory(source)) {
                Files.createDirectories(destination);
            }
            for (Path path : files) {
                if (Files.isRegularFile(path)) {
                    destination = destination.resolve(path.getFileName());
                    Files.createFile(destination);
                    copyFileIncludes(path.toString(), destination.toString());
                } else {
                    destination = destination.resolve(path.getFileName());
                    Files.createDirectories(destination);
                    detourAllFoldersAndCopyFiles(path.toString(), destination.toString());
                }
                destination = destination.getParent();
            }
        } catch (IOException e) {
            System.out.println("Tried copy non-existent directory");
        }
    }

    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        detourAllFoldersAndCopyFiles(pathFrom, pathTo);
    }
}
