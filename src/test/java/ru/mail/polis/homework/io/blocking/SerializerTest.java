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

public class SerializerTest {

    private static List<Structure> structures = new ArrayList<>();

    @BeforeClass
    public static void randomStructure() {
        Random random = new Random();
        for (int i = 0; i < 200000; i++) {
            Structure structure = new Structure();
            structure.setId(random.nextLong());
            structure.setName(random.nextInt() + "Hello world!");
            SubStructure[] subStructures = new SubStructure[2];
            subStructures[0] = randomSubStructure(random);
            subStructures[1] = randomSubStructure(random);
            structure.setSubStructures(subStructures);
            structure.setCoeff(random.nextFloat());
            structure.setFlag1(random.nextBoolean());
            structure.setFlag2(random.nextBoolean());
            structure.setFlag3(random.nextBoolean());
            structure.setFlag4(random.nextBoolean());
            structure.setParam((byte) 125);
            structures.add(structure);
        }
    }

    private static SubStructure randomSubStructure(Random random) {
        return new SubStructure(random.nextInt(), random.nextInt() + "Name",
                random.nextBoolean(), random.nextDouble());
    }

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
    public void defaultSerializeAndDefaultDeserialize() throws IOException, ClassNotFoundException {
        System.out.println("Default S/D");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);


        long startTime = System.currentTimeMillis();
        StructureSerializer.defaultSerialize(structures, file.toString());
        long inputSpeed = (System.currentTimeMillis() - startTime);
        System.out.println("Input speed: " + inputSpeed);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.defaultDeserialize(file.toString());
        System.out.println("Output speed: " + (System.currentTimeMillis() - startTime));

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " mb");
        Assert.assertEquals(structures, structuresList);
    }

    @Test
    public void serializeAndDeserialize() throws IOException {
        System.out.println("Not Default S/D");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);

        long startTime = System.currentTimeMillis();
        StructureSerializer.serialize(structures, file.toString());
        long inputSpeed = (System.currentTimeMillis() - startTime);
        System.out.println("Input speed: " + inputSpeed);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.deserialize(file.toString());
        System.out.println("Output speed: " + (System.currentTimeMillis() - startTime));

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " mb");
        Assert.assertEquals(structures, structuresList);
    }

}
