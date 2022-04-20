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
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertArrayEquals;

public class StructureSerializerTest {

    private static final List<Structure> structures = new ArrayList<>();
    private static final StructureSerializer serializer = new StructureSerializer();

    @BeforeClass
    public static void generateStructures() {
        for (int i = 0; i < 300_000; i++) {
            structures.add(generate());
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
    public void defaultSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("default serialize");

        long start = System.currentTimeMillis();
        serializer.defaultSerialize(structures, file.toString());

        System.out.println("time of default serialization = " + (System.currentTimeMillis() - start) + " ms");

        System.out.println("file size = " + Files.size(file) + " bytes");

        start = System.currentTimeMillis();
        List<Structure> resultOfDeserialize = serializer.defaultDeserialize(file.toString());
        System.out.println("time of default deserialization = " + (System.currentTimeMillis() - start) + " ms\n");

        assertArrayEquals(structures.toArray(), resultOfDeserialize.toArray());
    }

    @Test
    public void structureSerialize() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        System.out.println("structure serialize");

        long start = System.currentTimeMillis();
        serializer.serialize(structures, file.toString());

        System.out.println("time of structure serialization = " + (System.currentTimeMillis() - start) + " ms");

        System.out.println("file size = " + Files.size(file) + " bytes");

        start = System.currentTimeMillis();
        List<Structure> resultOfDeserialize = serializer.deserialize(file.toString());
        System.out.println("time of structure deserialization = " + (System.currentTimeMillis() - start) + " ms");

        assertArrayEquals(structures.toArray(), resultOfDeserialize.toArray());
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
