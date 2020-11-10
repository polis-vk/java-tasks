package ru.mail.polis.homework.io.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SerializerTest {
    private final List<Animal> animals = new ArrayList<>();
    private final List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private final List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private final Serializer serializer = new Serializer();

    private final String fileTest = "fileTest";
    private final int number = 100;
    private final Random random = new Random();

    @Before
    public void initiation() {
        for (int i = 0; i < number; i++) {
            animals.add(new Animal("Animal" + i, randomHabitat(), new Iq(i), i, random.nextInt(10) / 2 == 0, Arrays.asList("meat" + i, "water" + i)));
        }

        for (Animal animal : animals) {
            animalsWithMethods.add(new AnimalWithMethods(animal.getName(), animal.getHabitat(), animal.getIq(), animal.getAge(), animal.isPredator(), animal.getFood()));
            animalsExternalizable.add(new AnimalExternalizable(animal.getName(), animal.getHabitat(), new Iq(animal.getIq().getIqSize()), animal.getAge(), animal.isPredator(), animal.getFood()));
        }
    }

    private Habitat randomHabitat() {
        Habitat habitat = Habitat.AIR;
        switch (random.nextInt(3)) {
            case 0:
                habitat = Habitat.AIR;
                break;
            case 1:
                habitat = Habitat.LAND;
                break;
            case 2:
                habitat = Habitat.WATER;
                break;
        }
        return habitat;
    }

    private <T> void serialize(List<T> list, Consumer<List<T>> consumer) {
        consumer.accept(list);
    }

    private <R> List<R> deserialize(Supplier<List<R>> supplier) {
        List<R> resList = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            resList = supplier.get();
        }
        return resList;
    }

    @Test
    public void defaultSerializationTest() throws IOException {
        long startTime = System.currentTimeMillis();
        serialize(animals, x -> serializer.defaultSerialize(x, fileTest));
        long endTime = System.currentTimeMillis();
        long serialisedTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        List<Animal> deserializedList = deserialize(() -> serializer.defaultDeserialize(fileTest));
        endTime = System.currentTimeMillis();
        long deserializeTime = endTime - startTime;

        Assert.assertEquals(animals, deserializedList);
        System.out.println("Default:" + "\n" +
                "Serialize time: " + serialisedTime + " ms" + "\n"
                + "Deserialize time: " + deserializeTime + " ms" + "\n"
                + "File size: " + Files.size(Paths.get("fileTest")) + " bytes" + "\n");
    }

    @Test
    public void serializationWithMethodsTest() throws IOException {
        long startTime = System.currentTimeMillis();
        serialize(animalsWithMethods, x -> serializer.serializeWithMethods(x, fileTest));
        long endTime = System.currentTimeMillis();
        long serialisedTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedList = deserialize(() -> serializer.deserializeWithMethods(fileTest));
        endTime = System.currentTimeMillis();
        long deserializeTime = endTime - startTime;

        Assert.assertEquals(animalsWithMethods, deserializedList);
        System.out.println("WithMethods:" + "\n" +
                "Serialize time: " + serialisedTime + " ms" + "\n"
                + "Deserialize time: " + deserializeTime + " ms" + "\n"
                + "File size: " + Files.size(Paths.get("fileTest")) + " bytes" + "\n");
    }

    @Test
    public void serializeWithExternalizableTest() throws IOException {
        long startTime = System.currentTimeMillis();
        serialize(animalsExternalizable, x -> serializer.serializeWithExternalizable(animalsExternalizable, fileTest));
        long endTime = System.currentTimeMillis();
        long serialisedTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedList = deserialize(() -> serializer.deserializeWithExternalizable(fileTest));
        endTime = System.currentTimeMillis();
        long deserializeTime = endTime - startTime;

        Assert.assertEquals(animalsExternalizable, deserializedList);
        System.out.println("WithExternalizable:" + "\n" +
                "Serialize time: " + serialisedTime + " ms" + "\n"
                + "Deserialize time: " + deserializeTime + " ms" + "\n"
                + "File size: " + Files.size(Paths.get("fileTest")) + " bytes" + "\n");
    }

    @Test
    public void customSerializeTest() throws IOException {
        long startTime = System.currentTimeMillis();
        serialize(animals, x -> serializer.customSerialize(x, fileTest));
        long endTime = System.currentTimeMillis();
        long serialisedTime = endTime - startTime;

        startTime = System.currentTimeMillis();
        List<Animal> deserializedList = deserialize(() -> serializer.customDeserialize(fileTest));
        endTime = System.currentTimeMillis();
        long deserializeTime = endTime - startTime;

        Assert.assertEquals(animals, deserializedList);
        System.out.println("Custom:" + "\n" +
                "Serialize time: " + serialisedTime + " ms" + "\n"
                + "Deserialize time: " + deserializeTime + " ms" + "\n"
                + "File size: " + Files.size(Paths.get("fileTest")) + " bytes" + "\n");
    }
}
