package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 4 баллов
     */

    public static String copyFiles(String pathFrom, String pathTo) {
        Path fromPath = Paths.get(pathFrom);
        Path toPath = Paths.get(pathTo);
        if (Files.notExists(fromPath)) {
            return null;
        }

        try (Stream<Path> filesFromPathStream = Files.walk(fromPath)) {
            Files.createDirectories(toPath.getParent());
            Iterator<Path> pathIterator = filesFromPathStream.iterator();
            while (pathIterator.hasNext()) {
                Path currentPathFrom = pathIterator.next();
                if (Files.isDirectory(currentPathFrom)) {
                    Files.createDirectory(toPath.resolve(fromPath.relativize(currentPathFrom)));
                } else if (Files.isRegularFile(currentPathFrom)) {
                    copyWithStream(currentPathFrom, toPath.resolve((fromPath.relativize(currentPathFrom))));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
        return toPath.toString();
    }

    private static void copyWithStream(Path pathFrom, Path pathTo) throws IOException {
        Files.createFile(pathTo);
        try (OutputStream out = new BufferedOutputStream(Files.newOutputStream(pathTo))) {
            try (InputStream in = new BufferedInputStream(Files.newInputStream(pathFrom))) {
                byte[] buffer = new byte[1024];
                int length = in.read(buffer);
                while (length != -1) {
                    out.write(buffer, 0, length);
                    length = in.read(buffer);
                }
            }
        }
    }
}
