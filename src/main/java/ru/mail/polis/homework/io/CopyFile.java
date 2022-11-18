package ru.mail.polis.homework.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.SimpleFileVisitor;
import java.util.Arrays;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        if (!checkPaths(pathFrom, pathTo)) {
            return;
        }

        Path pathSrc = Paths.get(pathFrom);
        if (Files.notExists(pathSrc)) {
            return;
        }

        Path pathDest = Paths.get(pathTo);

        try {
            Path theNearestDirectoryToDest = !Files.isDirectory(pathDest) ? pathDest.getParent() : pathDest;

            if (Files.notExists(theNearestDirectoryToDest)) {
                Files.createDirectories(theNearestDirectoryToDest);
            }

            copyDataFromFile(pathSrc, pathDest);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void copyDataFromFile(Path from, Path to) {
        StringBuilder stringBuilder = new StringBuilder();

        try (InputStream inputStreamReader = Files.newInputStream(from)) {
            while (inputStreamReader.available() > 0) {
                stringBuilder.append(inputStreamReader.readAllBytes());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (OutputStream outputStream = Files.newOutputStream(to)) {
            for (int i = 0; i < stringBuilder.length(); i++) {
                outputStream.write(stringBuilder.charAt(i));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static boolean checkPaths(String... paths) {
        for (int i = 0; i < paths.length; i++) {
            if (paths[i].isEmpty() || paths[i] == null) {
                return false;
            }
        }

        return true;
    }
}
