package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Objects;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        final Path source = Paths.get(pathFrom);
        source.normalize();

        final Path dest = Paths.get(pathTo);
        dest.normalize();

        if (!Files.exists(source)) {
            return null;
        }

        try {
            if (Files.isDirectory(source)) {
                Files.createDirectories(dest);
            }  else {
                Files.createDirectories(dest.getParent());
            }

            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Objects.requireNonNull(dir);
                    Objects.requireNonNull(attrs);

                    final Path relativePath = source.relativize(dir);
                    Files.createDirectory(dest.resolve(relativePath));

                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    Objects.requireNonNull(file);
                    Objects.requireNonNull(attrs);

                    final Path relativePath = source.relativize(file);
                    copyFile(file, dest, relativePath);

                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }

        return dest.toString();
    }

    private static void copyFile(final Path source, final Path dest, final Path relativePath) throws IOException {
        try (BufferedReader in = Files.newBufferedReader(source)) {
            try (BufferedWriter out = Files.newBufferedWriter(dest.resolve(relativePath))) {
                String next;
                while ((next = in.readLine()) != null) {
                    out.write(next);
                }
            }
        }
    }

}
