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
    private AnimalType animalType;
    private int countLegs;
    private boolean isDomesticated;
    private boolean isHerbivore;
    private OwnerWithMethods ownerWithMethods;


    public String getName() {
        return name;
    }

    public AnimalType getAnimalType() {
        return animalType;
    }

    public int getCountLegs() {
        return countLegs;
    }

    public boolean isDomesticated() {
        return isDomesticated;
    }

    public boolean isHerbivore() {
        return isHerbivore;
    }

    public OwnerWithMethods getOwnerWithMethods() {
        return ownerWithMethods;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAnimalType(AnimalType animalType) {
        this.animalType = animalType;
    }

    public void setCountLegs(int countLegs) {
        this.countLegs = countLegs;
    }

    public void setDomesticated(boolean domesticated) {
        isDomesticated = domesticated;
    }

    public void setHerbivore(boolean herbivore) {
        isHerbivore = herbivore;
    }

    public void setOwnerWithMethods(OwnerWithMethods ownerWithMethods) {
        this.ownerWithMethods = ownerWithMethods;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return countLegs == that.getCountLegs() && isDomesticated == that.isDomesticated()
                && isHerbivore == that.isHerbivore() && Objects.equals(name, that.getName())
                && animalType == that.getAnimalType()
                && Objects.equals(ownerWithMethods, that.getOwnerWithMethods());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, animalType, countLegs, isDomesticated, isHerbivore, ownerWithMethods);
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "name='" + name + '\'' +
                ", animalType=" + animalType +
                ", countLegs=" + countLegs +
                ", isDomesticated=" + isDomesticated +
                ", isHerbivore=" + isHerbivore +
                ", ownerWithMethods=" + ownerWithMethods +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(writing(name));
        out.writeUTF(animalType == null ? "null" : animalType.name());
        out.writeInt(countLegs);
        out.writeByte(isDomesticated ? 1 : 0);
        out.writeByte(isHerbivore ? 1 : 0);
        out.writeObject(ownerWithMethods);
    }

    private static String writing(String str) {
        return str == null ? "null" : str;
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = reading(in.readUTF());
        String at = reading(in.readUTF());
        animalType = at == null ? null : AnimalType.valueOf(at);
        countLegs = in.readInt();
        isDomesticated = in.readByte() == 1;
        isHerbivore = in.readByte() == 1;
        ownerWithMethods = (OwnerWithMethods) in.readObject();
    }

    private String reading(String str) {
        return str.equals("null") ? null : str;
    }
}

class OwnerWithMethods implements Serializable {
    private String name;
    private boolean isOrganization;

    public String getName() {
        return name;
    }

    public boolean isOrganization() {
        return isOrganization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOrganization(boolean organization) {
        this.isOrganization = organization;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OwnerWithMethods that = (OwnerWithMethods) o;
        return isOrganization == that.isOrganization() && Objects.equals(name, that.getName());
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isOrganization);
    }

    @Override
    public String toString() {
        return "OwnerWithMethods{" +
                "name='" + name + '\'' +
                ", isOrganization=" + isOrganization +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeUTF(name);
        out.writeByte(isOrganization ? 1 : 0);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        name = in.readUTF();
        isOrganization = in.readByte() == 1;
    }
}