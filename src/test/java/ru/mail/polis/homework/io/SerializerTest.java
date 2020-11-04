package ru.mail.polis.homework.io;

import org.junit.After;

import org.junit.Assert;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.Animal;
import ru.mail.polis.homework.io.objects.AnimalExternalizable;
import ru.mail.polis.homework.io.objects.AnimalWithMethods;
import ru.mail.polis.homework.io.objects.ExternalizableInnerDemon;
import ru.mail.polis.homework.io.objects.SerializableInnerDemon;
import ru.mail.polis.homework.io.objects.Serializer;


import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class SerializerTest {
  private final static int AMOUNT = 200;
  private final static String fileName = "./src/test/resources/serializeTest.ser";
  private static Serializer serializer = new Serializer();

  private static List<Animal> justAnimals = new ArrayList<Animal>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new Animal(10 + i,
          "Subj #" + i,
          new SerializableInnerDemon(i % 2 == 0 ? false : true),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          i % 2 == 0 ? false : true));
    }
  }};

  private static List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new AnimalWithMethods(10 + i,
          "Subj #" + i,
          new SerializableInnerDemon(i % 2 == 0 ? false : true),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          i % 2 == 0 ? false : true));
    }
  }};

  private static List<AnimalExternalizable> animalsExtern = new ArrayList<AnimalExternalizable>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new AnimalExternalizable(10 + i,
          "Subj #" + i,
          new ExternalizableInnerDemon(i % 2 == 0 ? false : true),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          i % 2 == 0 ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          i % 2 == 0 ? false : true));
    }
  }};

  @Test
  public void defaultSerializeTest() {
    Path path = Paths.get(fileName);
    long before = System.currentTimeMillis();

    try {
      serializer.defaultSerialize(justAnimals, fileName);
      List<Animal> animals = serializer.defaultDeserialize(fileName);

      long time = System.currentTimeMillis() - before;
      long size = Files.size(path);

      Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());

      printTestInfo("Default Serialize Test", size, time);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithMethodsTest() {
    Path path = Paths.get(fileName);
    long before = System.currentTimeMillis();

    try {
      serializer.serializeWithMethods(animalsWithMethods, fileName);

      List<AnimalWithMethods> animals = serializer.deserializeWithMethods(fileName);

      long time = System.currentTimeMillis() - before;
      long size = Files.size(path);

      Assert.assertArrayEquals(animalsWithMethods.toArray(), animals.toArray());

      printTestInfo("Serialize With Methods Test", size, time);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithExternalizableTest() {
    Path path = Paths.get(fileName);
    long before = System.currentTimeMillis();

    try {
      serializer.serializeWithExternalizable(animalsExtern, fileName);

      List<AnimalExternalizable> animals = serializer.deserializeWithExternalizable(fileName);

      long time = System.currentTimeMillis() - before;
      long size = Files.size(path);

      Assert.assertArrayEquals(animalsExtern.toArray(), animals.toArray());

      printTestInfo("Serialize With Externalizable Test", size, time);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void customSerializeTest() {
    Path path = Paths.get(fileName);
    long before = System.currentTimeMillis();

    try {
      serializer.customSerialize(justAnimals, fileName);

      List<Animal> animals = serializer.customDeserialize(fileName);


      long time = System.currentTimeMillis() - before;
      long size = Files.size(path);

      Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());
      printTestInfo("Custom Serialize Test", size, time);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @After
  public void deleteFile() {
    try {
      Files.deleteIfExists(Paths.get(fileName));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printTestInfo(String testName, long size, long time) {
    System.out.println(testName + "\n"
        + "Animal amount: " + AMOUNT + "\n"
        + "File size in bytes:  " + size + "\n"
        + "Time in millis: " + time + "\n");
  }
}
