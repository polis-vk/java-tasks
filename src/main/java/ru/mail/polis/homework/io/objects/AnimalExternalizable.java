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

    private static final byte TRUE = 1;
    private static final byte FALSE = 0;

    private String name;
    private int legs;
    private double weight;
    private boolean isChild;
    private boolean isWild;
    private AnimalType type;
    private Organization organization;

    public AnimalExternalizable() {
    }

    public AnimalExternalizable(
        String name,
        int legs,
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
        AnimalExternalizable animal = (AnimalExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeByte(name != null ? TRUE : FALSE);
        if (name != null ) {
            out.writeUTF(name);
        }

        out.writeInt(legs);
        out.writeDouble(weight);
        out.writeByte(isChild() ? TRUE : FALSE);
        out.writeByte(isWild() ? TRUE : FALSE);

        out.writeByte(type != null ? TRUE : FALSE);
        if (type != null) {
            out.writeUTF(type.name());
        }

        out.writeByte(organization != null ? TRUE : FALSE);
        if (organization != null) {
            String organizationName = organization.getName();
            out.writeByte(organizationName != null ? TRUE : FALSE);
            if (organizationName != null) {
                out.writeUTF(organizationName);
            }

            out.writeByte(organization.isVolunteer() ? TRUE : FALSE);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        if (in.readByte() == TRUE) {
            name = in.readUTF();
        }

        legs = in.readInt();
        weight = in.readDouble();
        isChild = in.readByte() == TRUE;
        isWild = in.readByte() == TRUE;

        if (in.readByte() == TRUE) {
            type = AnimalType.valueOf(in.readUTF());
        }

        if (in.readByte() == TRUE) {
            String organizationName = null;
            if (in.readByte() == TRUE) {
                organizationName = in.readUTF();
            }

            boolean isVolunteer = in.readByte() == TRUE;

            organization = new Organization(organizationName, isVolunteer);
        }
    }
}
