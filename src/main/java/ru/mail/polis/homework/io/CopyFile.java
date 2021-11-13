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
            Files.createDirectories(to.getParent());
            Files.walkFileTree(from, new SimpleFileVisitor<Path>() {
                @Override
                public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs)
                        throws IOException {
                    Files.createDirectory(to.resolve(from.relativize(dir)));
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path fileCopyFrom, BasicFileAttributes attrs)
                        throws IOException {
                    Path fileCopyTo = to.resolve(from.relativize(fileCopyFrom));

                    FileInputStream fileInputStream = new FileInputStream(fileCopyFrom.toString());
                    FileOutputStream fileOutputStream = new FileOutputStream(fileCopyTo.toString());
                    copy(fileInputStream, fileOutputStream);

                    fileInputStream.close();
                    fileOutputStream.close();
                    return FileVisitResult.CONTINUE;
                }
            });

        } catch (IOException exception) {
            System.out.println("Something bad happened");
        }
    }

    private static void copy(InputStream inputStream, OutputStream outputStream) throws IOException {
        byte[] buffer = new byte[1024];
        int bytesRead = inputStream.read(buffer);

        while (bytesRead > 0) {
            outputStream.write(buffer, 0, bytesRead);
            bytesRead = inputStream.read(buffer);
        }

        outputStream.flush();
    }
}
