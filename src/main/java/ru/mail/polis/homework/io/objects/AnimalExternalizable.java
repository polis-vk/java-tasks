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
    private static final int TYPE_BIT_INDEX = 0;
    private static final int NAME_BIT_INDEX = 1;
    private static final int IS_SEEK_BIT_INDEX = 2;
    private static final int IN_NURSERY_BIT_INDEX = 3;

    private AnimalNurseryExternalizable nursery;
    private int age;
    private AnimalType type;
    private String name;
    private boolean isSeek;
    private boolean inNursery;

    public AnimalExternalizable() {
        type = AnimalType.NONE;
    }

    public AnimalExternalizable(AnimalNurseryExternalizable nursery, AnimalType type, String name, int age, boolean isSeek, boolean inNursery) {
        this.nursery = nursery;
        this.age = age;
        this.type = type;
        this.name = name;
        this.isSeek = isSeek;
        this.inNursery = inNursery;
    }

    public AnimalNurseryExternalizable getNursery() {
        return nursery;
    }

    public int getAge() {
        return age;
    }

    public AnimalType getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public boolean isSeek() {
        return isSeek;
    }

    public boolean isInNursery() {
        return inNursery;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nursery, type, name, age, isSeek, inNursery);
    }

    @Override
    public String toString() {
        return "{\"AnimalNurseryExternalizable\":" + nursery + "," +
                "\"AnimalType\":" + "\"" + type + "\"," +
                "\"Name\":" + "\"" + name + "\"," +
                "\"Age\":" + age + "," +
                "\"isSeek\":" + "\"" + isSeek + "\"," +
                "\"inNursery\":" + "\"" + inNursery + "\"}";
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        AnimalExternalizable animal = (AnimalExternalizable) obj;
        return Objects.equals(nursery, animal.nursery) &&
                type.equals(animal.type) &&
                Objects.equals(name, animal.name) &&
                age == animal.age &&
                isSeek == animal.isSeek &&
                inNursery == animal.inNursery;
    }

    public void writeExternal(ObjectOutput out) throws IOException {
        boolean nullName = name == null;
        boolean nullType = type == null;

        out.writeObject(nursery);
        out.writeInt(age);

        byte zip = AnimalNurseryWithMethods.byteZip(nullType, TYPE_BIT_INDEX, (byte) 0);
        zip = AnimalNurseryWithMethods.byteZip(nullName, NAME_BIT_INDEX, zip);
        zip = AnimalNurseryWithMethods.byteZip(!isSeek, IS_SEEK_BIT_INDEX, zip);
        zip = AnimalNurseryWithMethods.byteZip(!inNursery, IN_NURSERY_BIT_INDEX, zip);
        out.writeByte(zip);

        if (!nullType) {
            out.writeUTF(type.name());
        }

        if (!nullName) {
            out.writeUTF(name);
        }
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        nursery = (AnimalNurseryExternalizable) in.readObject();
        age = in.readInt();

        byte zip = in.readByte();
        if (AnimalNurseryWithMethods.trueOrNotNullContain(TYPE_BIT_INDEX, zip)) {
            type = AnimalType.valueOf(in.readUTF());
        }

        if (AnimalNurseryWithMethods.trueOrNotNullContain(NAME_BIT_INDEX, zip)) {
            name = in.readUTF();
        }

        isSeek = AnimalNurseryWithMethods.trueOrNotNullContain(IS_SEEK_BIT_INDEX, zip);
        inNursery = AnimalNurseryWithMethods.trueOrNotNullContain(IN_NURSERY_BIT_INDEX, zip);
    }
}
