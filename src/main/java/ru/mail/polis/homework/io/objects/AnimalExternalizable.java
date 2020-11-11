package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {

  private Brain brain;
  private List<String> listName;
  private int weight;
  private String name;
  private Habitation habitation;
  private long distanceTraveled;

  public AnimalExternalizable() {

  }

  public AnimalExternalizable(Brain brain, List<String> listName, int weight, String name, Habitation habitation, long distanceTraveled) {
    this.brain = brain;
    this.listName = listName;
    this.weight = weight;
    this.name = name;
    this.habitation = habitation;
    this.distanceTraveled = distanceTraveled;
  }

  @Override
  public void writeExternal(ObjectOutput out) throws IOException {
    out.writeInt(this.brain.getSize());
    out.writeObject(this.listName);
    out.writeInt(this.weight);
    out.writeUTF(this.name);
    out.writeObject(this.habitation);
    out.writeLong(this.distanceTraveled);
  }

  @Override
  public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
    this.brain = new Brain(in.readInt());
    this.listName = (List<String>) in.readObject();
    this.weight = in.readInt();
    this.name = in.readUTF();
    this.habitation = (Habitation) in.readObject();
    this.distanceTraveled = in.readLong();
  }

  public Brain getBrain() {
    return brain;
  }

  public void setBrain(Brain brain) {
    this.brain = brain;
  }

  public List<String> getListName() {
    return listName;
  }

  public void setListName(List<String> listName) {
    this.listName = listName;
  }

  public int getWeight() {
    return weight;
  }

  public void setWeight(int weight) {
    this.weight = weight;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public Habitation getHabitation() {
    return habitation;
  }

  public void setHabitation(Habitation habitation) {
    this.habitation = habitation;
  }

  public long getDistanceTraveled() {
    return distanceTraveled;
  }

  public void setDistanceTraveled(long distanceTraveled) {
    this.distanceTraveled = distanceTraveled;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnimalExternalizable)) return false;
    AnimalExternalizable that = (AnimalExternalizable) o;
    return weight == that.weight &&
            distanceTraveled == that.distanceTraveled &&
            brain.equals(that.brain) &&
            listName.equals(that.listName) &&
            name.equals(that.name) &&
            habitation == that.habitation;
  }

  @Override
  public int hashCode() {
    return Objects.hash(brain, listName, weight, name, habitation, distanceTraveled);
  }
}