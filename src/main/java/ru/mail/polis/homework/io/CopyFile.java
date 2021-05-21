package ru.mail.polis.homework.io;


import java.io.*;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;


public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы. Исползуем новый API
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 4 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) throws IOException {
        Path to = Paths.get(pathTo);
        Path from = Paths.get(pathFrom);
        boolean isDirectory = Files.isDirectory(from);
        createAllNewDirectory(to, isDirectory);
        try {
            if (Files.isDirectory(from)) {
                if (Files.list(from).findAny().isPresent()) {
                    DirectoryStream<Path> stream = Files.newDirectoryStream(from);
                    stream.forEach(x -> {
                        try {
                            Path newTo = to.resolve(x.getFileName());
                            if (Files.isDirectory(x)) {
                                copyFiles(x.toString(), newTo.toString());
                            }
                            copy(x, newTo);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
                    stream.close();
                }
            } else {
                copy(from, to);
            }
        } catch (NoSuchFileException e) {
            e.printStackTrace();
        }
        return pathTo;
    }


    private static void createAllNewDirectory(Path pathTo, boolean isDirectory) throws IOException {
        List<Path> toCreate = new ArrayList<>();
        if (isDirectory) {
            toCreate.add(pathTo);
        }
        Path to = pathTo.getParent();
        while (to.getNameCount() != 0) {
            if (Files.exists(to)) {
                break;
            }
            toCreate.add(to);
            to = to.getParent();
        }
        for (int i = toCreate.size() - 1; i >= 0; i--) {
            Files.createDirectory(toCreate.get(i));
        }
    }

    private static void copy(Path from, Path to) throws IOException {
        InputStream is = null;
        OutputStream os = null;
        try {
            is = new FileInputStream(from.toString());
            os = new FileOutputStream(to.toString());
            byte[] buffer = new byte[1024];
            int length;
            while ((length = is.read(buffer)) > 0) {
                os.write(buffer, 0, length);
            }
        } finally {
            is.close();
            os.close();
        }
    }
}
