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
    private static final byte HAS_ALIAS = 1;
    private static final byte HAS_MOVE_TYPE = 2;
    private static final byte IS_WILD = 4;
    private static final byte IS_FURRY = 8;

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
        AnimalExternalizable animal = (AnimalExternalizable) o;
        return legs == animal.legs && wild == animal.wild && furry == animal.furry
                && Objects.equals(alias, animal.alias) && Objects.equals(organization, animal.organization)
                && moveType == animal.moveType;
    }

    @Override
    public int hashCode() {
        return Objects.hash(alias, legs, wild, furry, organization, moveType);
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
        out.writeByte(setFlags());
        writeString(out, alias);
        out.writeInt(legs);
        out.writeObject(organization);
        if (moveType != null) {
            writeString(out, moveType.toString());
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        byte flags = in.readByte();
        alias = readString(in, flags, HAS_ALIAS);
        legs = in.readInt();
        wild = (flags & IS_WILD) != 0;
        furry = (flags & IS_FURRY) != 0;
        organization = (OrganizationExternalizable) in.readObject();
        String currMoveType = readString(in, flags, HAS_MOVE_TYPE);
        if (currMoveType != null) {
            moveType = MoveType.valueOf(currMoveType);
        }
    }

    private static void writeString(ObjectOutput output, String str) throws IOException {
        if (str != null) {
            output.writeUTF(str);
        }
    }

    private static String readString(ObjectInput input, byte flags, byte category) throws IOException {
        return (flags & category) != 0 ? input.readUTF() : null;
    }

    private byte setFlags() {
        byte result = 0;
        if (alias != null) {
            result = (byte) (result | HAS_ALIAS);
        }
        if (moveType != null) {
            result = (byte) (result | HAS_MOVE_TYPE);
        }
        if (wild) {
            result = (byte) (result | IS_WILD);
        }
        if (furry) {
            result = (byte) (result | IS_FURRY);
        }
        return result;
    }

    static class OrganizationExternalizable implements Externalizable {
        private static final byte HAS_ORGANIZATION_NAME = 1;
        private static final byte HAS_ORGANIZATION_OWNER = 2;
        private static final byte ORGANIZATION_IS_FOREIGN = 4;

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
            OrganizationExternalizable organization = (OrganizationExternalizable) o;
            return foreign == organization.foreign && Objects.equals(name, organization.name)
                    && Objects.equals(owner, organization.owner);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, owner, foreign);
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
            out.writeByte(setFlags());
            writeString(out, name);
            writeString(out, owner);
        }

        @Override
        public void readExternal(ObjectInput in) throws IOException {
            byte flags = in.readByte();
            name = readString(in, flags, HAS_ORGANIZATION_NAME);
            owner = readString(in, flags, HAS_ORGANIZATION_OWNER);
            foreign = (flags & ORGANIZATION_IS_FOREIGN) != 0;
        }

        private byte setFlags() {
            byte result = 0;
            if (name != null) {
                result = (byte) (result | HAS_ORGANIZATION_NAME);
            }
            if (owner != null) {
                result = (byte) (result | HAS_ORGANIZATION_OWNER);
            }
            if (foreign) {
                result = (byte) (result | ORGANIZATION_IS_FOREIGN);
            }
            return result;
        }
    }
}

