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

  private Brain brain;
  private List<String> listName;
  private int weight;
  private String name;
  private Habitation habitation;
  private long distanceTraveled;

  public AnimalWithMethods(Brain brain, List<String> listName, int weight, String name, String habitation, long distanceTraveled) {
    this.brain = brain;
    this.listName = listName;
    this.weight = weight;
    this.name = name;
    this.habitation = Habitation.valueOf(habitation);
    this.distanceTraveled = distanceTraveled;
  }

  private void writeObject(ObjectOutputStream out) throws IOException {
    out.writeObject(this.brain);
    out.writeObject(this.listName);
    out.writeInt(this.weight);
    out.writeUTF(this.name);
    out.writeObject(this.habitation);
    out.writeLong(this.distanceTraveled);
  }

  private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
    this.brain = (Brain) in.readObject();
    this.listName = (List<String>) in.readObject();
    this.weight = in.readInt();
    this.name = in.readUTF();
    this.habitation = (Habitation) in.readObject();
    this.distanceTraveled = in.readLong();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof AnimalWithMethods)) return false;
    AnimalWithMethods that = (AnimalWithMethods) o;
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
