package ru.mail.polis.homework.files;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Comparator;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Stream;

@SuppressWarnings({"ConstantConditions", "ResultOfMethodCallIgnored"})
public class Directories {

    // removed the text because file encoding in broken here for some reason
    public static int removeWithFile(String path) {
        return removeWithFile(new File(path));
    }

    private static int removeWithFile(File dir) {
        int count = 0;

        if (dir.exists()) {
            if (dir.isDirectory())
                for (final File subFile : dir.listFiles())
                    count += removeWithFile(subFile.getAbsolutePath());

            dir.delete();
            count++;
        }

        return count;
    }

    public static int removeWithPath(String path) {
        AtomicInteger count = new AtomicInteger(0);

        try (Stream<Path> paths = Files.walk(Paths.get(path))) {
            paths.sorted(Comparator.reverseOrder())
                    .forEach(_path -> {
                        _path.toFile().delete();
                        count.getAndIncrement();
                    });
        } catch (IOException e) {
            if (count.get() != 0)
                e.printStackTrace();
        }

        return count.get();
    }
}
