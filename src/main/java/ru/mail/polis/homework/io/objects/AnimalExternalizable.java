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
public class AnimalExternalizable implements Externalizable {
  private int age;
  private String name;
  private ExternalizableInnerDemon demon;
  private List<String> friendNames;
  private Animal.Diet diet;
  private boolean isAlive;

  public AnimalExternalizable(int age, String name, ExternalizableInnerDemon demon, List<String> friendNames, Animal.Diet diet, boolean isAlive) {
    this.age = age;
    this.name = name;
    this.demon = demon;
    this.friendNames = friendNames;
    this.diet = diet;
    this.isAlive = isAlive;
  }

  public AnimalExternalizable() {

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

  public ExternalizableInnerDemon getDemon() {
    return demon;
  }

  public void setDemon(ExternalizableInnerDemon demon) {
    this.demon = demon;
  }

  public List<String> getFriendNames() {
    return friendNames;
  }

  public void setFriendNames(List<String> friendNames) {
    this.friendNames = friendNames;
  }

  public Animal.Diet getDiet() {
    return diet;
  }

  public void setDiet(Animal.Diet diet) {
    this.diet = diet;
  }

  public boolean isAlive() {
    return isAlive;
  }

  public void setAlive(boolean alive) {
    isAlive = alive;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(age);
    out.writeUTF(name);
    demon.writeExternal(out);
    out.writeObject(friendNames);
    out.writeObject(diet);
    out.writeBoolean(isAlive);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    age = in.readInt();
    name = in.readUTF();
    demon.readExternal(in);
    friendNames = (List<String>) in.readObject();
    diet = (Animal.Diet) in.readObject();
    isAlive = in.readBoolean();
  }
}
