package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
        Path pathToFile = Paths.get(fileName);

        try {
            Files.createDirectories(pathToFile.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(pathToFile))) {
            outputStream.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
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
        Path pathToFile = Paths.get(fileName);
        if (!Files.exists(pathToFile)) {
            return null;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(pathToFile))) {
            return (List<Animal>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {

        try {
            Files.createDirectories(Paths.get(fileName).getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {

                outputStream.writeUTF(animal.getName());
                outputStream.writeInt((animal.getType().ordinal()));
                outputStream.writeUTF(animal.getOwner().getName());
                outputStream.writeUTF(animal.getOwner().getPhoneNumber());
            }

        } catch (IOException e) {
            e.printStackTrace();
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

        if (!Files.exists(Paths.get(fileName))) {
            return null;
        }

        List<Animal> animals = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            while (inputStream.available() > 0) {

                String name = inputStream.readUTF();
                int type = inputStream.readInt();
                String nameOwner = inputStream.readUTF();
                String phoneOwner = inputStream.readUTF();
                animals.add(new Animal(
                        name,
                        AnimalType.values()[type],
                        new AnimalOwner(nameOwner, phoneOwner)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
