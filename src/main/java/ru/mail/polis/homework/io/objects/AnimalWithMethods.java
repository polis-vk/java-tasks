package ru.mail.polis.homework.io.objects;


import java.io.Serializable;

/**
 * Дубль класса Animal, для Serializer.serializeWithMethods
 * 3 тугрика
 */
public class AnimalWithMethods implements Serializable {

    public void writeObject(Object obj) {

    }

    public Object readObject() {
        return 0;
    }
}
