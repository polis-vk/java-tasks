package ru.mail.polis.homework.io.objects;

import ru.mail.polis.homework.io.objects.Animal;

public class NullAnimal extends Animal {
    private final static Animal nullParent = new NullAnimal();

    public static Animal getInstance(){
        return nullParent;
    }

    private NullAnimal() {
        super("NaN", 0, false, false, null, null, null);
    }
    @Override
    public boolean equals(Object o) {
        if (o != null && o.getClass() == getClass()) {
            return true;
        }
        return false;
    }
}