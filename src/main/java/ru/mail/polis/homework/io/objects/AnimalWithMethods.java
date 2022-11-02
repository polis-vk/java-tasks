package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Arrays;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    protected String name;
    protected int age;
    protected boolean isWild;
    protected boolean isFed;
    protected AnimalAbilityType animalAbilityType;
    protected Location animalLocation;

    public AnimalWithMethods() {
    }

    public AnimalWithMethods(String name, int age, boolean isWild, boolean isFed, AnimalAbilityType animalAbilityType, Location animalLocation) {
        this.name = name;
        this.age = age;
        this.isWild = isWild;
        this.isFed = isFed;
        this.animalAbilityType = animalAbilityType;
        this.animalLocation = animalLocation;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public boolean isWild() {
        return isWild;
    }

    public boolean isFed() {
        return isFed;
    }

    public AnimalAbilityType getAnimalAbilityType() {
        return animalAbilityType;
    }

    public Location getAnimalLocation() {
        return animalLocation;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setWild(boolean wild) {
        isWild = wild;
    }

    public void setFed(boolean fed) {
        isFed = fed;
    }

    public void setAnimalAbilityType(AnimalAbilityType animalAbilityType) {
        this.animalAbilityType = animalAbilityType;
    }

    public void setAnimalLocation(Location animalLocation) {
        this.animalLocation = animalLocation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return age == animal.age && isWild == animal.isWild && isFed == animal.isFed && Objects.equals(name, animal.name) && animalAbilityType == animal.animalAbilityType && Objects.equals(animalLocation, animal.animalLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isFed, animalAbilityType, animalLocation);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isWild=" + isWild +
                ", isFed=" + isFed +
                ", animalAbilityType=" + animalAbilityType +
                ", animalLocation=" + animalLocation +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        // Write Location first as it's Object
        out.writeUTF(animalLocation.getLatitude());
        out.writeUTF(animalLocation.getLongitude());
        // Write other fields of superclass
        out.writeUTF(name);
        out.writeInt(age);
        out.writeBoolean(isWild);
        out.writeBoolean(isFed);
        out.writeInt(animalAbilityType.ordinal());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        // Read Location first
        animalLocation = new Location();
        animalLocation.setLatitude(in.readUTF());
        animalLocation.setLongitude(in.readUTF());
        // Read other fields
        name = in.readUTF();
        age = in.readInt();
        isWild = in.readBoolean();
        isFed = in.readBoolean();

    }
}
