package ru.mail.polis.homework.files;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     */
    public static String copySmallFiles(String pathFrom, String pathTo) {
        final File sourcePath = new File(pathFrom);
        final File destinationPath = new File(pathTo);

        copySmallFiles(sourcePath, destinationPath);

        return pathTo;
    }

    private static void copySmallFiles(File sourcePath, File destinationPath) {
        if (sourcePath.isDirectory()) {

            if (!destinationPath.exists()) {
                destinationPath.mkdirs();
            }

            for (File subFile: sourcePath.listFiles()) {
//                copySmallFiles(subFile, destinationPath);
                String childPath = destinationPath.getAbsolutePath() + File.separator + subFile.getName();
                File childTo = new File(childPath);

                if (subFile.isDirectory()) {
                    copySmallFiles(subFile, childTo);
                }
                else {
                    try {
                        copyFile(subFile, childTo);
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }

        }
        else {
            if (sourcePath.isFile()) {
                    if (!destinationPath.exists()) {
                        File parent = new File(destinationPath.getParent());
                        parent.mkdirs();
                        try {
                            destinationPath.createNewFile();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                try {
                    copyFile(sourcePath, destinationPath);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void copyFile(File sourcePath, File destinationPath) throws IOException {
        try (InputStream in = new BufferedInputStream(new FileInputStream(sourcePath))) {
            try (OutputStream out = new BufferedOutputStream(new FileOutputStream(destinationPath))) {
                byte[] buffer = new byte[1024];
                int readLength;
                readLength = in.read(buffer);

                while (readLength > 0) {
                    out.write(buffer, 0, readLength);
                    readLength = in.read(buffer);
                }

            }
        }
    }

}
