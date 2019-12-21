package ru.mail.polis.homework.files;

import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CopyFileTest {

    @Before
    public void setUp() throws Exception {
        // --------------------------------------- create source for copyOnlyFile --------------------------------------
//        Path dir = Paths.get("src", "test", "resources", "directories", "copy", "first");
//        Path file = Paths.get("src", "test", "resources", "directories", "copy", "first", "file.txt");
//        Files.createDirectories(dir);
//        createFile(file);
        // --------------------------------------- create source for copyOnlyFile --------------------------------------


        // --------------------------------------- create source for copyOnlyDir ---------------------------------------
//        Path dir2 = Paths.get("src", "test", "resources", "directories", "copy", "second");
//        Files.createDirectories(dir2);
        // --------------------------------------- create source for copyOnlyDir ---------------------------------------


        // ------------------------------------- create source for copyDirWithFiles ------------------------------------
//        Path dir3 = Paths.get("src", "test", "resources", "directories", "copy", "third");
//        Path file1 = Paths.get("src", "test", "resources", "directories", "copy", "third", "file1.txt");
//        Path file2 = Paths.get("src", "test", "resources", "directories", "copy", "third", "file2.txt");
//        Path file3 = Paths.get("src", "test", "resources", "directories", "copy", "third", "file3.txt");
//        Path file4 = Paths.get("src", "test", "resources", "directories", "copy", "third", "file4.txt");
//        Files.createDirectories(dir3);
//        createFile(file1);
//        createFile(file2);
//        createFile(file3);
//        createFile(file4);
        // ------------------------------------- create source for copyDirWithFiles ------------------------------------


        // --------------------------------------- create source for copyDirHard ---------------------------------------
//        Path dir5 = Paths.get("src", "test", "resources", "directories", "copy", "fifth");
//        Files.createDirectories(dir5);
//        for (int i = 0; i < 10; i++) {
//            createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "file" + i + ".txt"));
//        }
//        Path dir51 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir1");
//        Files.createDirectories(dir51);
//        createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir1", "file1.txt"));
//        Path dir52 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2");
//        Files.createDirectories(dir52);
//        Path dir53 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir1", "dir1dir1");
//        Files.createDirectories(dir53);
//        for (int i = 0; i < 3; i++) {
//            createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir1", "dir1dir1",
//                    "file" + i + ".txt"));
//        }
//        Path dir54 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir1", "dir1dir2");
//        Files.createDirectories(dir54);
//        Path dir55 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir1");
//        Files.createDirectories(dir55);
//        createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir1",
//                "file1.txt"));
//        Path dir56 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir2");
//        Files.createDirectories(dir56);
//        for (int i = 0; i < 5; i++) {
//            createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir2",
//                    "file" + i + ".txt"));
//        }
//        Path dir57 = Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir1", "dirdir");
//        Files.createDirectories(dir57);
//        for (int i = 0; i < 7; i++) {
//            createFile(Paths.get("src", "test", "resources", "directories", "copy", "fifth", "dir2", "dir2dir1", "dirdir",
//                    "file" + i + ".txt"));
//        }
        // --------------------------------------- create source for copyDirHard ---------------------------------------

    }

//    @After
//    public void tearDown() throws Exception {
//        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "first").toFile());
//        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "second").toFile());
//        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "third").toFile());
//        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "fourth").toFile());
//        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "fifth").toFile());
//    }

    @Test
    public void copyOnlyFile() throws IOException {
        Path file = Paths.get("src", "test", "resources", "directories", "copy", "first", "file.txt");
        Path dest = Paths.get("src", "test", "resources", "directories", "copy1", "first", "file.txt");
        CopyFile.copySmallFiles(file.toString(), dest.toString());
        checkCopy(file, dest, false);
    }

    @Test
    public void copyOnlyDir() throws IOException {
        Path source = Paths.get("src", "test", "resources", "directories", "copy", "second");
        Path dest = Paths.get("src", "test", "resources", "directories", "copy1", "second");
        CopyFile.copySmallFiles(source.toString(), dest.toString());
        checkCopy(source, dest, true);
    }

    @Test
    public void copyDirWithFiles() throws IOException {
        Path source = Paths.get("src", "test", "resources", "directories", "copy", "third");
        Path dest = Paths.get("src", "test", "resources", "directories", "copy1", "third");
        CopyFile.copySmallFiles(source.toString(), dest.toString());
        checkCopy(source, dest, true);
    }

    @Test
    public void copyDirEmpty()  {
        Path source = Paths.get("src", "test", "resources", "directories", "copy", "forth");
        Path dest = Paths.get("src", "test", "resources", "directories", "copy1", "forth");
        CopyFile.copySmallFiles(source.toString(), dest.toString());
        try {
            Files.walk(dest).collect(Collectors.toList());
            fail();
        } catch (IOException e) {
            assertTrue(e instanceof NoSuchFileException);
        }
    }

    @Test
    public void copyDirHard() throws IOException {
        Path source = Paths.get("src", "test", "resources", "directories", "copy", "fifth");
        Path dest = Paths.get("src", "test", "resources", "directories", "copy1", "fifth");
        CopyFile.copySmallFiles(source.toString(), dest.toString());
        checkCopy(source, dest, true);
    }

    private void createFile(Path path) throws IOException {
        List<String> strings = Arrays.asList("Java (Indonesian: Jawa) is an island of Indonesia, bordered by the Indian Ocean on the south and the Java Sea on the north.",
                "With a population of over 141 million (Java only) or 145 million (including the inhabitants of its surrounding islands),",
                "Java has 56.7 percent of the Indonesian population and is the world's most populous island.[1] The Indonesian capital city,",
                "Jakarta, is located on its northwestern coast. Much of Indonesian history took place on Java.",
                "It was the centre of powerful Hindu-Buddhist empires, the Islamic sultanates, and the core of the colonial Dutch East Indies.",
                "Java was also the center of the Indonesian struggle for independence during the 1930s and 1940s.");
        Files.write(path, strings, StandardOpenOption.CREATE_NEW);
    }

    private void checkCopy(Path source, Path dest, boolean isDirectory) throws IOException {
        assertTrue(Files.exists(source));
        assertTrue(Files.exists(dest));
        assertTrue(isDirectory ? Files.isDirectory(source) : Files.isRegularFile(source));
        assertTrue(isDirectory ? Files.isDirectory(dest) : Files.isRegularFile(dest));
        List<Path> sourcePaths = Files.walk(source).collect(Collectors.toList());
        List<Path> destPaths = Files.walk(dest).collect(Collectors.toList());
        assertEquals(sourcePaths.size(), destPaths.size());
        for (int i = 0; i < sourcePaths.size(); i++) {
            int sourceIndex = sourcePaths.get(i).toString().indexOf("copy") + 4;
            int destIndex = destPaths.get(i).toString().indexOf("copy1") + 5;
            assertEquals(sourcePaths.get(i).toString().substring(sourceIndex),
                    destPaths.get(i).toString().substring(destIndex));
        }
    }
}