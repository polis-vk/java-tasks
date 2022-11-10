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
    private static final byte NULL_BYTE = 0;
    private static final byte NOT_NULL_BYTE = 1;

    private boolean isPet;
    private boolean isPredator;
    private int legs;
    private String color;
    private MoveType moveType;

    private AnimalPassportWithMethods animalPassportWithMethods;

    public AnimalWithMethods() {
    }

    public void setPet(boolean pet) {
        isPet = pet;
    }

    public void setPredator(boolean predator) {
        isPredator = predator;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    public void setAnimalPassportWithMethods(AnimalPassportWithMethods animalPassportWithMethods) {
        this.animalPassportWithMethods = animalPassportWithMethods;
    }

    @Override
    public int hashCode() {
        return Objects.hash(isPet, isPredator, legs,
                color, moveType, animalPassportWithMethods);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        AnimalWithMethods animalWithMethods = (AnimalWithMethods) obj;
        return isPet == animalWithMethods.isPet &&
                isPredator == animalWithMethods.isPredator &&
                legs == animalWithMethods.legs &&
                Objects.equals(color, animalWithMethods.color) &&
                moveType == animalWithMethods.moveType &&
                Objects.equals(animalPassportWithMethods, animalWithMethods.animalPassportWithMethods);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "pet='" + isPet + '\'' +
                ", predator=" + isPredator +
                ", legs=" + legs +
                ", color=" + color +
                ", moveType=" + moveType +
                ", animalPassportWithMethods=" + animalPassportWithMethods +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        byte booleansAsByte = (byte) (((isPet ? 1 : 0) << 1) + (isPredator ? 1 : 0));
        out.writeByte(booleansAsByte);
        out.writeInt(legs);
        SerializationUtilMethods.writeString(color, out);
        MoveType currentMoveType = moveType;
        SerializationUtilMethods.writeString(currentMoveType == null ? null : currentMoveType.toString(), out);
        if (animalPassportWithMethods == null) {
            out.writeByte(NULL_BYTE);
        } else {
            out.writeByte(NOT_NULL_BYTE);
            out.writeObject(animalPassportWithMethods);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        byte booleansAsByte = in.readByte();
        isPredator = (booleansAsByte & 1) != 0;
        isPet = (booleansAsByte & 2) != 0;
        legs = in.readInt();
        color = SerializationUtilMethods.readString(in);
        String moveTypeValue = SerializationUtilMethods.readString(in);
        moveType = moveTypeValue == null ? null : MoveType.valueOf(moveTypeValue);
        byte objIsNull = in.readByte();
        if (objIsNull == NULL_BYTE) {
            animalPassportWithMethods = null;
        } else {
            animalPassportWithMethods = (AnimalPassportWithMethods) in.readObject();
        }
    }

}

class AnimalPassportWithMethods implements Serializable {
    private String species;
    private Sex sex;

    private String name;
    private int age;
    private boolean isVaccinated;
    private String descriptionOfAnimal;

    public AnimalPassportWithMethods() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSpecies(String species) {
        this.species = species;
    }

    public void setVaccinated(boolean vaccinated) {
        isVaccinated = vaccinated;
    }

    public void setDescriptionOfAnimal(String descriptionOfAnimal) {
        this.descriptionOfAnimal = descriptionOfAnimal;
    }

    @Override
    public int hashCode() {
        return Objects.hash(species, sex, name, age, isVaccinated, descriptionOfAnimal);
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || obj.getClass() != getClass()) {
            return false;
        }
        AnimalPassportWithMethods animalPassportWithMethods = (AnimalPassportWithMethods) obj;
        return Objects.equals(species, animalPassportWithMethods.species) &&
                sex == animalPassportWithMethods.sex &&
                Objects.equals(name, animalPassportWithMethods.name) &&
                age == animalPassportWithMethods.age &&
                isVaccinated == animalPassportWithMethods.isVaccinated &&
                Objects.equals(descriptionOfAnimal, animalPassportWithMethods.descriptionOfAnimal);
    }

    @Override
    public String toString() {
        return "AnimalPassportWithMethods{" +
                "species='" + species + '\'' +
                ", sex=" + sex +
                ", name=" + name +
                ", age=" + age +
                ", vaccinated=" + isVaccinated +
                ", descriptionOfAnimal=" + descriptionOfAnimal +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        SerializationUtilMethods.writeString(species, out);
        Sex currentSex = sex;
        SerializationUtilMethods.writeString(currentSex == null ? null : currentSex.toString(), out);
        SerializationUtilMethods.writeString(name, out);
        out.writeInt(age);
        out.writeByte(isVaccinated ? 1 : 0);
        SerializationUtilMethods.writeString(descriptionOfAnimal, out);
    }

    private void readObject(ObjectInputStream in) throws IOException {
        species = SerializationUtilMethods.readString(in);
        String sexValue = SerializationUtilMethods.readString(in);
        sex = sexValue == null ? null : Sex.valueOf(sexValue);
        name = SerializationUtilMethods.readString(in);
        age = in.readInt();
        isVaccinated = in.readByte() == 1;
        descriptionOfAnimal = SerializationUtilMethods.readString(in);
    }

}
