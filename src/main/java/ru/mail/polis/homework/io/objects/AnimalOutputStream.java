package ru.mail.polis.homework.io.objects;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.List;

public class AnimalOutputStream extends FileOutputStream {
    public AnimalOutputStream(File file) throws FileNotFoundException {
        super(file);
    }

    public void writeAnimal(Animal animal) {
        try {
            if (animal == null) {
                write(intToBytes(-1));
                return;
            } else {
                write(intToBytes(1));
            }
            write(intToBytes(animal.countLegs));
            write(intToBytes(animal.isPet ? 1 : 0));
            write(intToBytes(animal.isFly ? 1 : 0));
            if (animal.name != null) {
                write(intToBytes(animal.name.length()));
                write(animal.name.getBytes(StandardCharsets.UTF_8));
            } else {
                write(intToBytes(-1));
            }
            write(intToBytes(animal.moveType.ordinal()));
            if (animal.population != null) {
                write(intToBytes(1));
                write(intToBytes(animal.population.mainland.ordinal()));
                write(longToBytes(animal.population.size));
            } else {
                write(intToBytes(-1));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private byte[] longToBytes(long number) {
        return new byte[]{
                (byte) number,
                (byte) (number >>> 8),
                (byte) (number >>> 16),
                (byte) (number >>> 24),
                (byte) (number >>> 32),
                (byte) (number >>> 40),
                (byte) (number >>> 48),
                (byte) (number >>> 56)};
    }

    private byte[] intToBytes(int number) {
        return new byte[]{
                (byte) number,
                (byte) (number >>> 8),
                (byte) (number >>> 16),
                (byte) (number >>> 24)};
    }

    public void writeAnimals(List<Animal> animalList) throws IOException {
        for (Animal animal : animalList) {
            writeAnimal(animal);
        }
    }
}
