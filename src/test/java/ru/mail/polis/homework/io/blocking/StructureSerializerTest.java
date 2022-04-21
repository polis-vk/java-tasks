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
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

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

    @Test
    public void defaultSerializerTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        List<Structure> structures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            structures.add(generate());
        }
        StructureSerializer serializer = new StructureSerializer();
        long serializeStart = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            serializer.defaultSerialize(structures, file.toString());
        }
        long serializeEnd = System.currentTimeMillis();
        long deserializeStart = System.currentTimeMillis();
        List<Structure> deserializedStructures = serializer.defaultDeserialize(file.toString());
        long deserializeEnd = System.currentTimeMillis();
        assertEquals(structures, deserializedStructures);
        System.out.println("Default Serializer:");
        System.out.println("Размер файла: " + Files.size(file) + " байт");
        System.out.println("Время записи: " + (serializeEnd - serializeStart) + " мс");
        System.out.println("Время чтения: " + (deserializeEnd - deserializeStart) + " мс");
    }

    @Test
    public void serializerTest() throws IOException {
        Path file = Paths.get("src", "test", "resources", "blocking", "structure.bin");
        Files.createFile(file);
        List<Structure> structures = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            structures.add(generate());
        }
        StructureSerializer serializer = new StructureSerializer();
        long serializeStart = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            serializer.serialize(structures, file.toString());
        }
        long serializeEnd = System.currentTimeMillis();
        long deserializeStart = System.currentTimeMillis();
        List<Structure> deserializedStructures = serializer.deserialize(file.toString());
        long deserializeEnd = System.currentTimeMillis();
        assertEquals(structures, deserializedStructures);
        System.out.println("Serializer:");
        System.out.println("Размер файла: " + Files.size(file) + " байт");
        System.out.println("Время записи: " + (serializeEnd - serializeStart) + " мс");
        System.out.println("Время чтения: " + (deserializeEnd - deserializeStart) + " мс");
        System.out.println("------------------------");
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
