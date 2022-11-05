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
    private String name;
    private AnimalType animalType;
    private int countLegs;
    private boolean isDomesticated;
    private boolean isHerbivore;
    private OwnerExternalizable ownerExternalizable;


    public AnimalExternalizable() {
    }

    public String getName() {
        return name;
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

    public void setOwnerExternalizable(OwnerExternalizable ownerExternalizable) {
        this.ownerExternalizable = ownerExternalizable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return countLegs == that.countLegs && isDomesticated == that.isDomesticated && isHerbivore == that.isHerbivore && Objects.equals(name, that.name) && animalType == that.animalType && Objects.equals(ownerExternalizable, that.ownerExternalizable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, animalType, countLegs, isDomesticated, isHerbivore, ownerExternalizable);
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "name='" + name + '\'' +
                ", animalType=" + animalType +
                ", countLegs=" + countLegs +
                ", isDomesticated=" + isDomesticated +
                ", isHerbivore=" + isHerbivore +
                ", ownerExternalizable=" + ownerExternalizable +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(animalType);
        out.writeInt(countLegs);
        out.writeByte(isDomesticated ? 1 : 0);
        out.writeByte(isHerbivore ? 1 : 0);
        out.writeObject(ownerExternalizable);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        animalType = (AnimalType) in.readObject();
        countLegs = in.readInt();
        isDomesticated = in.readByte() == 1;
        isHerbivore = in.readByte() == 1;
        ownerExternalizable = (OwnerExternalizable) in.readObject();
    }
}

class OwnerExternalizable implements Externalizable {
    private String name;
    private boolean isOrganization;

    public OwnerExternalizable() {
    }

    public String getName() {
        return name;
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
        OwnerExternalizable that = (OwnerExternalizable) o;
        return isOrganization == that.isOrganization && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, isOrganization);
    }

    @Override
    public String toString() {
        return "OwnerExternalizable{" +
                "name='" + name + '\'' +
                ", isOrganization=" + isOrganization +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeByte(isOrganization ? 1 : 0);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        isOrganization = in.readByte() == 1;
    }
}
