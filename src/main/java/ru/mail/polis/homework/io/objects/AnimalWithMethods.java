package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 балла
 */
public class AnimalWithMethods implements Serializable {
    private int age;
    private boolean isFriendly;
    private String name;
    private List<Integer> weightByLastTenDays;
    private Kind kind;
    private Animal.Owner owner;
    
    public AnimalWithMethods(int age, boolean isFriendly, String name, List<Integer> weightByLastTenDays, Kind kind, Animal.Owner owner) {
        this.age = age;
        this.isFriendly = isFriendly;
        this.name = name;
        this.weightByLastTenDays = weightByLastTenDays;
        this.kind = kind;
        this.owner = owner;
    }
    
    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeInt(age);
        out.writeBoolean(isFriendly);
        out.writeUTF(name);
        
        out.writeInt(weightByLastTenDays.size());
        for (Integer weight : weightByLastTenDays) {
            out.writeInt(weight);
        }
        out.writeUTF(kind.getName());
        out.writeLong(kind.getPopulationSize());
        out.writeInt(owner.ordinal());
    }
    
    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        isFriendly = in.readBoolean();
        name = in.readUTF();
        int daysListSize = in.readInt();
        weightByLastTenDays = new ArrayList<>(daysListSize);
        for (int j = 0; j < daysListSize; j++) {
            weightByLastTenDays.add(in.readInt());
        }
        kind = new Kind(in.readUTF(), in.readLong());
        owner = Animal.Owner.values()[in.readInt()];
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalWithMethods that = (AnimalWithMethods) o;
        return age == that.age &&
                isFriendly == that.isFriendly &&
                Objects.equals(name, that.name) &&
                Objects.equals(weightByLastTenDays, that.weightByLastTenDays) &&
                Objects.equals(kind, that.kind) &&
                owner == that.owner;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(age, isFriendly, name, weightByLastTenDays, kind, owner);
    }
}
