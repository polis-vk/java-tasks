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
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class SerializerTest {

    private static final StructureSerializer STRUCTURE_SERIALIZER = new StructureSerializer();
    private static final List<Structure> STRUCTURES = new ArrayList<>();

    @BeforeClass
    public static void randomStructure() {
        Random random = new Random();
        for (int i = 0; i < 500000; i++) {
            Structure structure = new Structure();
            structure.setId(random.nextLong());
            structure.setName(random.nextInt() + "Hello world! (Привет мир)");
            SubStructure[] subStructures = new SubStructure[2];
            subStructures[0] = randomSubStructure(random);
            subStructures[1] = randomSubStructure(random);
            structure.setSubStructures(subStructures);
            structure.setCoeff(random.nextFloat());
            structure.setFlag1(random.nextBoolean());
            structure.setFlag2(random.nextBoolean());
            structure.setFlag3(random.nextBoolean());
            structure.setFlag4(random.nextBoolean());
            structure.setParam((byte) random.nextInt(125));
            STRUCTURES.add(structure);
        }
    }

    private static SubStructure randomSubStructure(Random random) {
        return new SubStructure(random.nextInt(), random.nextInt() + "Name (Имя)",
                random.nextBoolean(), random.nextDouble());
    }

    @BeforeClass
    public static void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);
    }

    @AfterClass
    public static void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure1.bin").toFile());
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure2.bin").toFile());
    }

    @Test
    public void defaultSerializeAndDefaultDeserialize() throws IOException {
        System.out.println("Default S/D");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure1.bin");
        Files.createFile(file);

        long startTime = System.currentTimeMillis();
        STRUCTURE_SERIALIZER.defaultSerialize(STRUCTURES, file.toString());
        long inputSpeed = (System.currentTimeMillis() - startTime);
        System.out.println("Write speed: " + inputSpeed);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = STRUCTURE_SERIALIZER.defaultDeserialize(file.toString());
        System.out.println("Read speed: " + (System.currentTimeMillis() - startTime));

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(STRUCTURES.size(), structuresList.size());
    }

    @Test
    public void serializeAndDeserialize() throws IOException {
        System.out.println("My S/D");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure2.bin");
        Files.createFile(file);

        long startTime = System.currentTimeMillis();
        STRUCTURE_SERIALIZER.serialize(STRUCTURES, file.toString());
        long inputSpeed = (System.currentTimeMillis() - startTime);
        System.out.println("Write speed: " + inputSpeed);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = STRUCTURE_SERIALIZER.deserialize(file.toString());
        System.out.println("Read speed: " + (System.currentTimeMillis() - startTime));

        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(STRUCTURES.size(), structuresList.size());
    }

}
