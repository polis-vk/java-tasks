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
import java.util.logging.Level;
import java.util.logging.Logger;

public class SerializerTest {

    private static final int QUANTITY = 10000;
    private static final long SEED = 2001;
    private static Random random;
    private static final List<String> foodList = Arrays.asList("mushroom", "animal", "bark", "leaves", "berries");
    private final String fileName = "src/test/java/ru/mail/polis/homework/io/object/serialize";
    Serializer serializer = new Serializer();
    Logger log = Logger.getLogger(SerializerTest.class.getName());

    private static final List<Animal> animals = new ArrayList<Animal>() {{
        random = new Random(SEED);
        Animal animal;
        for (int i = 0; i < QUANTITY; i++) {
            animal = new Animal(random.nextInt(),
                    String.valueOf(random.nextInt()),
                    Animal.Habitat.values()[random.nextInt(2)],
                    Arrays.asList(foodList.get(random.nextInt(4)), foodList.get(random.nextInt(4))),
                    random.nextBoolean(),
                    random.nextDouble() * 100,
                    new Heart(random.nextBoolean())
            );
            add(animal);
        }
    }};

    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
        random = new Random(SEED);
        AnimalWithMethods animalWithMethods;
        for (int i = 0; i < QUANTITY; i++) {
            animalWithMethods = new AnimalWithMethods(random.nextInt(),
                    String.valueOf(random.nextInt()),
                    AnimalWithMethods.Habitat.values()[random.nextInt(2)],
                    Arrays.asList(foodList.get(random.nextInt(4)), foodList.get(random.nextInt(4))),
                    random.nextBoolean(),
                    random.nextDouble() * 100,
                    new HeartWithMethod(random.nextBoolean())
            );
            add(animalWithMethods);
        }
    }};

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>() {{
        random = new Random(SEED);
        AnimalExternalizable animalExternalizable;
        for (int i = 0; i < QUANTITY; i++) {
            animalExternalizable = new AnimalExternalizable(random.nextInt(),
                    String.valueOf(random.nextInt()),
                    AnimalExternalizable.Habitat.values()[random.nextInt(2)],
                    Arrays.asList(foodList.get(random.nextInt(4)), foodList.get(random.nextInt(4))),
                    random.nextBoolean(),
                    random.nextDouble() * 100,
                    new HeartExternalizable(random.nextBoolean())
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

        Assert.assertEquals(animals, animalsSerializer);

        log.log(Level.INFO, "Default: Serialize wasted time = " + (endSerialize - startTime) + "ms; " +
                "Deserialize wasted time = " + (endDeserialize - endSerialize) + "ms; " +
                "File size = " + fileSize + "kb.");
    }

    @Test
    public void serializeWithExternalizableTest() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, fileName);
        long endSerialize = System.currentTimeMillis();
        List<AnimalExternalizable> animalsSerializer = serializer.deserializeWithExternalizable(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertEquals(animalsExternalizable, animalsSerializer);

        log.log(Level.INFO, "Externalize: Serialize wasted time = " + (endSerialize - startTime) + "ms; " +
                "Deserialize wasted time = " + (endDeserialize - endSerialize) + "ms; " +
                "File size = " + fileSize + "kb.");
    }

    @Test
    public void serializeWithMethodsTest() {
        long startTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, fileName);
        long endSerialize = System.currentTimeMillis();
        List<AnimalWithMethods> animalsSerializer = serializer.deserializeWithMethods(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertEquals(animalsWithMethods, animalsSerializer);

        log.log(Level.INFO, "With method: Serialize wasted time = " + (endSerialize - startTime) + "ms; " +
                "Deserialize wasted time = " + (endDeserialize - endSerialize) + "ms; " +
                "File size = " + fileSize + "kb.");
    }

    @Test
    public void customSerialize() {
        long startTime = System.currentTimeMillis();
        serializer.customSerialize(animals, fileName);
        long endSerialize = System.currentTimeMillis();
        List<Animal> animalsSerializer = serializer.customDeserialize(fileName);
        long endDeserialize = System.currentTimeMillis();
        double fileSize = (double) new File(fileName).length() / (1024);

        Assert.assertEquals(animals, animalsSerializer);

        log.log(Level.INFO, "Custom: Serialize wasted time = " + (endSerialize - startTime) + "ms; " +
                "Deserialize wasted time = " + (endDeserialize - endSerialize) + "ms; " +
                "File size = " + fileSize + "kb.");
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
