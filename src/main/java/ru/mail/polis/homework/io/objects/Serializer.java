package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

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
        Path pathFile = Paths.get(fileName);
        if (Files.notExists(pathFile)) {
            try {
                Files.createFile(pathFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(pathFile))) {
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }

        List<Animal> animalsList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                animalsList.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalsList;
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path pathFile = Paths.get(fileName);
        if (Files.notExists(pathFile)) {
            try {
                Files.createFile(pathFile);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (PrintStream printStream = new PrintStream(Files.newOutputStream(pathFile))) {
            for (Animal animal : animals) {
                printStream.println(animal);
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
        Path path = Paths.get(fileName);
        if (Files.notExists(path)) {
            return Collections.emptyList();
        }

        List<Animal> animalList = new ArrayList<>();
        try (Scanner scanner = new Scanner(Files.newInputStream(path))) {
            {
                while (scanner.hasNext()) {
                    animalList.add(parseAnimal(scanner.nextLine()));
                }
            }
            return animalList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static Animal parseAnimal(String line) {
        if (line.equals("null")) {
            return null;
        }

        int startIndex = line.indexOf('=') + 1;
        int endIndex = line.indexOf(',');
        int age = Integer.parseInt(line.substring(startIndex, endIndex));

        line = line.substring(endIndex + 1);
        startIndex = line.indexOf("=") + 1;
        endIndex = line.indexOf(",");
        String name = line.substring(startIndex, endIndex);

        line = line.substring(endIndex + 1);
        startIndex = line.indexOf("=") + 1;
        endIndex = line.indexOf(",");
        if (!line.substring(startIndex, endIndex).equals("null")) {
            endIndex = line.indexOf("}") + 1;
        }
        Animal mom = parseAnimal(line.substring(startIndex, endIndex));

        line = line.substring(endIndex + 1);
        startIndex = line.indexOf("=") + 1;
        endIndex = line.indexOf(",");
        if (!line.substring(startIndex, endIndex).equals("null")) {
            endIndex = line.indexOf("}") + 1;
        }
        Animal dad = parseAnimal(line.substring(startIndex, endIndex));

        line = line.substring(endIndex + 1);
        startIndex = line.indexOf("=") + 1;
        endIndex = line.indexOf("}");
        Type type = Type.valueOf(line.substring(startIndex, endIndex));

        return new Animal(age, name, mom, dad, type);
    }
}
