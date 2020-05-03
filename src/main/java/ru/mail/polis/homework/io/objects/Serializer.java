package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;
import java.util.stream.Stream;

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
        Path pathFile = Paths.get(fileName);
        if (Files.notExists(pathFile)) {
            Files.createFile(pathFile);
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(pathFile))) {
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }

        List<Animal> animalsList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                animalsList.add((Animal) objectInputStream.readObject());
            }
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path pathFile = Paths.get(fileName);
        if (Files.notExists(pathFile)) {
            Files.createFile(pathFile);
        }
        try (PrintStream printStream = new PrintStream(Files.newOutputStream(pathFile))) {
            for (Animal animal : animals) {
                printStream.println(animal);
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
//        if (Files.notExists(Paths.get(fileName))) {
          return Collections.emptyList();
//        }
//
//        List<Animal> animalList = new ArrayList<>();
//        try (Scanner scanner = new Scanner(Files.newInputStream(Paths.get(fileName)))) {
//            {
//                if (scanner.hasNext()) {
//                    animalList.add(scanner.nextLine())
//                }
//            }
//            return animalList;
//        }
    }
}
