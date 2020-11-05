package ru.mail.polis.homework.objects;

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

public class SerializerTests {
    private final String fileName = "customWriting";
    private final int N = 100;
    private static final Logger log = Logger.getLogger(SerializerTests.class.getName());

    @Test
    public void defaultSerializeTest() throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<Animal> animals = (new Data()).getAnimalList();
        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.defaultSerialize(animals, fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.defaultDeserialize(fileName);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nDefault: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " byte");
        path.toFile().delete();
    }

    @Test
    public void withMethodsSerializeTest() throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<AnimalWithMethods> animals = (new Data()).getAnimalWithMethodsList();

        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.serializeWithMethods(animals, fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.deserializeWithMethods(fileName);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith methods: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " byte");
        path.toFile().delete();
    }

    @Test
    public void externalizableTest() throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<AnimalExternalizable> animals = (new Data()).getAnimalExternalizableList();

        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.serializeWithExternalizable(animals, fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.deserializeWithExternalizable(fileName);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nWith externalizable: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " byte");
        path.toFile().delete();
    }


    @Test
    public void customTest() throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<Animal> animals = (new Data()).getAnimalList();
        long begW = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.customSerialize(animals, fileName);
        }
        long endW = System.currentTimeMillis();
        long timeW = endW - begW;

        long begR = System.currentTimeMillis();
        for (int i = 0; i < N; i++) {
            Serializer.customDeserialize(fileName);
        }
        long endR = System.currentTimeMillis();
        long timeR = endR - begR;

        log.log(Level.INFO, "\nCustom: \t\nwriting time = " + timeW + "\t\nreading time = " + timeR + "\t\nfile size = " + path.toFile().length() + " byte");
        path.toFile().delete();
    }
}
