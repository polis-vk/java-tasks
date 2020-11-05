package ru.mail.polis.homework.io;


import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SerializerTest {

    private Serializer serializer = new Serializer();
    private List<Animal> animalsDefault = new ArrayList<>();
    private List<AnimalWithMethods> animalWithMethods = new ArrayList<>();
    private List<AnimalExternalizable> animalExternalizable = new ArrayList<>();


    private final List<Animal> AnimalsDefault_Starting = Arrays.asList(
            new Animal("BOBA", 43, 99.9, Characteristic.PASSIVE, Arrays.asList("Marucya", "Kolya")),
            new Animal("Marucya",28, 52.9, Characteristic.HOSTILE, Arrays.asList("Ilya")),
            new Animal("Kolya", 19, 72.88,Characteristic.SMART, Arrays.asList("Ilya")),
            new Animal("Ilya", 7, 24.75, Characteristic.STUPID, new ArrayList<>()));

    private final List<AnimalWithMethods> animalWithMethods_Starting = Arrays.asList(
            new AnimalWithMethods("BOBA", 43, 99.9, CharacteristicWithMethods.PASSIVE, Arrays.asList("Marucya", "Kolya")),
            new AnimalWithMethods("Marucya",28, 52.9, CharacteristicWithMethods.HOSTILE, Arrays.asList("Ilya")),
            new AnimalWithMethods("Kolya", 19, 72.88,CharacteristicWithMethods.SMART, Arrays.asList("Ilya")),
            new AnimalWithMethods("Ilya", 7, 24.75, CharacteristicWithMethods.STUPID, new ArrayList<>()));

    private final List<AnimalExternalizable> animalExternalizable_Starting = Arrays.asList(
            new AnimalExternalizable("BOBA", 43, 99.9, CharacteristicExternalizable.PASSIVE, Arrays.asList("Marucya", "Kolya")),
            new AnimalExternalizable("Marucya",28, 52.9, CharacteristicExternalizable.HOSTILE, Arrays.asList("Ilya")),
            new AnimalExternalizable("Kolya", 19, 72.88,CharacteristicExternalizable.SMART, Arrays.asList("Ilya")),
            new AnimalExternalizable("Ilya", 7, 24.75, CharacteristicExternalizable.STUPID, new ArrayList<>()));

    private final String DEFAULT = "default.bat";
    private final String METHODS = "methods.bat";
    private final String EXTERNAL = "externalizable.bat";
    private final String CUSTOM = "custom.bat";

    @Before
    public void start() {

        for (int i = 0; i < 1000; i++) {
            animalsDefault.add(AnimalsDefault_Starting.get(ThreadLocalRandom.current().nextInt(0, 4)));
        }
        for (int i = 0; i < 1000; i++) {
            animalWithMethods.add(animalWithMethods_Starting.get(ThreadLocalRandom.current().nextInt(0, 4)));
        }
        for (int i = 0; i < 1000; i++) {
            animalExternalizable.add(animalExternalizable_Starting.get(ThreadLocalRandom.current().nextInt(0, 4)));
        }
    }

    private void printInfo(long TimeSerializable, long TimeDeserializable, long size, String test) {
        System.out.println("\nTimeSerializable : " + TimeSerializable +
                "\nTimeDeserializable : " + TimeDeserializable +
                "\nSizeFile : " + size +
                "\nTest_" + test);
    }

    @Test
    public void TestSerializable() throws IOException, ClassNotFoundException {
        long startSerializable = System.currentTimeMillis();

        serializer.defaultSerialize(animalsDefault, DEFAULT);

        long endSerializable = System.currentTimeMillis();
        long TimeSerializable = endSerializable - startSerializable;

        long startDeserialize = System.currentTimeMillis();
        List<Animal> deserialize = new ArrayList<>();

        deserialize = serializer.defaultDeserialize(DEFAULT);

        long endSDeserialize = System.currentTimeMillis();
        long TimeDeserialize = endSDeserialize - startDeserialize;

        long fileSize = Files.size(Paths.get(DEFAULT));
        printInfo(TimeSerializable, TimeDeserialize, fileSize, "#1");

        Assert.assertArrayEquals(animalsDefault.toArray(), deserialize.toArray());
    }

    @Test
    public void TestSerializableWithMethods() throws IOException, ClassNotFoundException {

        long startSerializable = System.currentTimeMillis();

        serializer.serializeWithMethods(animalWithMethods, METHODS);

        long endSerializable = System.currentTimeMillis();
        long TimeSerializable = endSerializable - startSerializable;

        long fileSize = Files.size(Paths.get(METHODS));

        long startDeserialize = System.currentTimeMillis();
        List<AnimalWithMethods> deserialize = new ArrayList<>();

        deserialize = serializer.deserializeWithMethods(METHODS);

        long endSDeserialize = System.currentTimeMillis();
        long TimeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, TimeDeserialize, fileSize, "#2");

        Assert.assertArrayEquals(animalWithMethods.toArray(), deserialize.toArray());
    }

    @Test
    public void TestExternalizable() throws IOException, ClassNotFoundException {

        long startSerializable = System.currentTimeMillis();

        serializer.serializeWithExternalizable(animalExternalizable, EXTERNAL);

        long endSerializable = System.currentTimeMillis();
        long TimeSerializable = endSerializable - startSerializable;

        long fileSize = Files.size(Paths.get(EXTERNAL));

        long startDeserialize = System.currentTimeMillis();
        List<AnimalExternalizable> deserialize = new ArrayList<>();

        deserialize = serializer.deserializeWithExternalizable(EXTERNAL);

        long endSDeserialize = System.currentTimeMillis();
        long TimeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, TimeDeserialize, fileSize, "#3");

        Assert.assertArrayEquals(animalExternalizable.toArray(), deserialize.toArray());
    }

    @Test
    public void TestCustom() throws IOException, ClassNotFoundException {

        long startSerializable = System.currentTimeMillis();

        serializer.customSerialize(animalsDefault, CUSTOM);

        long endSerializable = System.currentTimeMillis();
        long TimeSerializable = endSerializable - startSerializable;

        long fileSize = Files.size(Paths.get(CUSTOM));

        long startDeserialize = System.currentTimeMillis();
        List<Animal> deserialize = new ArrayList<>();

        deserialize = serializer.customDeserialize(CUSTOM);

        long endSDeserialize = System.currentTimeMillis();
        long TimeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, TimeDeserialize, fileSize, "#4");

        Assert.assertArrayEquals(animalsDefault.toArray(), deserialize.toArray());
    }

    @After
    public void clear() {
        try {
            Files.deleteIfExists(Paths.get(DEFAULT));
            Files.deleteIfExists(Paths.get(METHODS));
            Files.deleteIfExists(Paths.get(EXTERNAL));
            Files.deleteIfExists(Paths.get(CUSTOM));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }




}
