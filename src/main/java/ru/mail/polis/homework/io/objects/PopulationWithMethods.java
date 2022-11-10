package ru.mail.polis.homework.io.objects;

import java.io.Serializable;
import java.io.IOException;
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
        PopulationWithMethods population = (PopulationWithMethods) o;
        return population.name.equals(name) && population.size == size && population.density == density;
    }

    @Override
    public String toString() {
        return "Population{" +
                "name='" + name + '\'' +
                ", size=" + size + '\'' +
                ", density=" + density +
                '}';
    }

    public void writeObject(ObjectOutputStream oos) throws IOException {
        PopulationByte populationByte = new PopulationByte(this);
        oos.writeByte(populationByte.writeByte());
        if (populationByte.nameIsNotNull()) {
            oos.writeUTF(name);
        }
        oos.writeLong(size);
        oos.writeInt(density);
    }

    public void readObject(ObjectInputStream ois) throws IOException {
        PopulationByte populationByte = new PopulationByte(ois.readByte());
        if (populationByte.nameIsNotNull()) {
            name = ois.readUTF();
        }
        size = ois.readLong();
        density = ois.readInt();
    }

}
