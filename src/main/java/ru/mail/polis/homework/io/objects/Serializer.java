package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

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
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(pathFile))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                writeAnimal(animal, outputStream);
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
        try (DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(path))) {
            {
                int size = dataInputStream.readInt();
                for (int i = 0; i < size; i++) {
                    animalList.add(parseAnimal(dataInputStream));
                }
            }
            return animalList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeAnimal(Animal animal, DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(animal.getAge());
        outputStream.writeUTF(animal.getName());
        writeParent(animal.getMom(), outputStream);
        writeParent(animal.getDad(), outputStream);
        outputStream.writeUTF(animal.getType().toString());
    }

    private static void writeParent(Animal animal, DataOutputStream outputStream) throws IOException {
        if (animal == null) {
            outputStream.writeBoolean(false);
            return;
        }
        outputStream.writeBoolean(true);
        writeAnimal(animal, outputStream);
    }

    private static Animal parseAnimal(DataInputStream dataInputStream) throws IOException {
        int age = dataInputStream.readInt();
        String name = dataInputStream.readUTF();
        Animal mom = null;
        if (dataInputStream.readBoolean()) {
            mom = parseAnimal(dataInputStream);
        }
        Animal dad = null;
        if (dataInputStream.readBoolean()) {
            dad = parseAnimal(dataInputStream);
        }
        Type type = Type.valueOf(dataInputStream.readUTF());
        return new Animal(age, name, mom, dad, type);
    }
}
