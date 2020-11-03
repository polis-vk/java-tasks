package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class SerializerTest {

  private final int numberToSerialize = 100;

  private final List<Animal> animalList = new ArrayList<>();
  private final List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
  private final List<AnimalExternalizable> animalExternalizableList = new ArrayList<>();
  private final Serializer serializer = new Serializer();

  private final String fileForTest = "test.txt";

  public SerializerTest() {
    animalList.add(new Animal(new Brain(10), Arrays.asList("bear", "tiger"), 22, "Frek", Animal.Habitation.LAND, 2222));
    animalList.add(new Animal(new Brain(3), Arrays.asList("lion"), 1, "Frik", Animal.Habitation.LAND, 211));
    animalList.add(new Animal(new Brain(4), Arrays.asList("bear", "tiger", "rabbit"), 20, "Frak", Animal.Habitation.WATER, 11));
    animalList.add(new Animal(new Brain(14), Arrays.asList("dog"), 23, "Frek", Animal.Habitation.SOIL, 333));
    animalList.add(new Animal(new Brain(1), Arrays.asList("cat", "tiger"), 66, "Freek", Animal.Habitation.WATER, 444));
    animalList.add(new Animal(new Brain(20), Arrays.asList("dog", "cat"), 12, "Fruk", Animal.Habitation.LAND, 555));
    animalList.add(new Animal(new Brain(110), Arrays.asList("cat"), 55, "Frok", Animal.Habitation.SOIL, 212));
    animalList.add(new Animal(new Brain(11), Arrays.asList("tiger"), 21, "Frak", Animal.Habitation.SOIL, 33));
    animalList.add(new Animal(new Brain(2), Arrays.asList("elephant"), 77, "Freok", Animal.Habitation.WATER, 414));
    animalList.add(new Animal(new Brain(33), Arrays.asList("tiger", "elephant"), 16, "Freak", Animal.Habitation.LAND, 812));

    for (int i = 0; i < animalList.size(); i++) {
      Animal animal = animalList.get(i);

      Brain brain = animal.getBrain();
      List<String> listName = animal.getListName();
      int weight = animal.getWeight();
      String name = animal.getName();
      Animal.Habitation habitation = animal.getHabitation();
      long distanceTraveled = animal.getDistanceTraveled();

      animalWithMethodsList.add(new AnimalWithMethods(brain, listName, weight, name, habitation, distanceTraveled));
      animalExternalizableList.add(new AnimalExternalizable(brain, listName, weight, name, habitation, distanceTraveled));
    }

  }

  @Test
  public void defaultSerializationTest() {

    try {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.defaultSerialize(animalList, fileForTest);
      }
      long endTime = System.currentTimeMillis();
      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<Animal> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.defaultDeserialize(fileForTest);
      }
      endTime = System.currentTimeMillis();
      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForTest));

      Assert.assertEquals(animalList, deserializedList);
      printInfo(serialisedTime, deserializeTime, fileSize);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializationWithMethodsTest() {
    try {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.serializeWithMethods(animalWithMethodsList, fileForTest);
      }
      long endTime = System.currentTimeMillis();
      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();

      List<AnimalWithMethods> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.deserializeWithMethods(fileForTest);
      }
      endTime = System.currentTimeMillis();
      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForTest));

      Assert.assertEquals(animalList, deserializedList);

      printInfo(serialisedTime, deserializeTime, fileSize);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithExternalizableTest() {
    try {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.serializeWithExternalizable(animalExternalizableList, fileForTest);
      }
      long endTime = System.currentTimeMillis();
      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<AnimalExternalizable> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.deserializeWithExternalizable(fileForTest);
      }
      endTime = System.currentTimeMillis();
      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForTest));

      Assert.assertEquals(animalList, deserializedList);

      printInfo(serialisedTime, deserializeTime, fileSize);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void customSerializeTest() {
    try {
      long startTime = System.currentTimeMillis();

      for (int i = 0; i < numberToSerialize; i++) {
        serializer.customSerialize(animalList, fileForTest);
      }
      long endTime = System.currentTimeMillis();
      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<Animal> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.customDeserialize(fileForTest);
      }
      endTime = System.currentTimeMillis();
      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForTest));

      Assert.assertEquals(animalList, deserializedList);

      printInfo(serialisedTime, deserializeTime, fileSize);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @After
  public void deleteFile() {
    try {
      Files.delete(Paths.get(fileForTest));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private void printInfo(Long serialisedTime, Long deserializeTime, Long fileSize) {
    System.out.println("Serialize time: " + serialisedTime + "\n" + "Deserialize time: " + deserializeTime + "\n" + "File size:" + fileSize + "\n");
  }
}
