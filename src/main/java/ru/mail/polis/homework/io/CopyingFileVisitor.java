package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyingFileVisitor extends SimpleFileVisitor<Path> {
    private static final int BUFFER_SIZE = 4096;
    private final Path from;
    private final Path to;

    private static void copy(Path from, Path to) throws IOException {
        try (InputStream in = new FileInputStream(from.toString());
             OutputStream out = new FileOutputStream(to.toString())) {
            byte[] buffer = new byte[BUFFER_SIZE];
            int readBytes = 0;
            while ((readBytes = in.read(buffer)) > 0) {
                out.write(buffer, 0, readBytes);
            }
        }
    }

    public CopyingFileVisitor(Path from, Path to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Path fileToCreate = to.resolve(from.relativize(file));
        if (!Files.exists(fileToCreate)) {
            Files.createFile(fileToCreate);
        }
        copy(file, fileToCreate);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path dirToCreate = to.resolve(from.relativize(dir));
        if (!Files.exists(dirToCreate)) {
            Files.createDirectory(dirToCreate);
        }
        return FileVisitResult.CONTINUE;
    }
}
