package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.Objects;

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

    private void writeObject(ObjectOutputStream objectOutputStream) throws IOException {
        objectOutputStream.defaultWriteObject();
    }

    private void readObject(ObjectInputStream objectInputStream) throws IOException, ClassNotFoundException {
        objectInputStream.defaultReadObject();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Clothes clothes = (Clothes) o;
        return size == clothes.size &&
                brand.equals(clothes.brand);
    }

    @Override
    public int hashCode() {
        return Objects.hash(brand, size);
    }
}

