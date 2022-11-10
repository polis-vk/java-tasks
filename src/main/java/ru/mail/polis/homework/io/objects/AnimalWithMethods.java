package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods extends Animal implements Serializable {

    public AnimalWithMethods() {
        super();
    }

    public AnimalWithMethods(boolean isAlive, boolean isExotic, short legs, String voice, AnimalSex sex, Zoo zoo) {
        super(isAlive, isExotic, legs, voice, sex, zoo);
    }


    private void writeObject(ObjectOutputStream out) throws IOException {
        out.writeBoolean(this.isAlive());
        out.writeBoolean(this.isExotic());
        out.writeShort(this.getLegs());
        out.writeUTF(this.getVoice());
        out.writeUTF(this.getSex().name());
        out.writeObject(this.getZoo());
    }

    private void readObject(ObjectInputStream in) throws IOException, ClassNotFoundException {
        this.setAlive(in.readBoolean());
        this.setExotic(in.readBoolean());
        this.setLegs(in.readShort());
        this.setVoice(in.readUTF());
        this.setSex(AnimalSex.valueOf(in.readUTF()));
        this.setZoo((Zoo) in.readObject());
    }
}
