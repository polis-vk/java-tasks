package ru.mail.polis.homework.io.objects;

import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;

public class ClothesExternalizable implements Externalizable {
    public String brand;
    public int size;

    ClothesExternalizable(String brand, int size){
        this.brand = brand;
        this.size = size;
    }

    public ClothesExternalizable() {
        brand = "Abibas";
        size = 1;
    }

    @Override
    public void writeExternal(ObjectOutput objectOutput) throws IOException {
        objectOutput.writeUTF(brand);
        objectOutput.writeInt(size);
    }

    @Override
    public void readExternal(ObjectInput objectInput) throws IOException, ClassNotFoundException {
        this.brand = objectInput.readUTF();
        this.size = objectInput.readInt();
    }
}
