package ru.mail.polis.homework.io.blocking;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StructureTest {

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure.bin").toFile());
    }

    @Test
    public void emptyFile() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            assertNull(structureInputStream.readStructure());
            assertArrayEquals(new Structure[0], structureInputStream.readStructures());
        }
    }

    @Test
    public void oneObject() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        Structure structure = new Structure();
        structure.setId(1233554344533L);
        structure.setName("oneObject");
        structure.setSubStructures(new SubStructure[] {
                new SubStructure(12313, "sub-oneObject", true, 0.1),
                new SubStructure(12314, "sub-oneObject-4", false, 0.2)});
        structure.setCoeff((float) Math.E);
        structure.setFlag1(true);
        structure.setFlag2(true);
        structure.setFlag3(false);
        structure.setFlag4(true);
        structure.setParam((byte) 125);

        try (StructureOutputStream outputStream = new StructureOutputStream(file.toFile())) {
            outputStream.write(structure);
            assertTrue(Files.size(file) > 10);
        }
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            assertEquals(structure, structureInputStream.readStructure());
            assertNull(structureInputStream.readStructure());
            assertArrayEquals(new Structure[] {structure}, structureInputStream.readStructures());
        }
    }

    @Test
    public void oneObjectWithNull() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        Structure structure = new Structure();
        structure.setId(1233554344533L);
        structure.setName(null);
        structure.setSubStructures(null);
        structure.setCoeff((float) Math.E);
        structure.setFlag1(true);
        structure.setFlag2(true);
        structure.setFlag3(false);
        structure.setFlag4(true);
        structure.setParam((byte) 125);

        try (StructureOutputStream outputStream = new StructureOutputStream(file.toFile())) {
            outputStream.write(structure);
            assertTrue(Files.size(file) > 10);
        }
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            assertEquals(structure, structureInputStream.readStructure());
            assertNull(structureInputStream.readStructure());
            assertArrayEquals(new Structure[] {structure}, structureInputStream.readStructures());
        }
    }

    @Test
    public void oneObjectWithEmptyArrays() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        Structure structure = new Structure();
        structure.setId(1233554344533L);
        structure.setName("null");
        structure.setSubStructures(new SubStructure[] {});
        structure.setCoeff((float) Math.E);
        structure.setFlag1(true);
        structure.setFlag2(true);
        structure.setFlag3(false);
        structure.setFlag4(true);
        structure.setParam((byte) 125);

        try (StructureOutputStream outputStream = new StructureOutputStream(file.toFile())) {
            outputStream.write(structure);
            assertTrue(Files.size(file) > 10);
        }
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            assertEquals(structure, structureInputStream.readStructure());
            assertNull(structureInputStream.readStructure());
            assertArrayEquals(new Structure[] {structure}, structureInputStream.readStructures());
        }
    }



    @Test
    public void manyObject() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        Structure[] structures = new Structure[rnd.nextInt(100) + 2];
        for (int i = 0; i < structures.length; i++) {
            structures[i] = generate();
        }

        try (StructureOutputStream outputStream = new StructureOutputStream(file.toFile())) {
            outputStream.write(structures);
            assertTrue(Files.size(file) > 10L * structures.length);
        }
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            for (Structure structure : structures) {
                assertEquals(structure, structureInputStream.readStructure());
            }
            assertNull(structureInputStream.readStructure());
            assertArrayEquals(structures, structureInputStream.readStructures());
        }
        try (StructureInputStream structureInputStream = new StructureInputStream(file.toFile())) {
            assertArrayEquals(structures, structureInputStream.readStructures());
            assertNull(structureInputStream.readStructure());
        }
    }


    private static final Random rnd = new Random();
    private static Structure generate() {
        Structure structure = new Structure();
        structure.setId(rnd.nextLong());
        if (rnd.nextBoolean()) {
            structure.setName(generateString());
        }
        int count = rnd.nextInt(5);
        if (count > 0) {
            SubStructure[] subStructures = new SubStructure[count];
            for (int i = 0; i < count; i++) {
                subStructures[i] = new SubStructure(rnd.nextInt(), generateString(), rnd.nextBoolean(), rnd.nextDouble());
            }
            structure.setSubStructures(subStructures);
        }
        structure.setCoeff(rnd.nextFloat());
        structure.setFlag1(rnd.nextBoolean());
        structure.setFlag2(rnd.nextBoolean());
        structure.setFlag3(rnd.nextBoolean());
        structure.setFlag4(rnd.nextBoolean());
        byte[] b = new byte[1];
        rnd.nextBytes(b);
        structure.setParam(b[0]);
        return structure;
    }

    private static String generateString() {
        char[] chars = new char[rnd.nextInt(18) + 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (rnd.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }
}
