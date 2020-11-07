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
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import static org.junit.Assert.assertEquals;

public class SerializerTests {
    private final String fileName = "testFile.bin";
    private final int N = 1000;
    private static final Logger log = Logger.getLogger(SerializerTests.class.getName());
    private Path path;

    @Before
    public void init(){
        path = Paths.get(fileName);
    }

    @Test
    public void defaultSerializeTest() throws IOException, ClassNotFoundException {
        List<Animal> animals = (new Data()).getAnimalList();
        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.defaultSerialize(new ArrayList<>(animals), fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            assertEquals(Serializer.defaultDeserialize(fileName), animals);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nDefault: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " byte");
    }

    @Test
    public void withMethodsSerializeTest() throws IOException, ClassNotFoundException {
        List<AnimalWithMethods> animals = (new Data()).getAnimalWithMethodsList();

        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.serializeWithMethods(new ArrayList<>(animals), fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            assertEquals(Serializer.deserializeWithMethods(fileName), animals);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith methods: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " bytes\n");
    }

    @Test
    public void externalizableTest() throws IOException, ClassNotFoundException {
        List<AnimalExternalizable> animals = (new Data()).getAnimalExternalizableList();

        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.serializeWithExternalizable(new ArrayList<>(animals), fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            assertEquals(Serializer.deserializeWithExternalizable(fileName), animals);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith externalizable: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " bytes\n");
    }


    @Test
    public void customTest() throws IOException {
        List<Animal> animals = (new Data()).getAnimalList();
        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.customSerialize(new ArrayList<>(animals), fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            assertEquals(Serializer.customDeserialize(fileName), animals);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nCustom: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " bytes\n");
    }

    @After
    public void tierDown(){
        path.toFile().delete();
    }
}
