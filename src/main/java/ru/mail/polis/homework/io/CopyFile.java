package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;

public class CopyFile {

    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        if (isIllegalDir(pathFrom) || pathTo == null)
            return "Illegal path";

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (Files.isRegularFile(from))
            return copyFile(from, to);

        if (!Files.exists(to))
            Files.createDirectories(to);

        try (DirectoryStream<Path> directoryStream = Files.newDirectoryStream(from)) {
            for (Path file: directoryStream) {
                copyFiles(file.toString(), to.resolve(file.getFileName()).toString());
            }
        }

        return null;
    }

    private static boolean isIllegalDir(String dirPath) {
        return dirPath == null || !Files.exists(Paths.get(dirPath));
    }

    private static String copyFile(Path from, Path to) throws IOException {
        if (!Files.exists(to.getParent())) {
            Files.createDirectories(to.getParent());
        }

        InputStream fin = Files.newInputStream(from);
        byte[] bytes = new byte[fin.available()];
        fin.read(bytes);
        Files.newOutputStream(to).write(bytes);
        return null;
    }

}
