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

    private static final byte TRUE = 1;
    private static final byte FALSE = 0;

    private String name;
    private int legs;
    private double weight;
    private boolean isChild;
    private boolean isWild;
    private AnimalType type;
    private Organization organization;

    public AnimalWithMethods(
        String name, int legs,
        double weight,
        boolean isChild,
        boolean isWild,
        AnimalType type,
        Organization organization
    ) {
        this.name = name;
        this.legs = legs;
        this.weight = weight;
        this.isChild = isChild;
        this.isWild = isWild;
        this.type = type;
        this.organization = organization;
    }

    public String getName() {
        return name;
    }

    public int getLegs() {
        return legs;
    }

    public double getWeight() {
        return weight;
    }

    public boolean isChild() {
        return isChild;
    }

    public boolean isWild() {
        return isWild;
    }

    public AnimalType getType() {
        return type;
    }

    public Organization getOrganization() {
        return organization;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }

    public void setIsChild(boolean isChild) {
        this.isChild = isChild;
    }

    public void setIsWild(boolean isWild) {
        this.isWild = isWild;
    }

    public void setType(AnimalType type) {
        this.type = type;
    }

    public void setOrganization(Organization organization) {
        this.organization = organization;
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, legs, weight, isChild, isWild, type, organization);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return Objects.equals(this.name, animal.name) && this.legs == animal.legs && this.weight == animal.weight &&
            this.isChild == animal.isChild && this.isWild == animal.isWild &&Objects.equals(this.type, animal.type) &&
            Objects.equals(this.organization, animal.organization);
    }

    @Override
    public String toString() {
        return "Animal{" +
            "name='" + name + '\'' +
            ", legs=" + legs +
            ", weight=" + weight +
            ", isChild=" + isChild +
            ", isWild=" + isWild +
            ", type=" + type +
            ", organization=" + organization +
            '}';
    }

    private void writeObject(ObjectOutputStream outputStream) throws IOException {
        outputStream.writeByte(name != null ? TRUE : FALSE);
        if (name != null ) {
            outputStream.writeUTF(name);
        }

        outputStream.writeInt(legs);
        outputStream.writeDouble(weight);
        outputStream.writeByte(isChild() ? TRUE : FALSE);
        outputStream.writeByte(isWild() ? TRUE : FALSE);

        outputStream.writeByte(type != null ? TRUE : FALSE);
        if (type != null) {
            outputStream.writeUTF(type.name());
        }

        outputStream.writeByte(organization != null ? TRUE : FALSE);
        if (organization != null) {
            String organizationName = organization.getName();
            outputStream.writeByte(organizationName != null ? TRUE : FALSE);
            if (organizationName != null) {
                outputStream.writeUTF(organizationName);
            }

            outputStream.writeByte(organization.isVolunteer() ? TRUE : FALSE);
        }
    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        if (inputStream.readByte() == TRUE) {
            name = inputStream.readUTF();
        }

        legs = inputStream.readInt();
        weight = inputStream.readDouble();
        isChild = inputStream.readByte() == TRUE;
        isWild = inputStream.readByte() == TRUE;

        if (inputStream.readByte() == TRUE) {
            type = AnimalType.valueOf(inputStream.readUTF());
        }

        if (inputStream.readByte() == TRUE) {
            String organizationName = null;
            if (inputStream.readByte() == TRUE) {
                organizationName = inputStream.readUTF();
            }

            boolean isVolunteer = inputStream.readByte() == TRUE;

            organization = new Organization(organizationName, isVolunteer);
        }
    }
}
