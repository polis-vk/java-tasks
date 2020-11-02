package ru.mail.polis.homework.io.objects;


import java.io.Serializable;
import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal implements Serializable {

  public enum Habitation {
    WATER,
    LAND,
    SOIL
  }

  private Brain brain;
  private List<String> listName;
  private int weight;
  private String name;
  private Habitation habitation;
  private long distanceTraveled;

  public Animal() {

  }

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

  public void setBrain(Brain brain) {
    this.brain = brain;
  }

  public void setListName(List<String> listName) {
    this.listName = listName;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public void setName(String name) {
    this.name = name;
  }

  public void setHabitation(Habitation habitation) {
    this.habitation = habitation;
  }

  public void setDistanceTraveled(long distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
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
}
