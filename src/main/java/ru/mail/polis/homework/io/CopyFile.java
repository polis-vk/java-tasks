package ru.mail.polis.homework.io;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
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
            return "File doesn't exists";
        }
        try {
            if (Files.isRegularFile(from)) {
                if (!Files.exists(to.getParent())) {
                    Files.createDirectories(to.getParent());
                }
                copyFile(from, to);
                return "Copy file";
            }
            if (!Files.exists(to))
            {
                if (!Files.exists(to.getParent()))
                {
                    Files.createDirectories(to);
                }
                else
                {
                    Files.createDirectory(to);
                }
            }

            try (DirectoryStream<Path> paths = Files.newDirectoryStream(from)) {
                paths.forEach(path -> copyFiles(path.toString(), to.resolve(path.getFileName()).toString()));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Copy directories";
    }

    public static void copyFile(Path from, Path to) throws IOException {
        try (BufferedInputStream in = new BufferedInputStream(Files.newInputStream(from));
             BufferedOutputStream out = new BufferedOutputStream(Files.newOutputStream(to))) {
            byte[] buf = new byte[1024];
            int length;
            while ((length = in.read(buf)) > 0) {
                out.write(buf, 0, length);
            }
        }
    }
}
