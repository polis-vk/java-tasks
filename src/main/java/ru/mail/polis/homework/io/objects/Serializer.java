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
        Path outputFile = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(outputFile))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
        Path inputFile = Paths.get(fileName);
        if (Files.notExists(inputFile)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(inputFile))) {
            int length = objectInputStream.readInt();
            for (int i = 0; i < length; i++) {
                animals.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path outputFile = Paths.get(fileName);

        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(outputFile))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                animalSerialize(outputStream, animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void animalSerialize(DataOutputStream outputStream, Animal animal) throws IOException {
        if (animal == null) {
            outputStream.writeInt(0);
            return;
        }
        outputStream.writeInt(1);
        outputStream.writeUTF(animal.getName());
        outputStream.writeInt(animal.getAge());
        animalSerialize(outputStream, animal.getDad());
        animalSerialize(outputStream, animal.getMum());
        outputStream.writeUTF(animal.getType().toString());
        outputStream.writeInt(animal.foods.size());
        for (Food food : animal.getFoods()) {
            outputStream.writeUTF(food.name);
            outputStream.writeInt(food.calorie);
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
        Path inputFile = Paths.get(fileName);
        if (Files.notExists(inputFile)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();

        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(inputFile))) {
            int length = inputStream.readInt();
            for (int i = 0; i < length; i++) {
                animals.add(animalDeserialize(inputStream));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private Animal animalDeserialize(DataInputStream inputStream) throws IOException {
        if (inputStream.readInt() == 0) {
            return null;
        }

        String name = inputStream.readUTF();
        int age = inputStream.readInt();
        Animal dad = animalDeserialize(inputStream);
        Animal mum = animalDeserialize(inputStream);
        Type type = Type.valueOf(inputStream.readUTF());
        Animal animal = new Animal(name, age, dad, mum, type);
        int length = inputStream.readInt();
        String nameFood;
        int calorie;
        for (int i = 0; i < length; i++) {
            nameFood = inputStream.readUTF();
            calorie = inputStream.readInt();
            animal.foods.add(new Food(nameFood, calorie));
        }
        return animal;
    }
}
