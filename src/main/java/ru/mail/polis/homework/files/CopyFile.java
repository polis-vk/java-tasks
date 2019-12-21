package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class CopyFile {

    private static void copy(String from, String to) throws IOException {
        File originalFile = new File(from);
        File resultFile = new File(to);

        if (!resultFile.exists() && !resultFile.createNewFile()) {
            throw new IOException("Cannot create file!");
        }

        try (InputStream in = new BufferedInputStream(new FileInputStream(originalFile));
             OutputStream out = new BufferedOutputStream(new FileOutputStream(resultFile))) {

            byte[] buffer = new byte[1024];
            int lengthRead;
            while ((lengthRead = in.read(buffer)) > 0) {
                out.write(buffer, 0, lengthRead);
                out.flush();
            }
        }
    }

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) throws IOException {
        File dest = new File(pathTo);
        File from = new File(pathFrom);
        if (!dest.exists() && !dest.canWrite() && !dest.mkdirs()) {
            throw new IOException("Cannot create directory!");
        }

        try (Stream<Path> files = Files.walk(Paths.get(pathFrom))) {
            files.filter(file -> file.toFile().isFile())
                    .forEach(original -> {
                        try {
                            copy(
                                    original.toAbsolutePath().toString(),
                                    pathTo + File.separator + original.getFileName()
                            );
                        } catch (IOException ioex) {
                            ioex.printStackTrace();
                        }
                    });
        }
        return null;
    }
}
