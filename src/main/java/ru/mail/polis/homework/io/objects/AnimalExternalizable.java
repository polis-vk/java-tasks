package ru.mail.polis.homework.io.objects;


import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.util.List;

/**
 * Дубль класса Animal, для Serializer.serializeWithExternalizable
 * 3 балла
 */
public class AnimalExternalizable extends Animal implements Externalizable {

    public AnimalExternalizable(int age, String name, Habitat habitat, List<String> food,
                                boolean sexIsMale, double height, Heart heart) {
        super(age, name, habitat, food, sexIsMale, height, heart);
    }

    public AnimalExternalizable() {
        super();
    }

    @Override
    public void writeExternal(ObjectOutput out) throws IOException {
        out.writeInt(age);
        out.writeUTF(name);
        out.writeObject(habitat);
        out.writeObject(food);
        out.writeBoolean(sexIsMale);
        out.writeDouble(height);
        out.writeObject(heart);
    }

    @Override
    public void readExternal(ObjectInput in) throws IOException, ClassNotFoundException {
        age = in.readInt();
        name = in.readUTF();
        habitat = (Habitat) in.readObject();
        food = (List<String>) in.readObject();
        sexIsMale = in.readBoolean();
        height = in.readDouble();
        heart = (Heart) in.readObject();
    }

}
