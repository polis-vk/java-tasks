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
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class SerializerTest {

  private final int numberToSerialize = 10000;

  private final List<Animal> animalList = new ArrayList<>();
  private final List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
  private final List<AnimalExternalizable> animalExternalizableList = new ArrayList<>();
  private final Serializer serializer = new Serializer();
  private final Random random = new Random();

  private final String fileForTest = "test";

  @Before
  public void initializeFields() {

    for (int i = 0; i < numberToSerialize; i++) {
      animalList.add(new Animal(new Brain(i), Arrays.asList("bear" + i, "tiger" + i), 22, "Frek" + i, randomHabitation(), i));
    }

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
    System.out.println("def");
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
    System.out.println("withmeth");
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
    System.out.println("extern");
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
    System.out.println("cus");
    printInfo(serialisedTime, deserializeTime, getFileSize(fileForTest));
  }

  @After
  public void freeRecourses() {
    animalList.clear();
    animalWithMethodsList.clear();
    animalExternalizableList.clear();

    try {
      Files.delete(Paths.get(fileForTest));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  private Habitation randomHabitation() {
    Habitation habitation = Habitation.WATER;
    switch (random.nextInt(3)) {
      case 0:
        habitation = Habitation.WATER;
        break;
      case 1:
        habitation = Habitation.LAND;
        break;
      case 2:
        habitation = Habitation.SOIL;
        break;
    }
    return habitation;
  }

  private <T> void serialize(List<T> list, Consumer<List<T>> consumer) {
    consumer.accept(list);
  }

  private <R> List<R> deserialize(Supplier<List<R>> supplier) {
    return supplier.get();
  }

  private long getFileSize(String fileForTest) {
    long fileSize = 0;
    try {
      fileSize = Files.size(Paths.get(fileForTest));
    } catch (IOException e) {
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
