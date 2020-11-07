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
import java.util.Random;

public class SerializerTest {
  private final static int AMOUNT = 2000;
  private final static String fileName = "./src/test/resources/serializeTest.ser";
  private static final Serializer serializer = new Serializer();
  private static final Random rand = new Random();
  private static final List<Animal> justAnimals = new ArrayList<Animal>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new Animal(10 + i,
          "Subj #" + i,
          new SerializableInnerDemon(rand.nextBoolean()),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          rand.nextBoolean() ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          rand.nextBoolean()));
    }
  }};

  private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<AnimalWithMethods>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new AnimalWithMethods(10 + i,
          "Subj #" + i,
          new SerializableInnerDemon(rand.nextBoolean()),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          rand.nextBoolean() ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          rand.nextBoolean()));
    }
  }};

  private static final List<AnimalExternalizable> animalsExtern = new ArrayList<AnimalExternalizable>() {{
    for (int i = 0; i < AMOUNT; i++) {
      int finalI = i;
      add(new AnimalExternalizable(10 + i,
          "Subj #" + i,
          new ExternalizableInnerDemon(rand.nextBoolean()),
          new ArrayList<String>() {{
            add("Friend #" + finalI);
            add("Friend #" + finalI + 1);
          }},
          rand.nextBoolean() ? Animal.Diet.MEAT_EATER : Animal.Diet.OMNIVOROUS,
          rand.nextBoolean()));
    }
  }};

  @Test
  public void defaultSerializeTest() {
    Path path = Paths.get(fileName);

    try {
      long beforeSerialization = System.currentTimeMillis();
      serializer.defaultSerialize(justAnimals, fileName);
      long serializationTime = System.currentTimeMillis() - beforeSerialization;

      long beforeDeserialization = System.currentTimeMillis();
      List<Animal> animals = serializer.defaultDeserialize(fileName);
      long deserializationTime = System.currentTimeMillis() - beforeDeserialization;

      long size = Files.size(path);

      Assert.assertEquals(justAnimals, animals);

      printTestInfo("Default Serialize Test", size, serializationTime, deserializationTime);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithMethodsTest() {
    Path path = Paths.get(fileName);

    try {
      long beforeSerialization = System.currentTimeMillis();
      serializer.serializeWithMethods(animalsWithMethods, fileName);
      long serializationTime = System.currentTimeMillis() - beforeSerialization;

      long beforeDeserialization = System.currentTimeMillis();
      List<AnimalWithMethods> animals = serializer.deserializeWithMethods(fileName);
      long deserializationTime = System.currentTimeMillis() - beforeDeserialization;

      long size = Files.size(path);

      Assert.assertEquals(animalsWithMethods, animals);

      printTestInfo("Serialize With Methods Test", size, serializationTime, deserializationTime);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithExternalizableTest() {
    Path path = Paths.get(fileName);

    try {
      long beforeSerialization = System.currentTimeMillis();
      serializer.serializeWithExternalizable(animalsExtern, fileName);
      long serializationTime = System.currentTimeMillis() - beforeSerialization;

      long beforeDeserialization = System.currentTimeMillis();
      List<AnimalExternalizable> animals = serializer.deserializeWithExternalizable(fileName);
      long deserializationTime = System.currentTimeMillis() - beforeDeserialization;

      long size = Files.size(path);

      Assert.assertEquals(animalsExtern, animals);

      printTestInfo("Serialize With Externalizable Test", size, serializationTime, deserializationTime);
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void customSerializeTest() {
    Path path = Paths.get(fileName);

    try {
      long beforeSerialization = System.currentTimeMillis();
      serializer.customSerialize(justAnimals, fileName);
      long serializationTime = System.currentTimeMillis() - beforeSerialization;

      long beforeDeserialization = System.currentTimeMillis();
      List<Animal> animals = serializer.customDeserialize(fileName);
      long deserializationTime = System.currentTimeMillis() - beforeDeserialization;

      long size = Files.size(path);

      Assert.assertArrayEquals(justAnimals.toArray(), animals.toArray());
      printTestInfo("Custom Serialize Test", size, serializationTime, deserializationTime);
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

  private void printTestInfo(String testName, long size, long serializationTime, long deserializationTime) {
    System.out.println(
        testName + "\n"
            + "Animal amount: " + AMOUNT + "\n"
            + "File size in kb:  " + size / 1024 + "\n"
            + "Serialization time in millis: " + serializationTime + "\n"
            + "Deserialization Time in millis: " + deserializationTime + "\n"
    );
  }
}
