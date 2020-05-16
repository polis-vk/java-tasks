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
        Path dataFile = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(dataFile))) {
            objectOutputStream.writeObject(animals);
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path dataFile = Paths.get(fileName);

        if (Files.notExists(dataFile)) {
            return null;
        }

        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(dataFile))) {
            return (List<Animal>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException ex) {
            return null;
        }
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path dataFile = Paths.get(fileName);

        try {
            if (Files.notExists(dataFile)) {
                Files.createFile(dataFile);
            }

            try (DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileName))) {
                for (Animal animal : animals) {
                    dataOutputStream.writeUTF(animal.getName());
                    dataOutputStream.writeUTF(animal.getBirthdate());
                    dataOutputStream.writeUTF(animal.getArea());
                    dataOutputStream.writeUTF(animal.getType().toString());

                    dataOutputStream.writeUTF(animal.getFather().getName());
                    dataOutputStream.writeUTF(animal.getFather().getBirthdate());
                    dataOutputStream.writeUTF(animal.getFather().getType().toString());

                    dataOutputStream.writeUTF(animal.getMother().getName());
                    dataOutputStream.writeUTF(animal.getMother().getBirthdate());
                    dataOutputStream.writeUTF(animal.getMother().getType().toString());

                    dataOutputStream.writeInt(animal.getVaccinations().size());

                    for (Vaccination vaccination : animal.getVaccinations()) {
                        dataOutputStream.writeUTF(vaccination.getClinic());
                        dataOutputStream.writeUTF(vaccination.getDoctor());
                        dataOutputStream.writeUTF(vaccination.getDate());
                        dataOutputStream.writeUTF(vaccination.getType());
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path dataFile = Paths.get(fileName);

        if (Files.notExists(dataFile)) {
            return null;
        }

        List<Animal> animals = new ArrayList();

        try (DataInputStream dataInputStream = new DataInputStream(new FileInputStream(fileName))) {
            while (dataInputStream.available() > 0) {
                String name = dataInputStream.readUTF();
                String birthdate = dataInputStream.readUTF();
                String area = dataInputStream.readUTF();
                AnimalTypes type = AnimalTypes.valueOf(dataInputStream.readUTF());

                Animal father = new Animal(dataInputStream.readUTF(), dataInputStream.readUTF(), AnimalTypes.valueOf(dataInputStream.readUTF()));
                Animal mother = new Animal(dataInputStream.readUTF(), dataInputStream.readUTF(), AnimalTypes.valueOf(dataInputStream.readUTF()));

                int size = dataInputStream.read();
                List<Vaccination> vaccinations = new ArrayList();
                for (int i = 0; i < size; i++) {
                    vaccinations.add(new Vaccination(dataInputStream.readUTF(), dataInputStream.readUTF(), dataInputStream.readUTF(), dataInputStream.readUTF()));
                }
                animals.add(new Animal(name, birthdate, type, mother, father, vaccinations, null, area));
            }
        } catch (IOException ex) {
            return null;
        }

        return animals;
    }
}
