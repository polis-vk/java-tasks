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
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class SerializerTest {

    private Serializer serializer = new Serializer();
    private List<Animal> animalsDefault = new ArrayList<>();
    private List<AnimalWithMethods> animalWithMethods = new ArrayList<>();
    private List<AnimalExternalizable> animalExternalizable = new ArrayList<>();

    private final String[] names = {"Вова", "Влад", "Костя", "Аркадий", "Анакондий", "Света", "Илья", "Толик", "Вика", "Кристина"};
    private final int[] ages = {10, 20, 30, 40, 50, 60, 70, 80, 90, 100};
    private final double[] weights = {11.11, 12.12, 24.24, 33.33, 49.94, 55.55, 69.69, 72.29, 88.88, 93.69};
    private final String[] inhabitancy = {"Африка", "Европа", "Антарктида", "Северный полюс", "Зимбабве", "Туркмения", "США", "РАССИЯ", "Польша", "Украина"};

    private final String DEFAULT = "default.bat";
    private final String METHODS = "methods.bat";
    private final String EXTERNAL = "externalizable.bat";
    private final String CUSTOM = "custom.bat";

    @Before
    public void start() {
        for (int i = 0; i < 1000; i++) {
            animalsDefault.add(new Animal(
                    names[ThreadLocalRandom.current().nextInt(0, 10)],
                    ages[ThreadLocalRandom.current().nextInt(0, 10)],
                    weights[ThreadLocalRandom.current().nextInt(0, 10)],
                    Characteristic.values()[ThreadLocalRandom.current().nextInt(0, 6)],
                    Collections.singletonList(names[ThreadLocalRandom.current().nextInt(0, 10)]),
                    new AnimalInfo(
                            ages[ThreadLocalRandom.current().nextInt(0, 10)],
                            inhabitancy[ThreadLocalRandom.current().nextInt(0, 10)],
                            ThreadLocalRandom.current().nextBoolean())
                    ));
        }

        for (int i = 0; i < 1000; i++) {
            animalWithMethods.add(new AnimalWithMethods(
                    names[ThreadLocalRandom.current().nextInt(0, 10)],
                    ages[ThreadLocalRandom.current().nextInt(0, 10)],
                    weights[ThreadLocalRandom.current().nextInt(0, 10)],
                    CharacteristicWithMethods.values()[ThreadLocalRandom.current().nextInt(0, 6)],
                    Collections.singletonList(names[ThreadLocalRandom.current().nextInt(0, 10)]),
                    new AnimalInfoWithMethods(
                            ages[ThreadLocalRandom.current().nextInt(0, 10)],
                            inhabitancy[ThreadLocalRandom.current().nextInt(0, 10)],
                            ThreadLocalRandom.current().nextBoolean())
            ));
        }

        for (int i = 0; i < 1000; i++) {
            animalExternalizable.add(new AnimalExternalizable(
                    names[ThreadLocalRandom.current().nextInt(0, 10)],
                    ages[ThreadLocalRandom.current().nextInt(0, 10)],
                    weights[ThreadLocalRandom.current().nextInt(0, 10)],
                    Characteristic.values()[ThreadLocalRandom.current().nextInt(0, 6)],
                    Collections.singletonList(names[ThreadLocalRandom.current().nextInt(0, 10)]),
                    new AnimalInfo(
                            ages[ThreadLocalRandom.current().nextInt(0, 10)],
                            inhabitancy[ThreadLocalRandom.current().nextInt(0, 10)],
                            ThreadLocalRandom.current().nextBoolean())
            ));
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

        long fileSize = Files.size(Paths.get(DEFAULT));

        long startDeserialize = System.currentTimeMillis();
        List<Animal> deserialize;

        deserialize = serializer.defaultDeserialize(DEFAULT);

        long endSDeserialize = System.currentTimeMillis();
        long timeDeserialize = endSDeserialize - startDeserialize;


        printInfo(TimeSerializable, timeDeserialize, fileSize, "#1");

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
        List<AnimalWithMethods> deserialize;

        deserialize = serializer.deserializeWithMethods(METHODS);

        long endSDeserialize = System.currentTimeMillis();
        long timeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, timeDeserialize, fileSize, "#2");

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
        List<AnimalExternalizable> deserialize;

        deserialize = serializer.deserializeWithExternalizable(EXTERNAL);

        long endSDeserialize = System.currentTimeMillis();
        long timeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, timeDeserialize, fileSize, "#3");

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
        List<Animal> deserialize;

        deserialize = serializer.customDeserialize(CUSTOM);

        long endSDeserialize = System.currentTimeMillis();
        long timeDeserialize = endSDeserialize - startDeserialize;
        printInfo(TimeSerializable, timeDeserialize, fileSize, "#4");

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
