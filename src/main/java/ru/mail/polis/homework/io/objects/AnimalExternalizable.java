package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 тугрика
 */
public class AnimalExternalizable extends Animal implements Externalizable {

    public AnimalExternalizable() {
        super();
    }

    public AnimalExternalizable(boolean isAlive, boolean isExotic, short legs, String voice, AnimalSex sex, Zoo zoo) {
        super(isAlive, isExotic, legs, voice, sex, zoo);
    }


    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeBoolean(this.isAlive());
        out.writeBoolean(this.isExotic());
        out.writeShort(this.getLegs());
        out.writeUTF(this.getVoice());
        out.writeUTF(this.getSex().name());
        out.writeObject(this.getZoo());
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        this.setAlive(in.readBoolean());
        this.setExotic(in.readBoolean());
        this.setLegs(in.readShort());
        this.setVoice(in.readUTF());
        this.setSex(AnimalSex.valueOf(in.readUTF()));
        this.setZoo((Zoo) in.readObject());
    }
}
