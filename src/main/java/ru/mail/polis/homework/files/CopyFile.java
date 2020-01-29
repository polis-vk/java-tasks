package ru.mail.polis.homework.files;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        if (pathFrom.equals(pathTo)) {
            return null;
        }

        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        try {
            return copySmallFilesWithPaths(from, to);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static String copySmallFilesWithPaths(final Path pathFrom, final Path pathTo) throws IOException {
        if (Files.exists(pathFrom) && Files.isReadable(pathFrom)) {
            if (Files.isRegularFile(pathFrom)) {
                if (!Files.exists(pathTo.getParent())) {
                    Files.createDirectories(pathTo.getParent());
                }
                copyFile(pathFrom, pathTo);
                return pathTo.toString();
            } else if (Files.isDirectory(pathFrom)) {
                if (!Files.exists(pathTo)) {
                    Files.createDirectories(pathTo);
                }
                Files.list(pathFrom).forEach(oldPath -> {
                    Path newPath = Paths.get(pathTo.toString(), oldPath.getFileName().toString());
                    if (Files.isDirectory(oldPath)) {
                        try {
                            copySmallFilesWithPaths(oldPath, newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    } else if (Files.isRegularFile(oldPath)) {
                        try {
                            copyFile(oldPath, newPath);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                });
                return pathTo.toString();
            }
        }

        return null;
    }

    private static void copyFile(final Path from, final Path to) throws IOException {
        try (BufferedReader reader = Files.newBufferedReader(from)) {
            try (BufferedWriter writer = Files.newBufferedWriter(to)) {
                reader.lines().forEach(line -> {
                    try {
                        writer.write(line + "\n");
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
