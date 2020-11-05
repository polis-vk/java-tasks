package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

  private final Brain brain;
  private final List<String> listName;
  private final int weight;
  private final String name;
  private final Habitation habitation;
  private final long distanceTraveled;

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

  @Override
  public String toString() {
    return "Animal{" +
            "brain=" + brain +
            ", listName=" + listName +
            ", weight=" + weight +
            ", name='" + name + '\'' +
            ", habitation=" + habitation +
            ", distanceTraveled=" + distanceTraveled +
            '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Animal)) return false;
    Animal animal = (Animal) o;
    return getWeight() == animal.getWeight() &&
            getDistanceTraveled() == animal.getDistanceTraveled() &&
            getBrain().equals(animal.getBrain()) &&
            getListName().equals(animal.getListName()) &&
            getName().equals(animal.getName()) &&
            getHabitation() == animal.getHabitation();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getBrain(), getListName(), getWeight(), getName(), getHabitation(), getDistanceTraveled());
  }
}
