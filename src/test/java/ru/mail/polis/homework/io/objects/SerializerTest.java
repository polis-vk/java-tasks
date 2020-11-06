package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SerializerTest {

  private final int numberToSerialize = 1000;

  private final List<Animal> animalList = new ArrayList<>();
  private final List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
  private final List<AnimalExternalizable> animalExternalizableList = new ArrayList<>();
  private final Serializer serializer = new Serializer();

  private final String fileForTest = "test";

  @Before
  public void initializeFields() {
    animalList.add(new Animal(new Brain(10), Arrays.asList("bear", "tiger"), 22, "Frek", Habitation.LAND, 2222));
    animalList.add(new Animal(new Brain(3), Arrays.asList("lion"), 1, "Frik", Habitation.LAND, 211));
    animalList.add(new Animal(new Brain(4), Arrays.asList("bear", "tiger", "rabbit"), 20, "Frak", Habitation.WATER, 11));
    animalList.add(new Animal(new Brain(14), Arrays.asList("dog"), 23, "Frek", Habitation.SOIL, 333));
    animalList.add(new Animal(new Brain(1), Arrays.asList("cat", "tiger"), 66, "Freek", Habitation.WATER, 444));
    animalList.add(new Animal(new Brain(20), Arrays.asList("dog", "cat"), 12, "Fruk", Habitation.LAND, 555));
    animalList.add(new Animal(new Brain(110), Arrays.asList("cat"), 55, "Frok", Habitation.SOIL, 212));
    animalList.add(new Animal(new Brain(11), Arrays.asList("tiger"), 21, "Frak", Habitation.SOIL, 33));
    animalList.add(new Animal(new Brain(2), Arrays.asList("elephant"), 77, "Freok", Habitation.WATER, 414));
    animalList.add(new Animal(new Brain(33), Arrays.asList("tiger", "elephant"), 16, "Freak", Habitation.LAND, 812));

    for (Animal animal : animalList) {
      animalWithMethodsList.add(new AnimalWithMethods(animal.getBrain(), animal.getListName(), animal.getWeight(), animal.getName(), animal.getHabitation().toString(), animal.getDistanceTraveled()));
      animalExternalizableList.add(new AnimalExternalizable(new Brain(animal.getBrain().getSize()), animal.getListName(), animal.getWeight(), animal.getName(), animal.getHabitation().toString(), animal.getDistanceTraveled()));
    }
  }

  @Test
  public void defaultSerializationTest() {

    long startTime = System.currentTimeMillis();
    serialize(animalList, x -> serializer.defaultSerialize(x, fileForTest));
    long endTime = System.currentTimeMillis();
    long serialisedTime = endTime - startTime;

    startTime = System.currentTimeMillis();
    List<Animal> deserializedList = deserialize(() -> serializer.defaultDeserialize(fileForTest));
    endTime = System.currentTimeMillis();
    long deserializeTime = endTime - startTime;

    Assert.assertEquals(animalList, deserializedList);
    printInfo(serialisedTime, deserializeTime, getFileSize(fileForTest));
  }

  @Test
  public void serializationWithMethodsTest() {
    long startTime = System.currentTimeMillis();
    serialize(animalWithMethodsList, x -> serializer.serializeWithMethods(x, fileForTest));
    long endTime = System.currentTimeMillis();
    long serialisedTime = endTime - startTime;

    startTime = System.currentTimeMillis();

    List<AnimalWithMethods> deserializedList = deserialize(() -> serializer.deserializeWithMethods(fileForTest));
    endTime = System.currentTimeMillis();
    long deserializeTime = endTime - startTime;

    Assert.assertEquals(animalWithMethodsList, deserializedList);
    printInfo(serialisedTime, deserializeTime, getFileSize(fileForTest));
  }

  @Test
  public void serializeWithExternalizableTest() {
    long startTime = System.currentTimeMillis();
    serialize(animalExternalizableList, x -> serializer.serializeWithExternalizable(animalExternalizableList, fileForTest));
    long endTime = System.currentTimeMillis();
    long serialisedTime = endTime - startTime;

    startTime = System.currentTimeMillis();
    List<AnimalExternalizable> deserializedList = deserialize(() -> serializer.deserializeWithExternalizable(fileForTest));

    endTime = System.currentTimeMillis();
    long deserializeTime = endTime - startTime;

    Assert.assertEquals(animalExternalizableList, deserializedList);
    printInfo(serialisedTime, deserializeTime, getFileSize(fileForTest));
  }

  @Test
  public void customSerializeTest() {

    long startTime = System.currentTimeMillis();
    serialize(animalList, x -> serializer.customSerialize(x, fileForTest));
    long endTime = System.currentTimeMillis();
    long serialisedTime = endTime - startTime;

    startTime = System.currentTimeMillis();
    List<Animal> deserializedList = deserialize(() -> serializer.customDeserialize(fileForTest));
    endTime = System.currentTimeMillis();
    long deserializeTime = endTime - startTime;

    Assert.assertEquals(animalList, deserializedList);
    printInfo(serialisedTime, deserializeTime, getFileSize(fileForTest));
  }

  @After
  public void freeRecourses() {
    animalList.clear();
    animalWithMethodsList.clear();
    animalExternalizableList.clear();

    try {
      Files.delete(Paths.get(fileForTest));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
  }

  private <T> void serialize(List<T> list, Consumer<List<T>> consumer) {
    for (int i = 0; i < numberToSerialize; i++) {
      consumer.accept(list);
    }
  }

  private <R> List<R> deserialize(Supplier<List<R>> supplier) {
    List<R> resList = new ArrayList<>();
    for (int i = 0; i < numberToSerialize; i++) {
      resList = supplier.get();
    }
    return resList;
  }

  private long getFileSize(String fileForTest) {
    long fileSize = 0;
    try {
      fileSize = Files.size(Paths.get(fileForTest));
    }
    catch (IOException e) {
      e.printStackTrace();
    }
    return fileSize;
  }

  private void printInfo(Long serialisedTime, Long deserializeTime, Long fileSize) {
    System.out.println("Serialize time: " + serialisedTime + " ms" + "\n"
            + "Deserialize time: " + deserializeTime + " ms" + "\n"
            + "File size:" + fileSize + " bytes" + "\n");
  }
}
