package ru.mail.polis.homework.io;

import java.io.EOFException;
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        CopyFilesRecursively(from, to);
        return null;
    }

    private static void CopyFilesRecursively(Path from, Path to) {
        if (!Files.isDirectory(from)) {
            CopyOneFile(from, to);
            return;
        }
        try (DirectoryStream<Path> folder = Files.newDirectoryStream(from)) {
            Files.createDirectories(to);
            for (Path path : folder) {
                CopyFilesRecursively(path, to.resolve(path.getFileName()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void CopyOneFile(Path from, Path to) {
        try (FileInputStream in = new FileInputStream(String.valueOf(from));
             FileOutputStream out = new FileOutputStream(String.valueOf(to))) {
            byte[] buffer = new byte[(int) Files.size(from)];
            if (in.read(buffer) == -1) {
                throw new EOFException();
            }
            out.write(buffer);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
