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
    private int legs;
    private boolean hair;
    private boolean vertebrate;
    private String name;
    private AnimalType type;
    private AnimalOwnerWithMethods owner;

    private static final int TRUE = 1;
    private static final int FALSE = 0;

    public AnimalWithMethods(int legs, boolean hair, boolean vertebrate, String name, AnimalType type,
                             AnimalOwnerWithMethods owner) {
        this.legs = legs;
        this.hair = hair;
        this.vertebrate = vertebrate;
        this.name = name;
        this.type = type;
        this.owner = owner;
    }

    public int getLegs() {
        return legs;
    }

    public boolean isHair() {
        return hair;
    }

    public boolean isVertebrate() {
        return vertebrate;
    }

    public String getName() {
        return name;
    }

    public AnimalType getType() {
        return type;
    }

    public AnimalOwnerWithMethods getOwner() {
        return owner;
    }

    @Override
    public int hashCode() {
        return Objects.hash(legs, hair, vertebrate, name, type, owner);
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof AnimalWithMethods)) {
            return false;
        }

        AnimalWithMethods animal = (AnimalWithMethods) obj;
        return this.legs == animal.legs && this.hair == animal.hair && this.vertebrate == animal.vertebrate &&
                Objects.equals(this.name, animal.name) && Objects.equals(this.type, animal.type) &&
                Objects.equals(this.owner, animal.owner);
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(legs);

        byte hairAndVertebrate = (byte) ((hair ? TRUE : FALSE) << 1);
        hairAndVertebrate += vertebrate ? TRUE : FALSE;
        out.writeByte(hairAndVertebrate);

        byte nameIsNull = (byte) (name == null ? TRUE : FALSE);
        out.writeByte(nameIsNull);
        if (nameIsNull == FALSE) {
            out.writeUTF(name);
        }

        byte typeIsNull = (byte) (type == null ? TRUE : FALSE);
        out.writeByte(typeIsNull);
        if (typeIsNull == FALSE) {
            out.writeUTF(type.name());
        }

        out.writeObject(owner);
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        legs = in.readInt();

        byte hairAndVertebrate = in.readByte();
        hair = hairAndVertebrate >> 1 == TRUE;
        vertebrate = hairAndVertebrate % 2 == TRUE;

        if (in.readByte() == FALSE) {
            name = in.readUTF();
        }

        if (in.readByte() == FALSE) {
            type = AnimalType.valueOf(in.readUTF());
        }

        owner = (AnimalOwnerWithMethods) in.readObject();
    }
}
