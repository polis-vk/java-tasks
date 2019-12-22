package ru.mail.polis.homework.files;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.*;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class CopyFileTest {

    @After
    public void tearDown() {
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "first").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "second").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "third").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "fourth").toFile());
        FileUtils.deleteDirectory(Paths.get("src", "test", "resources", "directories", "copy1", "fifth").toFile());
    }

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
    public void copyDirEmpty() {
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
        List<String> strings = Arrays.asList("Жили-были три китайца: Як, Як-цедрак, Як-цедрак-цедрак-цедрони.",
                "Жили-были три китайки: Цыпа, Цыпа-дрыпа, Цыпа-дрыпа-дрымпампони.",
                "Все они переженились: Як на Цыпе, Як-цедрак на Цыпе-дрыпе,",
                "Як-цедрак-цедрак-цедрони на Цыпе-дрыпе-дрымпампони.",
                "И у них родились дети. У Яка с Цыпой — Шах, у Яка-цедрака с Цыпой-дрыпой — Шах-",
                "шарах, у Яка-цедрака-цедрака-цедрони с Цыпой-дрыпой-дрымпампони — Шах-шарах-шарах-широни.");
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