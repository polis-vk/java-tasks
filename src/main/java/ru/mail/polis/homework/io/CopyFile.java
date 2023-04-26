package ru.mail.polis.homework.io;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.stream.Stream;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Используем новый API
     * В тесте для создания нужных файлов для первого запуска надо раскомментировать код в setUp()
     * 3 тугрика
     */
    public static void copyFiles(String pathFrom, String pathTo) {
        Path dest = Paths.get(pathTo);
        Path source = Paths.get(pathFrom);

        if (Files.notExists(source)) {
            return;
        }
        try {
            if (Files.isDirectory(source)) {
                Files.createDirectories(dest);
                try (Stream<Path> contentInStream = Files.list(source)) {
                    contentInStream.forEach(path -> {
                        copyFiles(path.toString(), dest.resolve(source.relativize(path)).toString()); //копирование файлов из источника в целевую папку по составному пути
                    });
                }
            } else {
                Files.createDirectories(dest.getParent());
                copy(source, dest);
            }
        } catch (IOException e) {
            System.out.println("IOException");
        }
    }

    private static void copy(Path source, Path dest) throws IOException {
        try (BufferedReader br = Files.newBufferedReader(source)) {
            try (BufferedWriter bw = Files.newBufferedWriter(dest)) {
                br.lines().forEach(str -> {
                    try {
                        bw.write(str);
                    } catch (IOException e) {
                        throw new RuntimeException(e);  // прокидывать выше IOExeption не дает стрим(
                    }
                });
            }
        }
    }
}
