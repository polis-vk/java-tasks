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
    public AnimalExternalizable() {
        super();
    }

    public AnimalExternalizable(String name, Habitat habitat, int weight, int age, boolean isPredator, List<String> food){
        super(name, habitat, weight, age, isPredator, food);
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(getName());
        objectOutput.writeObject(getHabitat());
        objectOutput.writeInt(getWeight());
        objectOutput.writeInt(getAge());
        objectOutput.writeBoolean(isPredator());
        objectOutput.writeObject(getFood());
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        setName(objectInput.readUTF());
        setHabitat((Habitat) objectInput.readObject());
        setWeight(objectInput.readInt());
        setAge(objectInput.readInt());
        setPredator(objectInput.readBoolean());
        setFood((List<String>) objectInput.readObject());
    }
}
