package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable implements Externalizable {
    private static final long serialVersionUID = 1357924680098765432L;

    protected String name;
    protected int age;
    protected boolean isWild;
    protected boolean isFed;
    protected AnimalAbilityType animalAbilityType;
    protected Location animalLocation;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(String name, int age, boolean isWild, boolean isFed, AnimalAbilityType animalAbilityType, Location animalLocation) {
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
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return age == animal.age && isWild == animal.isWild && isFed == animal.isFed && Objects.equals(name, animal.name) && animalAbilityType == animal.animalAbilityType && Objects.equals(animalLocation, animal.animalLocation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, age, isWild, isFed, animalAbilityType, animalLocation);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", isWild=" + isWild +
                ", isFed=" + isFed +
                ", animalAbilityType=" + animalAbilityType +
                ", animalLocation=" + animalLocation +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        // Write Location first as it's Object
        out.writeUTF(animalLocation.getLatitude());
        out.writeUTF(animalLocation.getLongitude());
        // Write other fields of superclass
        out.writeUTF(name);
        out.writeInt(age);
        out.writeBoolean(isWild);
        out.writeBoolean(isFed);
        out.writeUTF(animalAbilityType.toString());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        // Read Location first
        animalLocation = new Location();
        animalLocation.setLatitude(in.readUTF());
        animalLocation.setLongitude(in.readUTF());
        // Read other fields
        name = in.readUTF();
        age = in.readInt();
        isWild = in.readBoolean();
        isFed = in.readBoolean();
        animalAbilityType = AnimalAbilityType.valueOf(in.readUTF());
    }
}
