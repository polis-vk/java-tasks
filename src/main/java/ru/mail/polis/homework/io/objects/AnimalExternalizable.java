package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {
  public AnimalExternalizable(int age,
                              String name,
                              InnerDemon demon,
                              List<String> friendNames,
                              Diet diet,
                              boolean isAlive) {
    super(age, name, demon, friendNames, diet, isAlive);
  }

  public AnimalExternalizable() {
    super();
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(age);
    out.writeUTF(name);
    out.writeObject(demon);
    out.writeObject(friendNames);
    out.writeObject(diet);
    out.writeBoolean(isAlive);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    age = in.readInt();
    name = in.readUTF();
    demon = (InnerDemon) in.readObject();
    friendNames = (List<String>) in.readObject();
    diet = (Diet) in.readObject();
    isAlive = in.readBoolean();
  }
}
