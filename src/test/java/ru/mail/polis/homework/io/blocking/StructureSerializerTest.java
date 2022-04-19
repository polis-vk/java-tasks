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

    @Before
    public void setUp() throws IOException {
        Path dir = Paths.get("src", "test", "resources", "blocking");
        Files.createDirectories(dir);
    }

    @After
    public void tearDown() throws Exception {
        FileUtils.forceDelete(Paths.get("src", "test", "resources", "blocking", "structure.bin").toFile());
    }

    private static final Random rnd = new Random();

    private static Structure generateStructure() {
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

    public List<Structure> generateList() {
        List<Structure> structuresList = new ArrayList<>(10);
        for (int i = 0; i < 10; i++) {
            structuresList.add(i, generateStructure());
        }
        return structuresList;
    }

    final long IO_COUNT = 6000;
    private final StructureSerializer structureSerializer = new StructureSerializer();

    @Test
    public void defaultSerializeTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("\nDefault serialize:");

        long startInput = System.currentTimeMillis();
        for (int i = 0; i < IO_COUNT; i++) {
            structureSerializer.defaultSerialize(generateList(), file.toString());
        }
        long finishInput = System.currentTimeMillis();
        System.out.println("Writing time: " + (finishInput - startInput) + "ms");

        long startOutput = System.currentTimeMillis();
        structureSerializer.defaultDeserialize(file.toString());
        long finishOutput = System.currentTimeMillis();
        System.out.println("Reading time: " + (finishOutput - startOutput) + "ms");

        System.out.println("File size: " + Files.size(file) + " bytes\n");
    }

    @Test
    public void customSerializeTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("Custom serialize:");

        long startInput = System.currentTimeMillis();
        for (int i = 0; i < IO_COUNT; i++) {
            structureSerializer.serialize(generateList(), file.toString());
        }
        long finishInput = System.currentTimeMillis();
        System.out.println("Writing time: " + (finishInput - startInput) + "ms");

        long startOutput = System.currentTimeMillis();
        structureSerializer.deserialize(file.toString());
        long finishOutput = System.currentTimeMillis();
        System.out.println("Reading time: " + (finishOutput - startOutput) + "ms");

        System.out.println("File size: " + Files.size(file) + " bytes");
    }
}