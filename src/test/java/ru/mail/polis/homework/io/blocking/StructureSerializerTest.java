package ru.mail.polis.homework.io.blocking;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;


public class StructureSerializerTest {

    private static final List<Structure> structures = new ArrayList<>();
    private static final int SIZE_LIST = 100;

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure.bin").toFile());
    }

    @BeforeClass
    public static void randomStructure() {
        Random random = new Random();
        for (int i = 0; i < SIZE_LIST; i++) {
            Structure structure = new Structure();
            structure.setId(random.nextLong());
            structure.setName(random.nextInt() + "Serialize? No problem");
            SubStructure[] subStructures = new SubStructure[2];
            subStructures[0] = randomSubStructure(random);
            subStructures[1] = randomSubStructure(random);
            structure.setSubStructures(subStructures);
            structure.setCoeff(random.nextFloat());
            structure.setFlag1(random.nextBoolean());
            structure.setFlag2(random.nextBoolean());
            structure.setFlag3(random.nextBoolean());
            structure.setFlag4(random.nextBoolean());
            structure.setParam((byte) random.nextInt(333));
            structures.add(structure);
        }
    }

    private static SubStructure randomSubStructure(Random random) {
        return new SubStructure(random.nextInt(), random.nextInt() + "Name",
                random.nextBoolean(), random.nextDouble());
    }

    @Test
    public void defaultSerialize() throws IOException {
        System.out.println("Default");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);

        long start = System.currentTimeMillis();
        StructureSerializer.defaultSerialize(structures, file.toString());
        long inputSpeed = (System.currentTimeMillis() - start);
        System.out.println("Input time " + inputSpeed + "ms");

        start = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.defaultDeserialize(file.toString());
        System.out.println("Output time: " + (System.currentTimeMillis() - start) + "ms");

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(structures, structuresList);
    }

    @Test
    public void customSerialize() throws IOException {
        System.out.println("Custom");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);

        long start = System.currentTimeMillis();
        StructureSerializer.serialize(structures, file.toString());
        long inputSpeed = (System.currentTimeMillis() - start);
        System.out.println("Input time: " + inputSpeed+"ms");

        start = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.deserialize(file.toString());
        System.out.println("Output time: " + (System.currentTimeMillis() - start) + "ms");

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(structures, structuresList);
    }
}