package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CustomFileVisitor extends SimpleFileVisitor<Path> {
    private final Path from;
    private final Path to;
    private final int BUFFER_SIZE = 1024;

    public CustomFileVisitor(Path from, Path to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        dir = unionPaths(dir);
        if (Files.notExists(dir)) {
            Files.createDirectories(dir);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        copyFile(file, unionPaths(file));
        return FileVisitResult.CONTINUE;
    }

    private void copyFile(Path from, Path to) {
        try (FileInputStream fileInputStream = new FileInputStream(from.toFile())) {
            try (FileOutputStream fileOutputStream = new FileOutputStream(to.toFile())) {
                byte[] buffer = new byte[BUFFER_SIZE];
                while (fileInputStream.available() > 0) {
                    if (fileInputStream.read(buffer) > 0) {
                        fileOutputStream.write(buffer);
                    }
                }
                fileOutputStream.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private Path unionPaths(Path dir) {
        return to.resolve(from.relativize(dir));
    }
}
