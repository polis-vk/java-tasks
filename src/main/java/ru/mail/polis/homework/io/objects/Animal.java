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
  private int age;
  private String name;
  private SerializableInnerDemon demon;
  private List<String> friendNames;
  private Diet diet;
  private boolean isAlive;

  public enum Diet {
    VEGAN,
    MEAT_EATER,
    OMNIVOROUS
  }

  public Animal(int age, String name, SerializableInnerDemon demon, List<String> friendNames, Diet diet, boolean isAlive) {
    this.age = age;
    this.name = name;
    this.demon = demon;
    this.friendNames = friendNames;
    this.diet = diet;
    this.isAlive = isAlive;
  }

  public Animal() {

  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public SerializableInnerDemon getDemon() {
    return demon;
  }

  public void setDemon(SerializableInnerDemon demon) {
    this.demon = demon;
  }

  public List<String> getFriendNames() {
    return friendNames;
  }

  public void setFriendNames(List<String> friendNames) {
    this.friendNames = friendNames;
  }

  public Diet getDiet() {
    return diet;
  }

  public void setDiet(Diet diet) {
    this.diet = diet;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Animal)) return false;
    Animal animal = (Animal) o;
    return getAge() == animal.getAge() &&
        isAlive() == animal.isAlive() &&
        getName().equals(animal.getName()) &&
        getDemon().equals(animal.getDemon()) &&
        getFriendNames().equals(animal.getFriendNames()) &&
        getDiet() == animal.getDiet();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAge(), getName(), getDemon(), getFriendNames(), getDiet(), isAlive());
  }
}
