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
    private String alias;
    private int legs;
    private boolean wild;
    private boolean furry;
    private OrganizationExternalizable organization;
    private MoveType moveType;

    public AnimalExternalizable() {
    }

    public String getAlias() {
        return alias;
    }

    public int getLegs() {
        return legs;
    }

    public boolean isWild() {
        return wild;
    }

    public boolean isFurry() {
        return furry;
    }

    public OrganizationExternalizable getOrganization() {
        return organization;
    }

    public MoveType getMoveType() {
        return moveType;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public void setLegs(int legs) {
        this.legs = legs;
    }

    public void setWild(boolean wild) {
        this.wild = wild;
    }

    public void setFurry(boolean furry) {
        this.furry = furry;
    }

    public void setOrganization(OrganizationExternalizable organization) {
        this.organization = organization;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return getLegs() == that.getLegs() && isWild() == that.isWild() && isFurry() == that.isFurry() && Objects.equals(getAlias(), that.getAlias()) && Objects.equals(getOrganization(), that.getOrganization()) && getMoveType() == that.getMoveType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlias(), getLegs(), isWild(), isFurry(), getOrganization(), getMoveType());
    }

    @Override
    public String toString() {
        return "AnimalExternalizable{" +
                "alias='" + alias + '\'' +
                ", legs=" + legs +
                ", wild=" + wild +
                ", furry=" + furry +
                ", organization=" + organization +
                ", moveType=" + moveType +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(alias);
        out.writeInt(legs);
        out.writeByte(setBooleanFlags(wild, furry));
        out.writeObject(organization);
        out.writeObject(moveType);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        alias = (String) in.readObject();
        legs = in.readInt();
        byte booleanFlags = in.readByte();
        wild = (booleanFlags & 1) != 0;
        furry = (booleanFlags & 2) != 0;
        organization = (OrganizationExternalizable) in.readObject();
        moveType = (MoveType) in.readObject();
    }

    private static byte setBooleanFlags(boolean wild, boolean furry) {
        byte result = 0;
        if (wild) result = (byte) (result | 1);
        if (furry) result = (byte) (result | 2);
        return result;
    }
}

class OrganizationExternalizable implements Externalizable {
    private String name;
    private String owner;
    private boolean foreign;

    public OrganizationExternalizable() {
    }

    public String getName() {
        return name;
    }

    public String getOwner() {
        return owner;
    }

    public boolean isForeign() {
        return foreign;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public void setForeign(boolean foreign) {
        this.foreign = foreign;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OrganizationExternalizable that = (OrganizationExternalizable) o;
        return isForeign() == that.isForeign() && Objects.equals(getName(), that.getName()) && Objects.equals(getOwner(), that.getOwner());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getOwner(), isForeign());
    }

    @Override
    public String toString() {
        return "OrganizationExternalizable{" +
                "name='" + name + '\'' +
                ", owner='" + owner + '\'' +
                ", foreign=" + foreign +
                '}';
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeObject(name);
        out.writeObject(owner);
        out.writeBoolean(foreign);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        name = (String) in.readObject();
        owner = (String) in.readObject();
        foreign = in.readBoolean();
    }
}

