package ru.mail.polis.homework.io;

import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorWithDelete extends SimpleFileVisitor<Path> {
    private int countOfDeletedFilesAndDirectories = 0;

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        countOfDeletedFilesAndDirectories++;
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException exc) throws IOException {
        countOfDeletedFilesAndDirectories++;
        Files.delete(path);
        return FileVisitResult.CONTINUE;
    }

    public int getCountOfDeletedFilesAndDirectories() {
        return countOfDeletedFilesAndDirectories;
    }
}
