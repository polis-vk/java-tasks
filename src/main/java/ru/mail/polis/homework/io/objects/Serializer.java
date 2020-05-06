package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Нужно создать тесты для этого файла
 * 1) тест на чтение и запись пустого списка
 * 2) тест на чтение и запись некоторого списка (список до и после процедуры должны быть равны)
 * 3) тест на чтение и запись некоторых разных списков (одинаковые списки до и после процедуры должны быть равны,
 * а разные списки до и после процедуры должны быть не равны)
 */
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath));
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return null;
        }
        List<Animal> animalList = new ArrayList<>();
        try {
            ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath));
            int numberOfAnimals = inputStream.readInt();
            for (int i = 0; i < numberOfAnimals; i++) {
                animalList.add((Animal) inputStream.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        }
        return animalList;
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
        }

        try {
            DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath));
            outputStream.writeInt(animals.size());

            for (Animal animal : animals) {
                outputStream.writeUTF(animal.getNickname());
                outputStream.writeBoolean(animal.isTrained());
                outputStream.writeUTF(animal.getBreed().toString());
                Cattery tmpCattery = animal.getCattery();
                outputStream.writeUTF(tmpCattery.getCatteryName());
                outputStream.writeUTF(tmpCattery.getAddress());
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоровневых потоков
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        Path filePath = Paths.get(fileName);
        if (!Files.exists(filePath)) {
            return null;
        }
        List<Animal> animalList = new ArrayList<>();
        try {
            DataInputStream inputStream = new DataInputStream(Files.newInputStream(filePath));
            int numberOfAnimals = inputStream.readInt();
            for (int i = 0; i < numberOfAnimals; i++) {
                animalList.add(
                        new Animal(
                                inputStream.readUTF(),
                                inputStream.readBoolean(),
                                Breed.valueOf(inputStream.readUTF()),
                                new Cattery(
                                        inputStream.readUTF(),
                                        inputStream.readUTF()
                                )
                        )
                );
            }

        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

        return animalList;
    }

}
