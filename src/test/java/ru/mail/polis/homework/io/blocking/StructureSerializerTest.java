package ru.mail.polis.homework.io.blocking;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.EOFException;
import java.io.File;
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

    private static List<Structure> structureList = new ArrayList<>();
    private static SubStructure[] subStructureList = new SubStructure[2];

    private static void subStructureFill(Random random) {
        subStructureList[0] =  new SubStructure(random.nextInt(), random.nextInt() + "Name", random.nextBoolean(), random.nextDouble());
        subStructureList[1] = new SubStructure(random.nextInt(), random.nextInt() + "Name", random.nextBoolean(), random.nextDouble());
    }

    @Before
    public void structureFill() {
        Random random = new Random();
        for (int i = 0; i < 100; i++) {
            Structure structure = new Structure();
            structure.setId(random.nextLong());
            structure.setName(random.nextInt() + "Hello world!");
            subStructureFill(random);
            structure.setSubStructures(subStructureList);
            structure.setCoeff(random.nextFloat());
            structure.setFlag1(random.nextBoolean());
            structure.setFlag2(random.nextBoolean());
            structure.setFlag3(random.nextBoolean());
            structure.setFlag4(random.nextBoolean());
            structure.setParam((byte) random.nextInt(127));
        }
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
    public void defaultSerializeDeserialize() throws IOException, EOFException, ClassNotFoundException {
        System.out.println("Default Serialize and Deserialize");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        long startTime = System.currentTimeMillis();
        StructureSerializer.defaultSerialize(structureList, file.toString());
        long inputTime = (System.currentTimeMillis() - startTime);
        System.out.println("Input time: " + inputTime);
        startTime = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.defaultDeserialize(file.toString());
        System.out.println("Output time: " + (System.currentTimeMillis() - startTime));
        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(structureList, structuresList);
    }

    @Test
    public void serializeDeserialize() throws IOException {
        System.out.println("Serialize and Deserialize");
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        long startTime = System.currentTimeMillis();
        StructureSerializer.serialize(structureList, file.toString());
        long inputTime = (System.currentTimeMillis() - startTime);
        System.out.println("Input speed: " + inputTime);
        startTime = System.currentTimeMillis();
        List<Structure> structuresList = StructureSerializer.deserialize(file.toString());
        System.out.println("Output speed: " + (System.currentTimeMillis() - startTime));
        File file1 = new File(file.toString());
        System.out.println("Size file: " + file1.length() + " bytes");
        Assert.assertEquals(structureList, structuresList);
    }
}
