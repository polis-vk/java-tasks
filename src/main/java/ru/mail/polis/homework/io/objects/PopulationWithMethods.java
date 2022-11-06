package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

public class PopulationWithMethods implements Serializable {
    private String name;
    private long size;
    private int density;

    public PopulationWithMethods(String name, long size, int density) {
        this.name = name;
        this.size = size;
        this.density = density;
    }


    public String getName() {
        return name;
    }

    public long getSize() {
        return size;
    }

    public int getDensity() {
        return density;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        PopulationWithMethods that = (PopulationWithMethods) o;
        return that.getName().equals(getName()) && that.getSize() == getSize() && that.getDensity() == getDensity();
    }

    @Override
    public String toString() {
        return "Population{" +
                "name='" + name + '\'' +
                ", size=" + size + '\'' +
                ", density=" + density +
                '}';
    }

    public void writeObject(ObjectOutputStream out) throws IOException {
        writeString(out, name);
        out.writeLong(size);
        out.writeInt(density);
    }

    public void readObject(ObjectInputStream in) throws IOException {
        name = readString(in);
        size = in.readLong();
        density = in.readInt();
    }

    private static void writeString(ObjectOutput out, String str) throws IOException {
        if (str == null) {
            out.writeByte(0);
        } else {
            out.writeByte(1);
            out.writeUTF(str);
        }
    }

    private static String readString(ObjectInput in) throws IOException {
        if (in.readByte() == 0) {
            return null;
        }
        return in.readUTF();
    }

}
