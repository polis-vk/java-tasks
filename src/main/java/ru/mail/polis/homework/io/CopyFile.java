package ru.mail.polis.homework.io;

import java.io.File;
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
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return;
        }
        if (Files.isRegularFile(from)) {
            if (Files.isDirectory(to)) {
                return;
            }
            try {
                Files.createDirectories(to.getParent());
                copyFile(from, to);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
            if (!Files.exists(to)) {
                Files.createDirectories(to);
            }
            for (Path path : directoryStream) {
                copyFiles(pathFrom + File.separator + path.getFileName(), pathTo + File.separator + path.getFileName());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static long copyFile(Path fromFile, Path toFile) throws IOException {
        long total = 0;
        try (InputStream in = Files.newInputStream(fromFile); OutputStream out = Files.newOutputStream(toFile)) {
            byte[] buffer = new byte[1024];
            int blockSize = 0;
            do {
                blockSize = in.read(buffer);
                out.write(buffer, 0, blockSize == -1 ? 0 : blockSize);
                total += blockSize;
            } while (blockSize > 0);
            return total;
        }
    }

}
