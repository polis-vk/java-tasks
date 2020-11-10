package ru.mail.polis.homework.objects;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;
import ru.mail.polis.homework.io.objects.Serializer;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class SerializerTests {
    private final String fileName = "testFile.bin";
    private final int N = 10000;
    private static final Logger log = Logger.getLogger(SerializerTests.class.getName());
    private Path path;
    private Data data = new Data(N);

    @Before
    public void init() {
        path = Paths.get(fileName);
    }

    @Test
    public void defaultSerializeTest() throws IOException, ClassNotFoundException {
        List<Animal> animals = data.getAnimalList();
        long begW = System.currentTimeMillis();

        Serializer.defaultSerialize(animals, fileName);

        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();

        assertEquals(Serializer.defaultDeserialize(fileName), animals);

        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nDefault: \t\nWriting time = " + timeW + "\t\nReading time = " + timeR + "\t\nFile size = " + path.toFile().length() + " byte\n");
    }

    @Test
    public void withMethodsSerializeTest() throws IOException, ClassNotFoundException {
        List<AnimalWithMethods> animals = data.getAnimalWithMethodsList();

        long begW = System.currentTimeMillis();

        Serializer.serializeWithMethods(animals, fileName);

        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();

        assertEquals(Serializer.deserializeWithMethods(fileName), animals);

        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith methods: \t\nWriting time = " + timeW + "\t\nReading time = " + timeR + "\t\nFile size = " + path.toFile().length() + " byte\n");

    }

    @Test
    public void externalizableTest() throws IOException, ClassNotFoundException {
        List<AnimalExternalizable> animals = data.getAnimalExternalizableList();

        long begW = System.currentTimeMillis();

        Serializer.serializeWithExternalizable(animals, fileName);

        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();

        assertEquals(Serializer.deserializeWithExternalizable(fileName), animals);

        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith externalizable: \t\nWriting time = " + timeW + "\t\nReading time = " + timeR + "\t\nFile size = " + path.toFile().length() + " bytes\n");
    }


    @Test
    public void customTest() throws IOException {
        List<Animal> animals = data.getAnimalList();
        long begW = System.currentTimeMillis();

        Serializer.customSerialize(animals, fileName);

        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();

        assertEquals(Serializer.customDeserialize(fileName), animals);

        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nCustom: \t\nWriting time = " + timeW + "\t\nReading time = " + timeR + "\t\nFile size = " + path.toFile().length() + " bytes\n");
    }

    @After
    public void tierDown() {
        path.toFile().delete();
    }
}
