package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

/**
 * Нужно создать тесты для этого файла
 * 1) тест на чтение и запись пустого списка
 * 2) тест на чтение и запись некоторого списка (список до и после процедуры должны быть равны)
 * 3) тест на чтение и запись некоторых разных списков (одинаковые списки до и после процедуры должны быть равны,
 * а разные списки до и после процедуры должны быть не равны)
 */
public class Serializer {
    private Path filePath;

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        filePath = Path.of(fileName);
        createFile(filePath);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
        filePath = Path.of(fileName);
        if (!Files.exists(filePath)) {
            return Collections.emptyList();
        }
        List<Animal> animalList = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            int count = objectInputStream.readInt();
            for (int i = 0; i < count; i++) {
                Animal animal = (Animal) objectInputStream.readObject();
                animalList.add(animal);
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
        filePath = Path.of(fileName);
        createFile(filePath);
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                outputStream.writeUTF(animal.getName());
                outputStream.writeUTF(animal.getEats().toString());
                outputStream.writeInt(animal.getPersonalNumber());
                outputStream.writeInt(animal.getAge());

                outputStream.writeUTF(animal.getOwner().getName());
                outputStream.writeInt(animal.getOwner().getAge());

                for (Food item : animal.getEaten()) {
                    outputStream.writeUTF(item.getTitle());
                    outputStream.writeDouble(item.getWeight());
                    outputStream.writeUTF(item.getDate().toString());
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
        filePath = Path.of(fileName);
        if (!Files.exists(filePath)) {
            return Collections.emptyList();
        }
        List<Animal> animalList = new ArrayList<>();
        try (DataInputStream dataInputStream = new DataInputStream(Files.newInputStream(filePath))) {
            int count = dataInputStream.readInt();
            for (int i = 0; i < count; i++) {
                String name = dataInputStream.readUTF();
                Eats eats = Eats.valueOf(dataInputStream.readUTF());
                int personalNumber = dataInputStream.readInt();
                int age = dataInputStream.readInt();

                String ownerName = dataInputStream.readUTF();
                int ownerAge = dataInputStream.readInt();
                Owner owner = new Owner(ownerName, ownerAge);

                int listCount = dataInputStream.readInt();
                List<Food> foodList = new ArrayList<>();
                for (int j = 0; i < listCount; i++) {
                    String foodTitle = dataInputStream.readUTF();
                    double foodWeight = dataInputStream.readDouble();
                    Date date = toDate(dataInputStream.readUTF());
                    foodList.add(new Food(foodTitle, foodWeight, date));
                }

                Animal animal = new Animal(name, age, owner, eats);
                animal.setPersonalNumber(personalNumber);
                for (Food item : foodList) {
                    animal.addEaten(item);
                }
                animalList.add(animal);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }
        return animalList;
    }

    private void createFile(Path filePath) {
        if (!Files.exists(filePath)) {
            try {
                Files.createFile(filePath);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private Date toDate(String dateString) {
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        try {

            return formatter.parse(dateString.replaceAll("Z$", "+0000"));

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }
}

