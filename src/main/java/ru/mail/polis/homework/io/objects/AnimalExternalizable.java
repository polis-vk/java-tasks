package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {

  public AnimalExternalizable(Brain brain, List<String> listName, int weight, String name, Habitation habitation, long distanceTraveled) {
    super(brain, listName, weight, name, habitation, distanceTraveled);
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {

  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {

  }

}