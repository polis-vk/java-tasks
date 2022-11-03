package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String name;
    private int age;
    private boolean friendly;
    private boolean warmBlooded;
    private AnimalType animalType;
    private Population population;

    public AnimalWithMethods(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, Population population) {
        this.name = name;
        this.age = age;
        this.friendly = friendly;
        this.warmBlooded = warmBlooded;
        this.animalType = animalType;
        this.population = population;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isFriendly() {
        return friendly;
    }

    public boolean isWarmBlooded() {
        return warmBlooded;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public Population getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setFriendly(boolean friendly) {
        this.friendly = friendly;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setWarmBlooded(boolean warmBlooded) {
        this.warmBlooded = warmBlooded;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public void setPopulation(Population population) {
        this.population = population;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        oos.writeUTF(name);
        oos.writeInt(age);
        oos.writeBoolean(friendly);
        oos.writeBoolean(warmBlooded);
        oos.writeUTF(animalType.toString());
        oos.writeObject(population);
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        name = ois.readUTF();
        age = ois.readInt();
        friendly = ois.readBoolean();
        warmBlooded = ois.readBoolean();
        animalType = AnimalType.valueOf(ois.readUTF());
        population = (Population) ois.readObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalWithMethods that = (AnimalWithMethods) o;
        return that.getName().equals(getName()) && that.getAge() == getAge()
                && that.isFriendly() == isFriendly() && that.isWarmBlooded() == isWarmBlooded()
                && that.getAnimalType() == getAnimalType() && that.getPopulation() == getPopulation();
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age + '\'' +
                ", friendly=" + friendly + '\'' +
                ", warm-blooded=" + warmBlooded + '\'' +
                ", population=" + population + '\'' +
                ", animalType=" + animalType +
                '}';
    }
}
