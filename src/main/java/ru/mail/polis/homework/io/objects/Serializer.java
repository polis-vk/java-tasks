package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static ru.mail.polis.homework.io.objects.Type.parseType;

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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        Path outputFile = Paths.get(fileName);
        if (Files.notExists(outputFile)) {
            Files.createFile(outputFile);
        }

        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(outputFile))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path outputFile = Paths.get(fileName);
        if (Files.notExists(outputFile)) {
            Files.createFile(outputFile);
        }

        try (PrintStream outputStream = new PrintStream(Files.newOutputStream(outputFile))) {
            for (Animal animal : animals) {
                outputStream.print(animal);
                outputStream.print(';');
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        Path inputFile = Paths.get(fileName);
        if (Files.notExists(inputFile)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();
        try (Scanner scanner = new Scanner(Files.newInputStream(inputFile))) {
            if (!scanner.hasNext()) {
                return animals;
            }
            String[] inputString = scanner.nextLine().split(";");
            Animal animalObj;
            for (String animal : inputString) {
                animalObj = parseAnimal(animal);
                animals.add(animalObj);
            }
        }
        return animals;
    }

    private Animal parseAnimal(String animal) {
        Animal dad = null;
        Animal mum = null;
        // TODO: 01.05.2020 изменить парсинг строки, чтобы обработанное удалялось
        int firstIndex = animal.indexOf('\'') + 1;
        int lastIndex = animal.indexOf('\'', firstIndex);
        String name = animal.substring(firstIndex, lastIndex);

        firstIndex = animal.indexOf('=', lastIndex) + 1;
        lastIndex = animal.indexOf(',', firstIndex);
        int age = Integer.parseInt(animal.substring(firstIndex, lastIndex));

        firstIndex = animal.indexOf('{', lastIndex) + 1;
        lastIndex = animal.indexOf('}', firstIndex) + 1;
        if (firstIndex != 0) {
            dad = parseAnimal(animal.substring(firstIndex, lastIndex));
        }

        firstIndex = animal.indexOf('{', lastIndex) + 1;
        lastIndex = animal.indexOf('}', firstIndex) + 1;
        if (firstIndex != 0) {
            mum = parseAnimal(animal.substring(firstIndex, lastIndex));
        }

        lastIndex = animal.lastIndexOf('}');
        firstIndex = animal.lastIndexOf('=') + 1;
        Type type = parseType(animal.substring(firstIndex, lastIndex));

        return new Animal(name, age, dad, mum, type);
    }
}
