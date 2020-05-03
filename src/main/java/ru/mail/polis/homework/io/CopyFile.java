package ru.mail.polis.homework.io;

import java.io.*;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;

public class CopyFile {
    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFromString, String pathToString) throws IOException {
        final Path source = Paths.get(pathFromString);
        final Path dest = Paths.get(pathToString);
    
        if (Files.notExists(source)) {
            return pathToString;
        }
        Files.createDirectories(dest.getParent());
        Files.walkFileTree(source, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
                Files.copy(dir, dest.resolve(source.relativize(dir)), StandardCopyOption.REPLACE_EXISTING);
                return FileVisitResult.CONTINUE;
            }
        
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) {
                copyFileData(file.toString(), dest.resolve(source.relativize(file)).toString());
                return FileVisitResult.CONTINUE;
            }
        });
        return pathToString;
    }
    
    private static void copyFileData(String fileFrom, String fileTo) {
        try (InputStream in = new FileInputStream(fileFrom);
             OutputStream out = new FileOutputStream(fileTo)) {
            byte[] bytes = new byte[4096];
            while (in.read(bytes) != -1) {
                out.write(bytes);
            }
            out.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
