package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
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

        Path fileFrom = Paths.get(pathFrom);
        Path fileTo = Paths.get(pathTo);

        try {
            Files.createDirectories(fileTo.getParent());
            copyOneFile(fileFrom, fileTo);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return null;
    }

    private static void copyOneFile(Path fileFrom, Path fileTo) throws IOException {
        if (Files.notExists(fileFrom)) {
            return;
        }
        if (Files.isDirectory(fileFrom)) {
            if (Files.notExists(fileTo)) {
                Files.createDirectories(fileTo);
            }
            Stream<Path> pathStream = Files.list(fileFrom);
            if (pathStream == null) {
                return;
            }
            pathStream.forEach(path -> {
                try {
                    copyOneFile(path, fileTo.resolve(path.getFileName()));
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });

        } else {

            try (InputStream streamIn = new FileInputStream(String.valueOf(fileFrom));
                 OutputStream streamOut = new FileOutputStream(String.valueOf(fileTo))) {

                byte[] byteBuffer = new byte[1024];
                int length = 0;
                while ((length = streamIn.read(byteBuffer)) > 0) {
                    streamOut.write(byteBuffer, 0, length);
                }
            }
        }
    }
}
