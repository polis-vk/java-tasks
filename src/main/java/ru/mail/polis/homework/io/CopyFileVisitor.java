package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFileVisitor extends SimpleFileVisitor<Path> {
    private final Path from;
    private final Path to;

    public CopyFileVisitor(Path from, Path to) {
        this.from = from;
        this.to = to;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Path dirToCopy = to.resolve(from.relativize(dir));
        if (Files.notExists(dirToCopy)) {
            Files.createDirectories(dirToCopy);
        }

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        copyFile(file, to.resolve(from.relativize(file)));
        return FileVisitResult.CONTINUE;
    }

    private void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (FileChannel source = new FileInputStream(pathFrom.toFile()).getChannel();
             FileChannel destination = new FileOutputStream(pathTo.toFile()).getChannel()) {
            destination.transferFrom(source, 0, source.size());
        }
    }
}
