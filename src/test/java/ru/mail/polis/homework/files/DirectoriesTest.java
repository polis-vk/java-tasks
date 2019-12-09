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

import static org.junit.Assert.assertEquals;

public class DirectoriesTest {



    @After
    public void tearDown() throws Exception {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "first").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "second").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "third").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "fourth").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "fifth").toFile());
    }

    @Test
    public void onlyFiles() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "directories", "first");
        Files.createDirectories(dir);
        for (int i = 0; i < 10; i++) {
            createFile(Paths.get("src", "test", "resources", "directories", "first", "file" + i + ".txt"));
        }
        assertEquals(11, Directories.removeWithFile(dir.toString()));
    }

    @Test
    public void onlyDirectories() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "directories", "second");
        Files.createDirectories(dir);

        Path dir1 = Paths.get("src", "test", "resources", "directories", "second", "dir1");
        Files.createDirectories(dir1);
        Path dir2 = Paths.get("src", "test", "resources", "directories", "second", "dir2");
        Files.createDirectories(dir2);
        Path dir3 = Paths.get("src", "test", "resources", "directories", "second", "dir1", "dir1dir1");
        Files.createDirectories(dir3);
        Path dir4 = Paths.get("src", "test", "resources", "directories", "second", "dir1", "dir1dir2");
        Files.createDirectories(dir4);
        Path dir5 = Paths.get("src", "test", "resources", "directories", "second", "dir2", "dir2dir1");
        Files.createDirectories(dir5);
        Path dir6 = Paths.get("src", "test", "resources", "directories", "second", "dir2", "dir2dir2");
        Files.createDirectories(dir6);
        Path dir7 = Paths.get("src", "test", "resources", "directories", "second", "dir2", "dir2dir1", "dirdir");
        Files.createDirectories(dir7);
        assertEquals(8, Directories.removeWithFile(dir.toString()));
    }

    @Test
    public void one() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "directories", "third");
        Files.createDirectories(dir);
        assertEquals(1, Directories.removeWithFile(dir.toString()));
    }

    @Test
    public void empty() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "directories", "forth");
        assertEquals(0, Directories.removeWithFile(dir.toString()));
    }


    @Test
    public void hard() throws IOException {
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
        assertEquals(35, Directories.removeWithFile(dir.toString()));

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