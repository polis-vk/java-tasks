package ru.mail.polis.homework.io.objects;

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

  private final List<Animal> animalList;
  private final List<AnimalWithMethods> animalWithMethodsList;
  private final List<AnimalExternalizable> animalExternalizableList;
  private final Serializer serializer = new Serializer();

  public SerializerTest() {
    animalList = new ArrayList<>();
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

    animalWithMethodsList = new ArrayList<>();
    animalExternalizableList = new ArrayList<>();

    animalWithMethodsList.add(new AnimalWithMethods(new Brain(10), Arrays.asList("bear", "tiger"), 22, "Frek", Animal.Habitation.LAND, 2222));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(3), Arrays.asList("lion"), 1, "Frik", Animal.Habitation.LAND, 211));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(4), Arrays.asList("bear", "tiger", "rabbit"), 20, "Frak", Animal.Habitation.WATER, 11));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(14), Arrays.asList("dog"), 23, "Frek", Animal.Habitation.SOIL, 333));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(1), Arrays.asList("cat", "tiger"), 66, "Freek", Animal.Habitation.WATER, 444));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(20), Arrays.asList("dog", "cat"), 12, "Fruk", Animal.Habitation.LAND, 555));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(110), Arrays.asList("cat"), 55, "Frok", Animal.Habitation.SOIL, 212));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(11), Arrays.asList("tiger"), 21, "Frak", Animal.Habitation.SOIL, 33));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(2), Arrays.asList("elephant"), 77, "Freok", Animal.Habitation.WATER, 414));
    animalWithMethodsList.add(new AnimalWithMethods(new Brain(33), Arrays.asList("tiger", "elephant"), 16, "Freak", Animal.Habitation.LAND, 812));

    animalExternalizableList.add(new AnimalExternalizable(new Brain(10), Arrays.asList("bear", "tiger"), 22, "Frek", Animal.Habitation.LAND, 2222));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(3), Arrays.asList("lion"), 1, "Frik", Animal.Habitation.LAND, 211));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(4), Arrays.asList("bear", "tiger", "rabbit"), 20, "Frak", Animal.Habitation.WATER, 11));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(14), Arrays.asList("dog"), 23, "Frek", Animal.Habitation.SOIL, 333));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(1), Arrays.asList("cat", "tiger"), 66, "Freek", Animal.Habitation.WATER, 444));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(20), Arrays.asList("dog", "cat"), 12, "Fruk", Animal.Habitation.LAND, 555));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(110), Arrays.asList("cat"), 55, "Frok", Animal.Habitation.SOIL, 212));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(11), Arrays.asList("tiger"), 21, "Frak", Animal.Habitation.SOIL, 33));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(2), Arrays.asList("elephant"), 77, "Freok", Animal.Habitation.WATER, 414));
    animalExternalizableList.add(new AnimalExternalizable(new Brain(33), Arrays.asList("tiger", "elephant"), 16, "Freak", Animal.Habitation.LAND, 812));
  }

  @Test
  public void defaultSerializationTest() {
    String fileForFirstTest = "test1.txt";

    try {
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.defaultSerialize(animalList, fileForFirstTest);
      }
      long endTime = System.currentTimeMillis();

      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<Animal> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.defaultDeserialize(fileForFirstTest);
      }
      endTime = System.currentTimeMillis();

      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForFirstTest));

      compareFields(animalList, deserializedList);

      System.out.println("Serialize time: " + serialisedTime + "\n" + "Deserialize time: " + deserializeTime + "\n" + "File size:" + fileSize);
    }
    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializationWithMethodsTest() {
    try {
      String fileForSecondTest = "test2.txt";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.serializeWithMethods(animalWithMethodsList, fileForSecondTest);
      }
      long endTime = System.currentTimeMillis();

      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();

      List<AnimalWithMethods> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.deserializeWithMethods(fileForSecondTest);
      }
      endTime = System.currentTimeMillis();

      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForSecondTest));

      compareFields(animalList, deserializedList);

      System.out.println("Serialize time: " + serialisedTime + "\n" + "Deserialize time: " + deserializeTime + "\n" + "File size:" + fileSize);
    }

    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void serializeWithExternalizableTest() {
    try {
      String fileForThirdTest = "test3.txt";
      long startTime = System.currentTimeMillis();
      for (int i = 0; i < numberToSerialize; i++) {
        serializer.serializeWithExternalizable(animalExternalizableList, fileForThirdTest);
      }
      long endTime = System.currentTimeMillis();

      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<AnimalExternalizable> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.deserializeWithExternalizable(fileForThirdTest);
      }
      endTime = System.currentTimeMillis();

      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForThirdTest));

      compareFields(animalList, deserializedList);

      System.out.println("Serialize time: " + serialisedTime + "\n" + "Deserialize time: " + deserializeTime + "\n" + "File size:" + fileSize);
    }

    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  @Test
  public void customSerializeTest() {
    try {
      String fileForThirdTest = "test4.txt";
      long startTime = System.currentTimeMillis();

      for (int i = 0; i < numberToSerialize; i++) {
        serializer.customSerialize(animalList, fileForThirdTest);
      }
      long endTime = System.currentTimeMillis();

      long serialisedTime = endTime - startTime;

      startTime = System.currentTimeMillis();
      List<Animal> deserializedList = new ArrayList<>();
      for (int i = 0; i < numberToSerialize; i++) {
        deserializedList = serializer.customDeserialize(fileForThirdTest);
      }
      endTime = System.currentTimeMillis();

      long deserializeTime = endTime - startTime;

      long fileSize = Files.size(Paths.get(fileForThirdTest));

      compareFields(animalList, deserializedList);

      System.out.println("Serialize time: " + serialisedTime + "\n" + "Deserialize time: " + deserializeTime + "\n" + "File size:" + fileSize);
    }

    catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
  }

  private void compareFields(List<Animal> startList, List<? extends Animal> deserializedList) {
    for (int i = 0; i < startList.size(); i++) {
      Assert.assertEquals(startList.get(i).getBrain().getSize(), deserializedList.get(i).getBrain().getSize());
      Assert.assertEquals(startList.get(i).getListName(), deserializedList.get(i).getListName());
      Assert.assertEquals(startList.get(i).getWeight(), deserializedList.get(i).getWeight());
      Assert.assertEquals(startList.get(i).getName(), deserializedList.get(i).getName());
      Assert.assertEquals(startList.get(i).getHabitation(), deserializedList.get(i).getHabitation());
      Assert.assertEquals(startList.get(i).getDistanceTraveled(), deserializedList.get(i).getDistanceTraveled());
    }
  }
}
