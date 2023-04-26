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
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path from = Paths.get(pathFrom);
        Path to = Paths.get(pathTo);

        if (!Files.exists(from)) {
            return;
        }
        if (!Files.isDirectory(from)) {
            Files.createDirectories(to.getParent());
        }

        FileVisitorWithCopy fileVisitor = new FileVisitorWithCopy(to);
        Files.walkFileTree(from, fileVisitor);
    }


}
