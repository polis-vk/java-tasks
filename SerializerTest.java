package ru.mail.polis.homework.objects;

import org.junit.Assert;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

public class SerializerTest {
    private final static int COUNT = 100;
    private final static String FILE = "test.bin";
    private static List<Animal> animalList;

    private static List<AnimalWithMethods> animalWithMethodsList;

    private static List<AnimalExternalizable> animalExternalizableList;

    private static List<Animal> animalCustomList;


    private Serializer serializer = new Serializer();

    @BeforeClass
    public static void initLists() {
        animalList = Arrays.asList(new Animal("Dog", true, Animal.NutritionType.MEAT, 4, Arrays.asList("Kolya"), new Heart(14, 88)),
                new Animal("Rabbit", false, Animal.NutritionType.PLANT, 7, Arrays.asList("Marshall"), new Heart(4, 30)),
                new Animal("Cat", false, Animal.NutritionType.MEAT, 3, Arrays.asList("Bat"), new Heart(8, 60)),
                new Animal("Eagle", true, Animal.NutritionType.MEAT, 32, Arrays.asList("Khabib", "Islam", "Umar"), new Heart(70, 100500)),
                new Animal("Monkey", true, Animal.NutritionType.PLANT, 13, Arrays.asList("Donald", "Joe"), new Heart(0, 0)),
                new Animal("Bear", false, Animal.NutritionType.BOTH, 29, Arrays.asList("Boris", "Vladimir", "Dmitry", "Vladimir Again"), new Heart(1337, 1488)),
                new Animal("Crocodile", true, Animal.NutritionType.MEAT, 18, Arrays.asList("Lacoste"), new Heart(1, 2)),
                new Animal("Bat", true, Animal.NutritionType.BOTH, 1, Arrays.asList("Kizaru"), new Heart(228, 228)),
                new Animal("Horse", true, Animal.NutritionType.PLANT, 100, Arrays.asList("Oxxxymiron"), new Heart(1703, 666)),
                new Animal("Donkey", false, Animal.NutritionType.PLANT, 5, Arrays.asList("Aliev"), new Heart(0, -100)));

        animalWithMethodsList = Arrays.asList(new AnimalWithMethods("Dog", true, AnimalWithMethods.NutritionType.MEAT, 4, Arrays.asList("Kolya"), new Heart(14, 88)),
                new AnimalWithMethods("Rabbit", false, AnimalWithMethods.NutritionType.PLANT, 7, Arrays.asList("Marshall"), new Heart(4, 30)),
                new AnimalWithMethods("Cat", false, AnimalWithMethods.NutritionType.MEAT, 3, Arrays.asList("Bat"), new Heart(8, 60)),
                new AnimalWithMethods("Eagle", true, AnimalWithMethods.NutritionType.MEAT, 32, Arrays.asList("Khabib", "Islam", "Umar"), new Heart(70, 100500)),
                new AnimalWithMethods("Monkey", true, AnimalWithMethods.NutritionType.PLANT, 13, Arrays.asList("Donald", "Joe"), new Heart(0, 0)),
                new AnimalWithMethods("Bear", false, AnimalWithMethods.NutritionType.BOTH, 29, Arrays.asList("Boris", "Vladimir", "Dmitry", "Vladimir Again"), new Heart(1337, 1488)),
                new AnimalWithMethods("Crocodile", true, AnimalWithMethods.NutritionType.MEAT, 18, Arrays.asList("Lacoste"), new Heart(1, 2)),
                new AnimalWithMethods("Bat", true, AnimalWithMethods.NutritionType.BOTH, 1, Arrays.asList("Kizaru"), new Heart(228, 228)),
                new AnimalWithMethods("Horse", true, AnimalWithMethods.NutritionType.PLANT, 100, Arrays.asList("Oxxxymiron"), new Heart(1703, 666)),
                new AnimalWithMethods("Donkey", false, AnimalWithMethods.NutritionType.PLANT, 5, Arrays.asList("Aliev"), new Heart(0, -100)));

        animalExternalizableList = Arrays.asList(new AnimalExternalizable("Dog", true, AnimalExternalizable.NutritionType.MEAT, 4, Arrays.asList("Kolya"), new Heart(14, 88)),
                new AnimalExternalizable("Rabbit", false, AnimalExternalizable.NutritionType.PLANT, 7, Arrays.asList("Marshall"), new Heart(4, 30)),
                new AnimalExternalizable("Cat", false, AnimalExternalizable.NutritionType.MEAT, 3, Arrays.asList("Bat"), new Heart(8, 60)),
                new AnimalExternalizable("Eagle", true, AnimalExternalizable.NutritionType.MEAT, 32, Arrays.asList("Khabib", "Islam", "Umar"), new Heart(70, 100500)),
                new AnimalExternalizable("Monkey", true, AnimalExternalizable.NutritionType.PLANT, 13, Arrays.asList("Donald", "Joe"), new Heart(0, 0)),
                new AnimalExternalizable("Bear", false, AnimalExternalizable.NutritionType.BOTH, 29, Arrays.asList("Boris", "Vladimir", "Dmitry", "Vladimir Again"), new Heart(1337, 1488)),
                new AnimalExternalizable("Crocodile", true, AnimalExternalizable.NutritionType.MEAT, 18, Arrays.asList("Lacoste"), new Heart(1, 2)),
                new AnimalExternalizable("Bat", true, AnimalExternalizable.NutritionType.BOTH, 1, Arrays.asList("Kizaru"), new Heart(228, 228)),
                new AnimalExternalizable("Horse", true, AnimalExternalizable.NutritionType.PLANT, 100, Arrays.asList("Oxxxymiron"), new Heart(1703, 666)),
                new AnimalExternalizable("Donkey", false, AnimalExternalizable.NutritionType.PLANT, 5, Arrays.asList("Aliev"), new Heart(0, -100)));

        animalCustomList = animalList;
    }



    @Test
    public void defaultTest()  {
         test(x -> serializer.defaultSerialize(x, FILE), () -> serializer.defaultDeserialize(FILE), animalList);
    }

    @Test
    public void withMethodsTest()  {
        test(x -> serializer.serializeWithMethods(x, FILE), () -> serializer.deserializeWithMethods(FILE), animalWithMethodsList);
    }

    @Test
    public void externalizableTest()  {
        test(x -> serializer.serializeWithExternalizable(x, FILE), () -> serializer.deserializeWithExternalizable(FILE), animalExternalizableList);
    }

    @Test
    public void customTest()  {
        test(x -> serializer.customSerialize(x, FILE), () -> serializer.customDeserialize(FILE), animalCustomList);
    }



    private <E> void test(Consumer<List<E>> ser, Supplier<List<E>> deser, List<E> source) {
        List<E> before = new ArrayList<>();
        for (int i = 0; i < COUNT; i++) {
            before.addAll(source);
        }
        long serializeTimeStart = System.currentTimeMillis();
        ser.accept(before);
        long serializeTime = System.currentTimeMillis() - serializeTimeStart;

        long deserializeTimeStart = System.currentTimeMillis();
        List<E> after = deser.get();
        long deserializeTime = System.currentTimeMillis() - deserializeTimeStart;

        Assert.assertEquals(before, after);

        printInfo(serializeTime, deserializeTime, FILE);
    }

    private void printInfo(long serializeTime, long deserializeTime, String file) {
        long size = 0;
        try {
            size = Files.size(Paths.get(file));
        } catch (IOException e) {
            e.printStackTrace();
        }
        System.out.println("\n Serialize time: " + serializeTime + "\n Deserialize time: " + deserializeTime + "\n File size: " + size);
    }

}
