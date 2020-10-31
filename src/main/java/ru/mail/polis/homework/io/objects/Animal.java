package ru.mail.polis.homework.io.objects;


import java.util.List;

/**
 * Класс должен содержать несколько полей с примитивами, строками, энамами и некоторыми сапомисными объектами.
 * Хотя бы один из них должен быть в списке! Например список размеров или список имен.
 * Всего должно быть минимум 6 полей с разными типами.
 * 1 балл
 */
public class Animal {
  protected int age;
  protected String name;
  protected InnerDemon demon;
  protected List<String> friendNames;
  protected Diet diet;
  protected boolean isAlive;

  public enum Diet {
    VEGAN,
    MEAT_EATER,
    OMNIVOROUS
  }

  public Animal(int age, String name, InnerDemon demon, List<String> friendNames, Diet diet, boolean isAlive) {
    this.age = age;
    this.name = name;
    this.demon = demon;
    this.friendNames = friendNames;
    this.diet = diet;
    this.isAlive = isAlive;
  }

  public Animal() {

  }
}
