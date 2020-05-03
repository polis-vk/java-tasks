package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
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
    public static void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            Files.createDirectories(Paths.get(fileName).getParent());
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeObject(animals);
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return (List<Animal>) inputStream.readObject();
        }
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public static void customSerialize(List<Animal> animals, String fileName) throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            Files.createDirectories(Paths.get(fileName).getParent());
        }

        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {

                outputStream.writeUTF(animal.getName());
                outputStream.writeInt((animal.getType()));
                outputStream.writeUTF(animal.getOwner().getName());
                outputStream.writeUTF(animal.getOwner().getPhoneNumber());
            }

        }
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоровневых потоков
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<Animal> customDeserialize(String fileName) throws IOException {
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
        }
        return animals;
    }
}
