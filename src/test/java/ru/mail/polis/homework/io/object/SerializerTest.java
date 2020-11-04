package ru.mail.polis.homework.io.object;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SerializerTest {

    private static final int QUANTITY = 100;
    private static final long SEED = 2001;
    private static final Random random = new Random(SEED);
    private static final List<String> foodList = Arrays.asList("mushroom", "animal", "bark", "leaves", "berries");
    private final String fileName = "src/test/java/ru/mail/polis/homework/io/object/serialize";
    Serializer serializer = new Serializer();

    private static final List<Animal> animals = new ArrayList<Animal>() {{
        Random random1 = new Random(SEED);
        Animal animal;
        for (int i = 0; i < QUANTITY; i++) {
            animal = new Animal(random1.nextInt(),
                    String.valueOf(random1.nextInt()),
                    Animal.Habitat.values()[random1.nextInt(2)],
                    Arrays.asList(foodList.get(random1.nextInt(4)), foodList.get(random1.nextInt(4))),
                    random1.nextBoolean(),
                    random1.nextDouble() * 100,
                    new Heart(random1.nextBoolean())
            );
            add(animal);
        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
        Random random1 = new Random(SEED);
        AnimalWithMethods animalWithMethods;
        for (int i = 0; i < QUANTITY; i++) {
            animalWithMethods = new AnimalWithMethods(random1.nextInt(),
                    String.valueOf(random1.nextInt()),
                    AnimalWithMethods.Habitat.values()[random1.nextInt(2)],
                    Arrays.asList(foodList.get(random1.nextInt(4)), foodList.get(random1.nextInt(4))),
                    random1.nextBoolean(),
                    random1.nextDouble() * 100,
                    new Heart(random1.nextBoolean())
            );
            add(animalWithMethods);
        }
    }};

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>() {{
        Random random1 = new Random(SEED);
        AnimalExternalizable animalExternalizable;
        for (int i = 0; i < QUANTITY; i++) {
            animalExternalizable = new AnimalExternalizable(random1.nextInt(),
                    String.valueOf(random1.nextInt()),
                    AnimalExternalizable.Habitat.values()[random1.nextInt(2)],
                    Arrays.asList(foodList.get(random1.nextInt(4)), foodList.get(random1.nextInt(4))),
                    random1.nextBoolean(),
                    random1.nextDouble() * 100,
                    new HeartExternalizable(random1.nextBoolean())
            );
            add(animalExternalizable);
        }
    }};

    @Test
    public void defaultSerializeTest() {
        long startTime = System.currentTimeMillis();
        serializer.defaultSerialize(animals, fileName);
        long endSerialize = System.currentTimeMillis();
        List<Animal> animalsSerializer = serializer.defaultDeserialize(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());

        System.out.println("  Default");
        System.out.println("Serialize wasted time = " + (endSerialize - startTime) + "ms.");
        System.out.println("Deserialize wasted time = " + (endDeserialize - startTime) + "ms.");
        System.out.println("File size = " + fileSize + "kb.");
    }

    @Test
    public void serializeWithExternalizableTest() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, fileName);
        long endSerialize = System.currentTimeMillis();
        List<AnimalExternalizable> animalsSerializer = serializer.deserializeWithExternalizable(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertArrayEquals(animalsExternalizable.toArray(), animalsSerializer.toArray());

        System.out.println("  Externalizable");
        System.out.println("Serialize wasted time = " + (endSerialize - startTime) + "ms.");
        System.out.println("Deserialize wasted time = " + (endDeserialize - startTime) + "ms.");
        System.out.println("File size = " + fileSize + "kb.");
    }

    @Test
    public void serializeWithMethodsTest() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, fileName);
        long endSerialize = System.currentTimeMillis();
        List<AnimalWithMethods> animalsSerializer = serializer.deserializeWithMethods(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertArrayEquals(animalsWithMethods.toArray(), animalsSerializer.toArray());

        System.out.println("  With methods");
        System.out.println("Serialize wasted time = " + (endSerialize - startTime) + "ms.");
        System.out.println("Deserialize wasted time = " + (endDeserialize - startTime) + "ms.");
        System.out.println("File size = " + fileSize + "kb.");
    }

    @Test
    public void customSerialize() {
        long startTime = System.currentTimeMillis();
        serializer.customSerialize(animals, fileName);
        long endSerialize = System.currentTimeMillis();
        List<Animal> animalsSerializer = serializer.customDeserialize(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());

        System.out.println("  Custom");
        System.out.println("Serialize wasted time = " + (endSerialize - startTime) + "ms.");
        System.out.println("Deserialize wasted time = " + (endDeserialize - startTime) + "ms.");
        System.out.println("File size = " + fileSize + "kb.");
    }


    @After
    public void deleteFile() {
        try {
            Files.deleteIfExists(Paths.get(fileName));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
