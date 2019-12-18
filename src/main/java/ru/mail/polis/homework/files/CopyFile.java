package ru.mail.polis.homework.files;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

@SuppressWarnings({"ConstantConditions", "UnusedReturnValue", "ResultOfMethodCallIgnored"})
public class CopyFile {

    public static String copySmallFiles(String pathFrom, String pathTo) {
        final File fileFrom = new File(pathFrom);
        final File fileTo = new File(pathTo);

        copySmallFiles(fileFrom, fileTo);

        return pathTo;
    }

    private static void copySmallFiles(File fileFrom, File fileTo) {
        if (fileFrom.exists())
            if (fileFrom.isDirectory()) {
                if (!fileTo.exists())
                    fileTo.mkdirs();
                else if (fileTo.isFile())
                    return;

                for (final File subFileFrom : fileFrom.listFiles()) {
                    final String subFileToPath = fileTo.getAbsolutePath() + File.separator + subFileFrom.getName();

                    final File subFileTo = new File(subFileToPath);

                    if (subFileFrom.isDirectory())
                        copySmallFiles(subFileFrom, subFileTo);
                    else
                        copyFile(subFileFrom, subFileTo);
                }

            } else if (fileFrom.isFile()) {
                final File parent = new File(fileTo.getParent());
                parent.mkdirs();

                try {
                    fileTo.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }

                copyFile(fileFrom, fileTo);
            }
    }

    private static void copyFile(File fileFrom, File fileTo) {
        try (FileInputStream in = new FileInputStream(fileFrom)) {
            try (FileOutputStream out = new FileOutputStream(fileTo)) {
                final byte[] buffer = new byte[4096];
                int length;

                while ((length = in.read(buffer)) > 0)
                    out.write(buffer, 0, length);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
