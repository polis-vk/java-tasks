package ru.mail.polis.homework.io.objects;


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
}
