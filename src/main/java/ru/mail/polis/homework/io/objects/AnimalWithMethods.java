package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

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
    private PopulationWithMethods population;

    public AnimalWithMethods(String name, int age, boolean friendly, boolean warmBlooded, AnimalType animalType, PopulationWithMethods population) {
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

    public PopulationWithMethods getPopulation() {
        return population;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setName(String name) {
        this.name = name;
    }

    private void writeObject(ObjectOutputStream oos) throws IOException {
        AnimalByte animalByte = new AnimalByte(this);
        oos.writeByte(animalByte.writeByte());
        if (animalByte.nameIsNotNull()) {
            oos.writeUTF(name);
        }
        oos.writeInt(age);
        if (animalByte.animalTypeIsNotNull()) {
            oos.writeUTF(animalType.name());
        }
        if (animalByte.populationIsNotNull()) {
            oos.writeObject(population);
        }
    }

    private void readObject(ObjectInputStream ois) throws IOException, ClassNotFoundException {
        AnimalByte animalByte = new AnimalByte(ois.readByte());
        if (animalByte.nameIsNotNull()) {
            name = ois.readUTF();
        }
        age = ois.readInt();
        friendly = animalByte.isFriendly();
        warmBlooded = animalByte.isWarmBlooded();
        if (animalByte.animalTypeIsNotNull()) {
            animalType = AnimalType.valueOf(ois.readUTF());
        }
        if (animalByte.populationIsNotNull()) {
            population = (PopulationWithMethods) ois.readObject();
        }
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return Objects.equals(animal.name, name) && animal.age == age
                && animal.friendly == friendly && animal.warmBlooded == warmBlooded
                && animal.animalType == animalType && Objects.equals(animal.population, population);
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
