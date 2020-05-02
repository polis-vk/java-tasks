package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

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
                outputStream.print('\n');
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
            while (scanner.hasNext()) {
                animals.add(parseAnimal(scanner.nextLine()));
            }
        }
        return animals;
    }

    private Animal parseAnimal(String animal) {
        if (animal.substring(1, animal.length() - 1).equals("null")) {
            return null;
        }
        int firstIndex = animal.indexOf("=") + 1;
        int lastIndex = animal.indexOf(",");
        String name = animal.substring(firstIndex, lastIndex);
        animal = animal.substring(lastIndex + 1);

        firstIndex = animal.indexOf("=") + 1;
        lastIndex = animal.indexOf(",");
        int age = Integer.parseInt(animal.substring(firstIndex, lastIndex));
        animal = animal.substring(lastIndex + 1);

        firstIndex = animal.indexOf("=") + 1;
        lastIndex = animal.indexOf(",");
        Type type = parseType(animal.substring(firstIndex, lastIndex));

        return new Animal(name, age, null, null, type);
    }
}
