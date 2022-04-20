package ru.mail.polis.homework.io.blocking;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class StructureSerializerTest {
    private static final Random rnd = new Random();
    private static final int callsNumber = 100000;
    private static final List<Structure> structures = generateStructuresList();
    private static final StructureSerializer structureSerializer = new StructureSerializer();
    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "serializer");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "serializer", "structures.bin").toFile());
    }

    @Test
    public void testDefaultSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "structures.bin");
        Files.createFile(file);

        System.out.println("testDefaultSerialize");
        long start = System.currentTimeMillis();
        for (int i = 0; i < callsNumber; i++) {
            structureSerializer.defaultSerialize(structures, file.toString());
        }
        long finish = System.currentTimeMillis();
        System.out.println("Time to write " + callsNumber + " files = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<Structure> readStructures = structureSerializer.defaultDeserialize(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(structures, readStructures);

        System.out.println("Size of file = " + Files.size(file) + "bytes");
    }

    @Test
    public void testStructureSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "serializer", "structures.bin");
        Files.createFile(file);

        System.out.println("testStructureSerialize");
        long start = System.currentTimeMillis();
        for (int i = 0; i < callsNumber; i++) {
            structureSerializer.serialize(structures, file.toString());
        }
        long finish = System.currentTimeMillis();
        System.out.println("Time to write " + callsNumber + " files = " + (finish - start) + " ms");

        start = System.currentTimeMillis();
        List<Structure> readStructures = structureSerializer.deserialize(file.toString());
        finish = System.currentTimeMillis();
        System.out.println("Time to read file = " + (finish - start) + " ms");

        assertEquals(structures, readStructures);

        System.out.println("Size of file = " + Files.size(file) + "bytes");
    }

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

    private static List<Structure> generateStructuresList() {
        List<Structure> structures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            structures.add(generate());
        }
        return structures;
    }
}