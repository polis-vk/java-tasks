package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

public class FileVisitorWithCopy extends SimpleFileVisitor<Path> {
    private Path copyTo;
    public FileVisitorWithCopy(Path copyTo) {
        this.copyTo = copyTo;
    }

    @Override
    public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
        Path newFile;
        if (Files.isDirectory(copyTo)) {
            newFile = Paths.get(copyTo.toString(), path.getFileName().toString());
        } else {
            newFile = copyTo;
        }

        InputStream input = Files.newInputStream(path);
        OutputStream output = Files.newOutputStream(newFile);
        output.write(input.readAllBytes());

        close(input, output);

        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult preVisitDirectory(Path path, BasicFileAttributes basicFileAttributes) throws IOException {
        if (!path.getFileName().toString().equals(copyTo.getFileName().toString())) {
            Path newDir = Paths.get(copyTo.toString(), path.getFileName().toString());
            copyTo = Files.createDirectories(newDir);
        } else {
            Files.createDirectories(copyTo);
        }
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path path, IOException exc) {
        copyTo = copyTo.getParent();
        return FileVisitResult.CONTINUE;
    }

    private void close (InputStream inputStream, OutputStream outputStream) throws IOException {
        inputStream.close();
        outputStream.close();
    }
}
