package ru.mail.polis.homework.files;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */

    public static String copySmallFiles(String pathFrom, String pathTo) {
        Path main = Paths.get(pathFrom);
        main.normalize();

        Path copy = Paths.get(pathTo);
        copy.normalize();

        if (Files.notExists(main)) {
            return null;
        }

        if (Files.isDirectory(main)) {
            if (!Files.isWritable(main)) {
                return null;
            }
        } else {
            //Блин, а как это переписать по нормальному?
            if (!copy.getParent().toFile().mkdirs()) {
                return null;
            }
        }
        try {
            Files.walkFileTree(main, new FVisitor(main, copy));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return copy.toString();
    }

    static class FVisitor extends SimpleFileVisitor<Path> {
        Path main;
        Path copy;

        public FVisitor(Path main, Path copy) {
            this.main = main;
            this.copy = copy;
        }

        @Override
        public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
            Path relativePath = main.relativize(path);
            copyFile(path, copy, relativePath);

            return FileVisitResult.CONTINUE;
        }

        @Override
        public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
            Path relativePath = main.relativize(path);
            Files.createDirectory(copy.resolve(relativePath));

            return FileVisitResult.CONTINUE;
        }

        private static void copyFile(Path main, Path copy, Path relativePath) throws IOException {
            //кстати, а зачем нам обрачивать в 2 try?
            try (InputStream in = new FileInputStream(main.toFile())) {
                try (OutputStream out = new FileOutputStream(copy.resolve(relativePath).toFile())) {

                    byte[] buffer = new byte[1024];
                    int length;
                    while ((length = in.read(buffer)) > 0) {
                        out.write(buffer, 0, length);
                    }
                }
            }
        }
    }
}