package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
  private int age;
  private String name;
  private SerializableInnerDemon demon;
  private List<String> friendNames;
  private Animal.Diet diet;
  private boolean isAlive;

  public AnimalWithMethods(int age, String name, SerializableInnerDemon demon, List<String> friendNames, Animal.Diet diet, boolean isAlive) {
    this.age = age;
    this.name = name;
    this.demon = demon;
    this.friendNames = friendNames;
    this.diet = diet;
    this.isAlive = isAlive;
  }

  public AnimalWithMethods() {

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

  private void writeObject(ObjectOutputStream objectStream) throws IOException {
    objectStream.writeInt(age);
    objectStream.writeUTF(name);
    objectStream.writeObject(demon);
    objectStream.writeObject(friendNames);
    objectStream.writeObject(diet);
    objectStream.writeBoolean(isAlive);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    age = in.readInt();
    name = in.readUTF();
    demon = (SerializableInnerDemon) in.readObject();
    friendNames = (List<String>) in.readObject();
    diet = (Animal.Diet) in.readObject();
    isAlive = in.readBoolean();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnimalWithMethods)) return false;
    AnimalWithMethods that = (AnimalWithMethods) o;
    return getAge() == that.getAge() &&
        isAlive() == that.isAlive() &&
        getName().equals(that.getName()) &&
        getDemon().equals(that.getDemon()) &&
        getFriendNames().equals(that.getFriendNames()) &&
        getDiet() == that.getDiet();
  }

  @Override
  public int hashCode() {
    return Objects.hash(getAge(), getName(), getDemon(), getFriendNames(), getDiet(), isAlive());
  }
}
