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

        if (!source.toFile().exists()) {
            return null;
        }

        if (source.toFile().isDirectory()) {
            if (!dest.toFile().mkdirs()) {
                return null;
            }
        }  else {
            if (!dest.getParent().toFile().mkdirs()) {
                return null;
            }
        }

        try {
            Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                    Objects.requireNonNull(dir);
                    Objects.requireNonNull(attrs);

                    final Path relativePath = source.relativize(dir);
                    dest.resolve(relativePath).toFile().mkdir();

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
        }

        return dest.toString();
    }

    private static void copyFile(final Path source, final Path dest, final Path relativePath) throws IOException {
        final File sourceFile = source.toFile();
        final File destinationFile = dest.resolve(relativePath).toFile();
        try (
                InputStream in = new FileInputStream(sourceFile);
                OutputStream out = new FileOutputStream(destinationFile)
        ) {
            byte[] buffer = new byte[1024];
            int length;
            while ((length = in.read(buffer)) > 0) {
                out.write(buffer, 0, length);
            }
        }
    }

}
