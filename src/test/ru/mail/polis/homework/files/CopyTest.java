package ru.mail.polis.homework.files;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.Arrays;
import java.util.List;

import static java.nio.file.Files.createFile;
import static org.junit.Assert.assertEquals;

public class CopyTest {





    @Test
    public void hard() throws IOException {
        // создается директория third-опия fifth
        Path dir = Paths.get("src", "test", "resources", "directories", "fifth");
        Files.createDirectories(dir);
        for (int i = 0; i < 10; i++) {
            createFile(Paths.get("src", "test", "resources", "directories", "fifth", "file" + i + ".txt"));
        }

        Path dir1 = Paths.get("src", "test", "resources", "directories", "fifth", "dir1");
        Files.createDirectories(dir1);
        createFile(Paths.get("src", "test", "resources", "directories", "fifth", "dir1", "file1.txt"));

        Path dir2 = Paths.get("src", "test", "resources", "directories", "fifth", "dir2");
        Files.createDirectories(dir2);
        Path dir3 = Paths.get("src", "test", "resources", "directories", "fifth", "dir1", "dir1dir1");
        Files.createDirectories(dir3);
        for (int i = 0; i < 3; i++) {
            createFile(Paths.get("src", "test", "resources", "directories", "fifth", "dir1", "dir1dir1",
                    "file" + i + ".txt"));
        }

        Path dir4 = Paths.get("src", "test", "resources", "directories", "fifth", "dir1", "dir1dir2");
        Files.createDirectories(dir4);
        Path dir5 = Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir1");
        Files.createDirectories(dir5);
        createFile(Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir1",
                "file1.txt"));

        Path dir6 = Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir2");
        Files.createDirectories(dir6);
        for (int i = 0; i < 5; i++) {
            createFile(Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir2",
                    "file" + i + ".txt"));
        }

        Path dir7 = Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir1", "dirdir");
        Files.createDirectories(dir7);
        for (int i = 0; i < 7; i++) {
            createFile(Paths.get("src", "test", "resources", "directories", "fifth", "dir2", "dir2dir1", "dirdir",
                    "file" + i + ".txt"));
        }
        Path dirTo = Paths.get("src", "test", "resources", "directories", "third");
        CopyFile.copySmallFiles(dir.toString(), dirTo.toString());

    }

    private void createFile(Path path) throws IOException {
        List<String> strings = Arrays.asList("Жили-были три китайца: Як, Як-цедрак, Як-цедрак-цедрак-цедрони.",
                "Жили-были три китайки: Цыпа, Цыпа-дрыпа, Цыпа-дрыпа-дрымпампони.",
                "Все они переженились: Як на Цыпе, Як-цедрак на Цыпе-дрыпе,",
                "Як-цедрак-цедрак-цедрони на Цыпе-дрыпе-дрымпампони.",
                "И у них родились дети. У Яка с Цыпой — Шах, у Яка-цедрака с Цыпой-дрыпой — Шах-",
                "шарах, у Яка-цедрака-цедрака-цедрони с Цыпой-дрыпой-дрымпампони — Шах-шарах-шарах-широни.");
        Files.write(path, strings, StandardOpenOption.CREATE_NEW);
    }
}