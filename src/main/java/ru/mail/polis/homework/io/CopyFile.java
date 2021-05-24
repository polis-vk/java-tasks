package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return "File doesn't exists";
        }
        if (Files.isRegularFile(from)) {
            copyFile(from, to);
            return "Copy file";
        }
        if (!Files.exists(to)) {
            try {
                Files.createDirectories(to);
            } catch (IOException e) {
                e.printStackTrace();
                return "Error";
            }
        }
        try (DirectoryStream<Path> paths = Files.newDirectoryStream(from)) {
            paths.forEach(path -> copyFiles(path.toString(), to.resolve(path.getFileName()).toString()));
        } catch (IOException e) {
            e.printStackTrace();
            return "Error";
        }
        return "Copy directories";
    }

    public static void copyFile(Path from, Path to) {
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(from))) {
            if (!Files.exists(to.getParent())) {
                Files.createDirectories(to.getParent());
            }
            if (!Files.exists(to)) {
                Files.createFile(to);
            }
            try (BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(to))) {
                byte[] buf = new byte[1024];
                int length;
                while ((length = in.read(buf)) > 0) {
                    out.write(buf, 0, length);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
