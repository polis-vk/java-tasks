package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ParentsExternalizable implements Externalizable {
    private String mother;
    private String father;

    ParentsExternalizable() {}

    ParentsExternalizable(String mother, String father) {
        this.mother = mother;
        this.father = father;
    }

    public String getMother() {
        return mother;
    }

    public String getFather() {
        return father;
    }

    @Override
    public String toString() {
        return '{' +
                "mother='" + mother + '\'' +
                ", father'" + father + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || obj.getClass() != this.getClass()) {
            return false;
        }

        ParentsExternalizable parents = (ParentsExternalizable) obj;
        return mother.equals(parents.mother) &&
                father.equals(parents.father);
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeUTF(mother);
        out.writeUTF(father);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        mother = in.readUTF();
        father = in.readUTF();
    }
}
