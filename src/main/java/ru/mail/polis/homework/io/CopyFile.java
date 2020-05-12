package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path fromDir = Paths.get(pathFrom);
        Path toDir = Paths.get(pathTo);

        try {
            Files.createDirectories(toDir.getParent());
            copyFolder(fromDir, toDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFolder(Path from, Path to) throws IOException {
        if (Files.notExists(from)) {
            return;
        }
        if (Files.isDirectory(from)) {
            if (Files.notExists(to)) {
                Files.createDirectories(to);
            }
            Stream<Path> pathStream = Files.list(from);
            if (pathStream == null) {
                return;
            }
            pathStream.forEach(path -> {
                try {
                    copyFolder(path, to.resolve(path.getFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } else {
            try (InputStream in = new FileInputStream(String.valueOf(from));
                 OutputStream out = new FileOutputStream(String.valueOf(to))) {
                byte[] buffer = new byte[1024];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
            }
        }
    }
}
