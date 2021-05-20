package ru.mail.polis.homework.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

import static java.nio.file.StandardCopyOption.REPLACE_EXISTING;

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
        try {
            Files.createDirectories(toPath.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }
        try (Stream<Path> stream = Files.walk(fromPath)) {
            stream.forEachOrdered(path -> {
                try {
                    Files.copy(path, toPath.resolve(fromPath.relativize(path)), REPLACE_EXISTING);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return toPath.toString();
    }
}
