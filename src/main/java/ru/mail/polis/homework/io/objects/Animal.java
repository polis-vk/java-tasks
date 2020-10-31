package ru.mail.polis.homework.io.objects;


import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal {

  public enum Habitation {
    WATER,
    LAND,
    SOIL
  }

  private final Brain brain;
  private final List<String> listName;
  private int weight;
  private final String name;
  private final Habitation habitation;
  private long distanceTraveled;

  public Animal(Brain brain, List<String> listName, int weight, String name, Habitation habitation, long distanceTraveled) {
    this.brain = brain;
    this.listName = listName;
    this.weight = weight;
    this.name = name;
    this.habitation = habitation;
    this.distanceTraveled = distanceTraveled;
  }

  public Brain getBrain() {
    return brain;
  }

  public List<String> getListName() {
    return listName;
  }

  public int getWeight() {
    return weight;
  }

  public String getName() {
    return name;
  }

  public Habitation getHabitation() {
    return habitation;
  }

  public long getDistanceTraveled() {
    return distanceTraveled;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setDistanceTraveled(long distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }
}
