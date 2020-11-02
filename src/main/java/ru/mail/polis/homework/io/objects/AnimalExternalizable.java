package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {

  public AnimalExternalizable() {

  }

  public AnimalExternalizable(Brain brain, List<String> listName, int weight, String name, Habitation habitation, long distanceTraveled) {
    super(brain, listName, weight, name, habitation, distanceTraveled);
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeObject(getBrain());
    out.writeObject(getListName());
    out.writeInt(getWeight());
    out.writeUTF(getName());
    out.writeObject(getHabitation());
    out.writeLong(getDistanceTraveled());
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    setBrain((Brain) in.readObject());
    setListName((List<String>) in.readObject());
    setWeight(in.readInt());
    setName(in.readUTF());
    setHabitation((Habitation) in.readObject());
    setDistanceTraveled(in.readLong());
  }

}