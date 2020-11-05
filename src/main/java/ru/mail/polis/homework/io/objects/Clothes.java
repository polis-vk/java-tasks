package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Clothes implements Serializable {
    private String brand;
    private int size;

    Clothes(String brand, int size){
        this.brand = brand;
        this.size = size;
    }

    public Clothes() { }

    public String getBrand() {
        return brand;
    }

    public int getSize() {
        return size;
    }

    void myWriteObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.writeUTF(brand);
        objectOutputStream.writeInt(size);
    }

    void myReadObject(ObjectInputStream objectInputStream) throws IOException {
        brand = objectInputStream.readUTF();
        size = objectInputStream.readInt();
    }
}

