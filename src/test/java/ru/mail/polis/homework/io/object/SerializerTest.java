package ru.mail.polis.homework.io.object;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class SerializerTest {

    private static final int QUANTITY = 100;
    private static final Random random = new Random();
    private static final List<String> foodList = Arrays.asList("mushroom", "animal", "bark", "leaves", "berries");
    private final String fileName = "src/test/java/ru/mail/polis/homework/io/object/serialize";
    Serializer serializer = new Serializer();

    private static final List<Animal> animals = new ArrayList<Animal>() {{
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
        AnimalWithMethods animalWithMethods;
        for (int i = 0; i < QUANTITY; i++) {
            animalWithMethods = new AnimalWithMethods(random.nextInt(),
                    String.valueOf(random.nextInt()),
                    AnimalWithMethods.Habitat.values()[random.nextInt(2)],
                    Arrays.asList(foodList.get(random.nextInt(4)), foodList.get(random.nextInt(4))),
                    random.nextBoolean(),
                    random.nextDouble() * 100,
                    new Heart(random.nextBoolean())
            );
            add(animalWithMethods);
        }
    }};

    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<AnimalExternalizable>() {{
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
        serializer.defaultSerialize(animals, fileName);

        List<Animal> animalsSerializer = serializer.defaultDeserialize(fileName);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());
    }

    @Test
    public void serializeWithExternalizableTest() {
        serializer.serializeWithExternalizable(animalsExternalizable, fileName);

        List<AnimalExternalizable> animalsSerializer = serializer.deserializeWithExternalizable(fileName);

        Assert.assertArrayEquals(animalsExternalizable.toArray(), animalsSerializer.toArray());
    }

    @Test
    public void serializeWithMethodsTest() {
        serializer.serializeWithMethods(animalsWithMethods, fileName);

        List<AnimalWithMethods> animalsSerializer = serializer.deserializeWithMethods(fileName);

        Assert.assertArrayEquals(animalsWithMethods.toArray(), animalsSerializer.toArray());
    }

    @Test
    public void customSerialize() throws IOException {
        serializer.customSerialize(animals, fileName);

        List<Animal> animalsSerializer = serializer.customDeserialize(fileName);

        Assert.assertArrayEquals(animals.toArray(), animalsSerializer.toArray());
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
