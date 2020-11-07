package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable implements Externalizable {
    private int age;
    private boolean isFriendly;
    private String name;
    private List<Integer> weightByLastDays;
    private Kind kind;
    private Owner owner;
    
    public AnimalExternalizable() {
    }
    
    public AnimalExternalizable(int age, boolean isFriendly, String name, List<Integer> weightByLastDays, Kind kind, Owner owner) {
        this.age = age;
        this.isFriendly = isFriendly;
        this.name = name;
        this.weightByLastDays = weightByLastDays;
        this.kind = kind;
        this.owner = owner;
    }
    
    public int getAge() {
        return age;
    }
    
    public boolean isFriendly() {
        return isFriendly;
    }
    
    public String getName() {
        return name;
    }
    
    public List<Integer> getWeightByLastDays() {
        return weightByLastDays;
    }
    
    public Kind getKind() {
        return kind;
    }
    
    public Owner getOwner() {
        return owner;
    }
    
    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeBoolean(isFriendly);
        out.writeUTF(name);
        
        out.writeInt(weightByLastDays.size());
        for (Integer weight : weightByLastDays) {
            out.writeInt(weight);
        }
        out.writeUTF(kind.getName());
        out.writeLong(kind.getPopulationSize());
        out.writeUTF(owner.name());
    }
    
    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        isFriendly = in.readBoolean();
        name = in.readUTF();
        int daysListSize = in.readInt();
        weightByLastDays = new ArrayList<>(daysListSize);
        for (int j = 0; j < daysListSize; j++) {
            weightByLastDays.add(in.readInt());
        }
        kind = new Kind(in.readUTF(), in.readLong());
        owner = Owner.valueOf(in.readUTF());
    }
    
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AnimalExternalizable that = (AnimalExternalizable) o;
        return age == that.age &&
                isFriendly == that.isFriendly &&
                Objects.equals(name, that.name) &&
                Objects.equals(weightByLastDays, that.weightByLastDays) &&
                Objects.equals(kind, that.kind) &&
                owner == that.owner;
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(age, isFriendly, name, weightByLastDays, kind, owner);
    }
}
