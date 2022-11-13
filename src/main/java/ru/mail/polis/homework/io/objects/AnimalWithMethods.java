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
    private static final int TYPE_BIT_INDEX = 0;
    private static final int NAME_BIT_INDEX = 1;
    private static final int IS_SEEK_BIT_INDEX = 2;
    private static final int IN_NURSERY_BIT_INDEX = 3;

    private AnimalNurseryWithMethods nursery;
    private int age;
    private AnimalType type;
    private String name;
    private boolean isSeek;
    private boolean inNursery;

    public AnimalWithMethods(AnimalNurseryWithMethods nursery, AnimalType type, String name, int age, boolean isSeek, boolean inNursery) {
        this.nursery = nursery;
        this.age = age;
        this.type = type;
        this.name = name;
        this.isSeek = isSeek;
        this.inNursery = inNursery;
    }

    public int getAge() {
        return age;
    }

    public String getName() {
        return name;
    }

    @Override
    public int hashCode() {
        return Objects.hash(nursery, type, name, age, isSeek, inNursery);
    }

    @Override
    public String toString() {
        return "{\"AnimalNurseryWithMethods\":" + nursery + "," +
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

        AnimalWithMethods animal = (AnimalWithMethods) obj;
        return Objects.equals(nursery, animal.nursery) &&
                Objects.equals(type, animal.type) &&
                Objects.equals(name, animal.name) &&
                age == animal.age &&
                isSeek == animal.isSeek &&
                inNursery == animal.inNursery;
    }

    private void writeObject(ObjectOutputStream out) throws IOException {
        boolean nullName = name == null;
        boolean nullType = type == null;

        out.writeObject(nursery);
        out.writeInt(age);

        byte zip = Animal.byteZip(nullType, TYPE_BIT_INDEX, (byte) 0);
        zip = Animal.byteZip(nullName, NAME_BIT_INDEX, zip);
        zip = Animal.byteZip(!isSeek, IS_SEEK_BIT_INDEX, zip);
        zip = Animal.byteZip(!inNursery, IN_NURSERY_BIT_INDEX, zip);
        out.writeByte(zip);

        if (!nullType) {
            out.writeUTF(type.name());
        }

        if (!nullName) {
            out.writeUTF(name);
        }
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        nursery = (AnimalNurseryWithMethods) in.readObject();
        age = in.readInt();

        byte zip = in.readByte();
        if (Animal.trueOrNotNullContain(TYPE_BIT_INDEX, zip)) {
            type = AnimalType.valueOf(in.readUTF());
        }

        if (Animal.trueOrNotNullContain(NAME_BIT_INDEX, zip)) {
            name = in.readUTF();
        }

        isSeek = Animal.trueOrNotNullContain(IS_SEEK_BIT_INDEX, zip);
        inNursery = Animal.trueOrNotNullContain(IN_NURSERY_BIT_INDEX, zip);
    }
}
