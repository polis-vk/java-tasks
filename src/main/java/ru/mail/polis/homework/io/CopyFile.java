package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * 3 балла
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        if (pathFrom == null || pathTo == null) {
            return "Null value of some path";
        }

        Path from = Paths.get(pathFrom);

        if (!Files.exists(from)) {
            return "No directory on this way";
        }

        Path to = Paths.get(pathTo);

        try {
            if (Files.isDirectory(to)) {
                createDirsByPath(to);
            } else {
                createDirsByPath(to.getParent());
            }
        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred while creating dirs";
        }

        try {
            Files.walkFileTree(from, new SimpleFileVisitor<>() {

                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) {
                    Path pathToDir = getPathToFile(from, to, dir);
                    try {
                        Files.createDirectories(pathToDir);
                    } catch (IOException e) {
                        e.printStackTrace();
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                    Path pathToFile = getPathToFile(from, to, file);
                    try (InputStream in = Files.newInputStream(file);
                         OutputStream out = Files.newOutputStream(pathToFile)) {
                        out.write(in.readAllBytes());
                    } catch (IOException e) {
                        e.printStackTrace();
                        return FileVisitResult.TERMINATE;
                    }
                    return FileVisitResult.CONTINUE;
                }


            });
        } catch (IOException e) {
            e.printStackTrace();
            return "Some exception occurred while walking file tree";
        }
        return "Succeed";
    }

    private static void createDirsByPath(Path dirs) throws IOException {
        if (!Files.exists(dirs)) {
            Files.createDirectories(dirs);
        }
    }

    private static Path getPathToFile(Path from, Path to, Path with) {
        return to.resolve(from.relativize(with));
    }
}
