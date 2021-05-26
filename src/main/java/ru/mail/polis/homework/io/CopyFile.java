package ru.mail.polis.homework.io;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     *
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        Path to = Paths.get(pathTo);
        Path from = Paths.get(pathFrom);

        if(!Files.exists(from))
        {
            return "No files were found";
        }

        if(Files.isRegularFile(from)) {
            try {
                if (Files.notExists(to.getParent())) {
                    Files.createDirectories(to.getParent());
                }
                copyFile(from, to);
            } catch (IOException e) {
                e.printStackTrace();
                return "Couldn't copy a file";
            }
            return "File was copied";
        }

        if(!Files.exists(to))
        {
            try
            {
                Files.createDirectories(to);
            } catch (IOException e) {
                e.printStackTrace();
                return "Couldn't create non-existent directory";
            }
        }

        try (DirectoryStream<Path> subPaths = Files.newDirectoryStream(from))
        {
            subPaths.forEach(i -> copyFiles(i.toString(), pathTo + File.separator + i.getFileName()));
        } catch (IOException e) {
            e.printStackTrace();
            return "Couldn't copy a file";
        }
        return "Directory was copied";
    }

    private static void copyFile(Path from, Path to) throws IOException {
        Files.createFile(to);

        try (InputStream inputStream = Files.newInputStream(from)) {
            try (OutputStream outputStream = Files.newOutputStream(to)) {
                byte[] buffer = new byte[1024];
                int lengthRead;
                while ((lengthRead = inputStream.read(buffer)) > 0) {
                    outputStream.write(buffer, 0, lengthRead);
                }
            }
        }
    }

}
