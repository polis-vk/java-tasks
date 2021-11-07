package ru.mail.polis.homework.io;

import ru.mail.polis.homework.io.blocking.StructureInputStream;
import ru.mail.polis.homework.io.blocking.StructureOutputStream;

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
    public static String copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);
        if (!Files.exists(from)) {
            return "Wrong path";
        }

        try {
            if (Files.isRegularFile(from)) {
                Files.createDirectories(to.getParent());
                try (FileInputStream structureInputStream = new StructureInputStream(from.toFile())) {
                    try (FileOutputStream structureOutputStream = new StructureOutputStream(to.toFile())) {
                        structureOutputStream.write(structureInputStream.read());
                    }
                }
                return "Done";
            } else {
                Files.createDirectories(to);
            }
            Files.walkFileTree(from, new SimpleFileVisitor<>() {
                @Override
                public FileVisitResult preVisitDirectory(Path directory, BasicFileAttributes attributes) throws IOException {
                    Path targetDirectory = to.resolve(from.relativize(directory));
                    System.out.println("Copying dir: " + from.relativize(directory));
                    if (!Files.exists(targetDirectory)) {
                        Files.createDirectory(targetDirectory);
                    }
                    return FileVisitResult.CONTINUE;
                }

                @Override
                public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                    System.out.println("Copying file: " + from.relativize(file));
                    try (FileInputStream structureInputStream = new StructureInputStream(file.toFile())) {
                        try (FileOutputStream structureOutputStream = new StructureOutputStream(to.resolve(from.relativize(file)).toFile())) {
                            structureOutputStream.write(structureInputStream.read());
                        }
                    }
                    return FileVisitResult.CONTINUE;
                }

            });
        } catch (IOException e) {
            e.printStackTrace();
        }

        return "Done";
    }

}
