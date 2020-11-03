package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods extends Animal implements Serializable {
  public AnimalWithMethods(int age,
                           String name,
                           InnerDemon demon,
                           List<String> friendNames,
                           Diet diet,
                           boolean isAlive) {
    super(age, name, demon, friendNames, diet, isAlive);
  }

  public AnimalWithMethods() {
  }

  private void writeObject(ObjectOutputStream objectStream) throws IOException {
    objectStream.defaultWriteObject();
  }

  private void readObject(ObjectInputStream objectStream) throws IOException, ClassNotFoundException {
    objectStream.defaultReadObject();
  }
}
