package ru.mail.polis.homework.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path from = Paths.get(pathFrom);
        if (Files.notExists(from)) {
            return;
        }

        Path to = Paths.get(pathTo);
        Path dirToCopy = Files.isDirectory(to) ? to : to.getParent();
        if (Files.notExists(dirToCopy)) {
            try {
                Files.createDirectories(dirToCopy);
                Files.walkFileTree(from, new CopyFileVisitor(from, to));
            } catch (IOException exception) {
                exception.printStackTrace();
            }
        }
    }
}
