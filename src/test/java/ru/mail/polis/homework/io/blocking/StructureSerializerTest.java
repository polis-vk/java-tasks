package ru.mail.polis.homework.io.blocking;

import org.junit.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;

public class StructureSerializerTest {

    private static Random random = new Random();
    final int SIZE = 50000;
    private final StructureSerializer structureSerializer = new StructureSerializer();

    private static Structure createStructure() {
        Structure structure = new Structure();
        structure.setId(random.nextLong());
        structure.setName(random.nextInt() + "NameStructure");
        structure.setCoeff(random.nextFloat());
        structure.setFlag1(random.nextBoolean());
        structure.setFlag2(random.nextBoolean());
        structure.setFlag3(random.nextBoolean());
        structure.setFlag4(random.nextBoolean());
        structure.setParam((byte) random.nextInt(127));
        SubStructure[] subStructures = new SubStructure[20];
        for (int i = 0; i < 20; i++) {
            subStructures[i] = new SubStructure(random.nextInt(), random.nextInt() + "NameStructure", random.nextBoolean(), random.nextDouble());
        }
        structure.setSubStructures(subStructures);
        return structure;
    }

    public List<Structure> createList() {
        List<Structure> structuresList = new ArrayList<>(SIZE);
        for (int i = 0; i < SIZE; i++) {
            structuresList.add(i, createStructure());
        }
        return structuresList;
    }

    @Before
    public void setUp() throws IOException {
        Path path = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(path);
    }

    @After
    public void tearDown() throws Exception {
        Files.delete(Paths.get("src", "test", "resources", "blocking", "structure.bin"));
    }

    @Test
    public void defaultSerializeTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("Default serialize and deserialize");
        long start = System.currentTimeMillis();
        structureSerializer.defaultSerialize(createList(), file.toString());
        long inputTime = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        structureSerializer.defaultDeserialize(file.toString());
        long outputTime = System.currentTimeMillis() - start;
        System.out.println("Time " + (inputTime + outputTime) + "ms");
        System.out.println("Size " + Files.size(file) + " bytes");
    }

    @Test
    public void SerializeTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("Serialize and Deserialize");
        long start = System.currentTimeMillis();
        structureSerializer.serialize(createList(), file.toString());
        long inputTime = System.currentTimeMillis() - start;
        start = System.currentTimeMillis();
        structureSerializer.deserialize(file.toString());
        long outputTime = System.currentTimeMillis() - start;
        System.out.println("Time " + (inputTime + outputTime) + "ms");
        System.out.println("Size " + Files.size(file) + " bytes");
    }
}
