package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class PopulationExternalizable implements Externalizable {
    private String name;
    private long size;
    private int density;

    public PopulationExternalizable(String name, long size, int density) {
        this.name = name;
        this.size = size;
        this.density = density;
    }

    public PopulationExternalizable() {
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
        PopulationExternalizable population = (PopulationExternalizable) o;
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

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        PopulationByte populationByte = new PopulationByte(this);
        out.writeByte(populationByte.writeByte());
        if (populationByte.nameIsNotNull()) {
            out.writeUTF(name);
        }
        out.writeLong(size);
        out.writeInt(density);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException {
        PopulationByte populationByte = new PopulationByte(in.readByte());
        if (populationByte.nameIsNotNull()) {
            name = in.readUTF();
        }
        size = in.readLong();
        density = in.readInt();
    }

}
