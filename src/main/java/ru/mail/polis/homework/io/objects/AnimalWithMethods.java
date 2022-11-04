package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {
    private String alias;
    private int legs;
    private boolean wild;
    private boolean furry;
    private OrganizationWithMethods organization;
    private MoveType moveType;

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

    public OrganizationWithMethods getOrganization() {
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

    public void setOrganization(OrganizationWithMethods organization) {
        this.organization = organization;
    }

    public void setMoveType(MoveType moveType) {
        this.moveType = moveType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods animal = (AnimalWithMethods) o;
        return getLegs() == animal.getLegs() && isWild() == animal.isWild() && isFurry() == animal.isFurry() && Objects.equals(getAlias(), animal.getAlias()) && Objects.equals(getOrganization(), animal.getOrganization()) && getMoveType() == animal.getMoveType();
    }

    @Override
    public int hashCode() {
        return Objects.hash(getAlias(), getLegs(), isWild(), isFurry(), getOrganization(), getMoveType());
    }

    @Override
    public String toString() {
        return "AnimalWithMethods{" +
                "alias='" + alias + '\'' +
                ", legs=" + legs +
                ", wild=" + wild +
                ", furry=" + furry +
                ", organization=" + organization +
                ", moveType=" + moveType +
                '}';
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        writeString(out, alias);
        out.writeInt(legs);
        out.writeByte(setBooleanFlags(wild, furry));
        out.writeObject(organization);
        writeString(out, moveType.toString());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        alias = readString(in);
        legs = in.readInt();
        byte booleanFlags = in.readByte();
        wild = (booleanFlags & 1) != 0;
        furry = (booleanFlags & 2) != 0;
        organization = (OrganizationWithMethods) in.readObject();
        moveType = MoveType.valueOf(readString(in));
    }

    private static void writeString(ObjectOutput output, String str) throws IOException {
        if (str == null) {
            output.writeByte(0);
        } else {
            output.writeByte(1);
            output.writeUTF(str);
        }
    }

    private static String readString(ObjectInput input) throws IOException {
        return input.readByte() == 0 ? null : input.readUTF();
    }

    private static byte setBooleanFlags(boolean wild, boolean furry) {
        byte result = 0;
        if (wild) {
            result = (byte) (result | 1);
        }
        if (furry) {
            result = (byte) (result | 2);
        }
        return result;
    }

    static class OrganizationWithMethods implements Serializable {
        private String name;
        private String owner;
        private boolean foreign;

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
            OrganizationWithMethods that = (OrganizationWithMethods) o;
            return isForeign() == that.isForeign() && Objects.equals(getName(), that.getName()) && Objects.equals(getOwner(), that.getOwner());
        }

        @Override
        public int hashCode() {
            return Objects.hash(getName(), getOwner(), isForeign());
        }

        @Override
        public String toString() {
            return "OrganizationWithMethods{" +
                    "name='" + name + '\'' +
                    ", owner='" + owner + '\'' +
                    ", foreign=" + foreign +
                    '}';
        }

        private void writeObject(ObjectOutputStream out) throws IOException {
            writeString(out, name);
            writeString(out, owner);
            out.writeBoolean(foreign);
        }

        private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
            name = readString(in);
            owner = readString(in);
            foreign = in.readBoolean();
        }
    }
}

