package ru.mail.polis.homework.io;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
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
        Path dirToCopy = getDirectoryToCopy(dir);
        if (Files.notExists(dirToCopy)) {
            Files.createDirectories(dirToCopy);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        copyFile(file, getDirectoryToCopy(file));
        return FileVisitResult.CONTINUE;
    }

    private Path getDirectoryToCopy(Path directory) {
        return to.resolve(from.relativize(directory));
    }

    private void copyFile(Path pathFrom, Path pathTo) throws IOException {
        try (FileInputStream sourceStream = new FileInputStream(pathFrom.toFile());
             FileOutputStream destinationStream = new FileOutputStream(pathTo.toFile());
             FileChannel source = sourceStream.getChannel();
             FileChannel destination = destinationStream.getChannel()) {
            destination.transferFrom(source, 0, source.size());
        }
    }
}
