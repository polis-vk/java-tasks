package ru.mail.polis.homework.io.objects;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import ru.mail.polis.homework.oop.vet.MoveType;

public class AnimalInputStream extends FileInputStream {

    static final byte[] BUFFER_4_BYTES = new byte[4];
    static final byte[] BUFFER_8_BYTES = new byte[8];
    List<Animal> animalList = new ArrayList<>();

    public AnimalInputStream(File file) throws FileNotFoundException {
        super(file);
    }

    public Animal readAnimal() throws IOException {
        if (readInt() == -1) {
            animalList.add(null);
            return null;
        }
        Animal animal = new Animal();
        animal.setCountLegs(readInt());
        animal.setPet(readInt() == 1);
        animal.setFly(readInt() == 1);
        animal.setName(readName());
        animal.setMoveType(MoveType.values()[readInt()]);
        int buf = readInt();
        if (buf == -1) {
            animal.setPopulation(null);
        } else {
            animal.setPopulation(new Population(Mainland.values()[readInt()], readLong()));
        }
        animalList.add(animal);
        return animal;
    }

    private long bytesToLong(byte[] bytes) {
        return ((long) bytes[7] << 56)
                | ((long) bytes[6] & 0xff) << 48
                | ((long) bytes[5] & 0xff) << 40
                | ((long) bytes[4] & 0xff) << 32
                | ((long) bytes[3] & 0xff) << 24
                | ((long) bytes[2] & 0xff) << 16
                | ((long) bytes[1] & 0xff) << 8
                | ((long) bytes[0] & 0xff);
    }

    private int bytesToInt(byte[] bytes) {
        return ((int) bytes[3] << 24)
                | ((int) bytes[2] & 0xff) << 16
                | ((int) bytes[1] & 0xff) << 8
                | ((int) bytes[0] & 0xff);
    }

    private int readInt() throws IOException {
        read(BUFFER_4_BYTES);
        return bytesToInt(BUFFER_4_BYTES);
    }

    private Long readLong() throws IOException {
        read(BUFFER_8_BYTES);
        return bytesToLong(BUFFER_8_BYTES);
    }

    private String readString(int length) throws IOException {
        byte[] buffer = new byte[length];
        read(buffer);
        return new String(buffer, StandardCharsets.UTF_8);
    }

    private String readName() throws IOException {
        int nameLength = readInt();
        if (nameLength == -1) {
            return null;
        }
        return readString(nameLength);
    }

    public List<Animal> readAnimals() throws IOException {
        while (available() > 0) {
            readAnimal();
        }
        return animalList;
    }
}
