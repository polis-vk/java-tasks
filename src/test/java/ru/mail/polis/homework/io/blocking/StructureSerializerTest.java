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


public class StructureSerializerTest {
    private static final Random rnd = new Random();
    private static final int NUM_OF_TESTS = 500;
    private final StructureSerializer structureSerializer = new StructureSerializer();

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure.bin").toFile());
    }

    private static String generateString() {
        char[] chars = new char[rnd.nextInt(18) + 2];
        for (int i = 0; i < chars.length; i++) {
            chars[i] = (char) (rnd.nextInt('z' - '0') + '0');
        }
        return new String(chars);
    }

    public static Structure generateStructure() {
        Structure structure = new Structure();
        structure.setId(rnd.nextLong());
        if (rnd.nextBoolean()) {
            structure.setName(generateString());
        }
        int count = rnd.nextInt(10);
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
        structure.setParam((byte) rnd.nextInt(150));
        return structure;
    }

    @Test
    public void defaultSerializerTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        List<Structure> structures = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            structures.add(generateStructure());
        }
        long startInputTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_OF_TESTS; i++) {
            structureSerializer.defaultSerialize(structures, file.toString());
        }
        long finishInputTime = System.currentTimeMillis();
        long startOutputTime = System.currentTimeMillis();
        structureSerializer.defaultDeserialize(file.toString());
        long finishOutputTime = System.currentTimeMillis();
        System.out.println("Default serialization");
        System.out.println("File size in byte: " + Files.size(file));
        System.out.println("Record time: " + (finishInputTime - startInputTime));
        System.out.println("Reading time: " + (finishOutputTime - startOutputTime) + "\n");
    }

    @Test
    public void customSerializerTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        List<Structure> structures = new ArrayList<>();
        for (int i = 0; i < 25; i++) {
            structures.add(generateStructure());
        }
        long startInputTime = System.currentTimeMillis();
        for (int i = 0; i < NUM_OF_TESTS; i++) {
            structureSerializer.serialize(structures, file.toString());
        }
        long finishInputTime = System.currentTimeMillis();
        long startOutputTime = System.currentTimeMillis();
        structureSerializer.deserialize(file.toString());
        long finishOutputTime = System.currentTimeMillis();
        System.out.println("Custom serialization");
        System.out.println("File size in byte: " + Files.size(file));
        System.out.println("Record time: " + (finishInputTime - startInputTime));
        System.out.println("Reading time: " + (finishOutputTime - startOutputTime));
    }
}