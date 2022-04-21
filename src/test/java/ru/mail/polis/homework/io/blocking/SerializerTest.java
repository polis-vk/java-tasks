package ru.mail.polis.homework.io.blocking;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;


public class SerializerTest {

    private ArrayList<Structure> structures;

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);

        Random random = new Random();
        structures = new ArrayList<>();
        for (int i = 0; i < 500; i++) {
            Structure structure = new Structure();
            structure.setId(random.nextLong());
            structure.setName("Object" + i);
            structure.setSubStructures(new SubStructure[]{
                    new SubStructure(random.nextInt(), "sub-oneObject", random.nextBoolean(), Math.random()),
                    new SubStructure(random.nextInt(), "sub-oneObject-4", random.nextBoolean(), Math.random())});
            structure.setCoeff(random.nextFloat());
            structure.setFlag1(random.nextBoolean());
            structure.setFlag2(random.nextBoolean());
            structure.setFlag3(random.nextBoolean());
            structure.setFlag4(random.nextBoolean());
            structure.setParam((byte) (Math.random() * 125));
            structures.add(structure);
        }
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure.txt").toFile());
    }

    @Test
    public void testDefault() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.txt");
        Files.createFile(file);

        System.out.println("Default serializer");
        StructureSerializer structureSerializer = new StructureSerializer();
        long startTime = System.currentTimeMillis();
        structureSerializer.defaultSerialize(structures, file.toString());
        long writeTime = System.currentTimeMillis() - startTime;
        System.out.println("Write time: " + writeTime);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = structureSerializer.defaultDeserialize(file.toString());
        long readTime = (System.currentTimeMillis() - startTime);
        System.out.println("Read time: " + readTime);
        assertEquals(structures, structuresList);
    }

    @Test
    public void testCustom() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.txt");
        Files.createFile(file);
        StructureSerializer structureSerializer = new StructureSerializer();

        System.out.println("Custom serializer");
        long startTime = System.currentTimeMillis();
        structureSerializer.serialize(structures, file.toString());
        long writeTime = System.currentTimeMillis() - startTime;
        System.out.println("Write time: " + writeTime);

        startTime = System.currentTimeMillis();
        List<Structure> structuresList = structureSerializer.deserialize(file.toString());
        long readTime = (System.currentTimeMillis() - startTime);
        System.out.println("Read time: " + readTime);
        assertEquals(structures, structuresList);
        assertEquals(structures.size(), structuresList.size());
    }
}
