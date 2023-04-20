package ru.mail.polis.homework.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) throws IOException {
        Path source = Paths.get(pathFrom);
        Path target = Paths.get(pathTo);
        if (!Files.exists(source)) {
            return;
        }
        if (!Files.exists(target)) {
            Files.createDirectories(target);
        }
        Files.walk(source)
                .forEach(path -> {
                    try {
                        Path targetPath = target.resolve(source.relativize(path));
                        if (Files.isDirectory(path)) {
                            Files.createDirectories(targetPath);
                        } else {
                            Files.copy(path, targetPath, StandardCopyOption.REPLACE_EXISTING);
                        }
                    } catch (IOException e) {
                        System.err.println("Ошибка при копировании файла или директории: " + e);
                    }
                });
    }

}
