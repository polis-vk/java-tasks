package ru.mail.polis.homework.io;

import org.junit.After;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;
import ru.mail.polis.homework.io.objects.InnerDemon;
import ru.mail.polis.homework.io.objects.Serializer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SerializerTest {
  private final static int AMOUNT = 100;
  private final static String fileName = "./src/test/resources/serializeTest.txt";
  private static Serializer serializer = new Serializer();


  private static List<Animal> justAnimals = new ArrayList<Animal>() {{
    for (int i = 0; i < AMOUNT; i++) {
      add(new Animal(21, i % 2 == 0 ? "Kek" : "Lol", new InnerDemon(i % 2 == 0 ? false : true), new ArrayList<String>() {{
        add("Kek");
        add("lol");
      }}, i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS, i % 2 == 0 ? false : true));
    }
  }};

  private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
    for (int i = 0; i < AMOUNT; i++) {
      add(new AnimalWithMethods(21, i % 2 == 0 ? "Kek" : "Lol", new InnerDemon(i % 2 == 0 ? false : true), new ArrayList<String>() {{
        add("Kek");
        add("lol");
      }}, i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS, i % 2 == 0 ? false : true));
    }
  }};

  private static List<AnimalExternalizable> animalsExtern = new ArrayList<AnimalExternalizable>() {{
    for (int i = 0; i < AMOUNT; i++) {
      add(new AnimalExternalizable(21, i % 2 == 0 ? "Kek" : "Lol", new InnerDemon(i % 2 == 0 ? false : true), new ArrayList<String>() {{
        add("Kek");
        add("lol");
      }}, i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS, i % 2 == 0 ? false : true));
    }
  }};

  @Test
  public void defaultSerializeTest() {
    serializer.defaultSerialize(justAnimals, fileName);

    List<Animal> animals = serializer.defaultDeserialize(fileName);

    Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());
  }

  @Test
  public void serializeWithMethodsTest() {
    serializer.serializeWithMethods(animalsWithMethods, fileName);

    List<AnimalWithMethods> animals = serializer.deserializeWithMethods(fileName);

    Assert.assertArrayEquals(animalsWithMethods.toArray(), animals.toArray());
  }

  @Test
  public void serializeWithExternalizableTest() {
    serializer.serializeWithExternalizable(animalsExtern, fileName);

    List<AnimalExternalizable> animals = serializer.deserializeWithExternalizable(fileName);

    Assert.assertArrayEquals(animalsExtern.toArray(), animals.toArray());
  }

  @Test
  public void customSerializeTest() {
    serializer.customSerialize(justAnimals, fileName);

    List<Animal> animals = serializer.customDeserialize(fileName);

    Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());
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
