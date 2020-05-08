package ru.mail.polis.homework.io;

import java.io.*;

public class CopyFile {

    /**
     * Реализовать копирование папки из pathFrom в pathTo. Скопировать надо все внутренности
     * Файлы копировать ручками через стримы.
     * В тесте для создания нужных файлов для первого запуска надо расскоментировать код в setUp()
     * <p>
     * 6 баллов
     */
    public static String copyFiles(String pathFrom, String pathTo) {
        File fromDir = new File(pathFrom);
        File toDir = new File(pathTo);

        if (fromDir.isFile() && !toDir.getParentFile().exists()) {
            toDir.getParentFile().mkdirs();
        }

        try {
            copyFolder(fromDir, toDir);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static void copyFolder(File from, File to) throws IOException {
        if (!from.exists()) {
            return;
        }
        if (from.isDirectory()) {                                     //если источник - каталог
            if (!to.exists()) {                                           //если финальная деректория не существует
                to.mkdirs();                                                 //создаем ее
            }
            String[] files = from.list();                               //список всех айлов в дериктории источнике
            for (String file : files) {                                 //проходимся по списку
                File srcFile = new File(from, file);                        //откуда копируем
                File destFile = new File(to, file);                         //куда копируем
                copyFolder(srcFile, destFile);                               //уходим в рекурсию
            }
        } else {                                                    //если источник - файл
            InputStream in = new FileInputStream(from);                 //стрим вх открыть
            OutputStream out = new FileOutputStream(to);                //стрим вых открыть
            byte[] buffer = new byte[1024];                             //буфер содержимого
            int length;
            while ((length = in.read(buffer)) > 0) {                     //цикл
                out.write(buffer, 0, length);                           //запись
            }
            in.close();                                                 //стрим вх закрыть
            out.close();                                                //стрим вых закрыть
        }
    }
}
