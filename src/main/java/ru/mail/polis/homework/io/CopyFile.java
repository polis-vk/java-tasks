package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

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
        try {
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Files.createDirectories(to.resolve(from.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path fileCopyFrom, BasicFileAttributes attrs)
                        throws IOException {
                    Path fileCopyTo = to.resolve(from.relativize(fileCopyFrom));
                    try {
                        Files.createFile(fileCopyTo);
                    } catch (IOException exception) {
                        Files.createDirectories(fileCopyTo.getParent());
                        Files.createFile(fileCopyTo);
                    }

                    copy(new FileInputStream(fileCopyFrom.toString()),
                            new FileOutputStream(fileCopyTo.toString()));
                    return FileVisitResult.CONTINUE;
                }
            });
        } catch (IOException exception) {
            System.out.println("Something bad happened");
        }
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead;
        do {
            bytesRead = inputStream.read(buffer);
            if (bytesRead > 0) {
                outputStream.write(buffer, 0, bytesRead);
            }
        } while (bytesRead > 0);
        outputStream.flush();
    }
}
