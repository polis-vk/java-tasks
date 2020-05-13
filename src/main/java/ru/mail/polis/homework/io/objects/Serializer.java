package ru.mail.polis.homework.io.objects;

import javax.xml.crypto.Data;
import java.io.*;
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
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
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
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
        try (DataOutputStream dataOutputStream = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            dataOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                dataOutputStream.writeInt(animal.getAge());
                dataOutputStream.writeUTF(animal.getName());
                dataOutputStream.writeInt(animal.getBirthday());
                dataOutputStream.writeUTF(animal.getType().name());

                List<Rewards> rewards = animal.getRewards();
                for (Rewards reward : rewards) {
                    dataOutputStream.writeInt(reward.getDate());
                    dataOutputStream.writeInt(reward.getPlace());
                    dataOutputStream.writeUTF(reward.getNameOfTournament());
                }
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
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream dataInputStream = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            int size = dataInputStream.readInt();
            for (int i = 0; i < size; i++) {
                int age = dataInputStream.readInt();
                String name = dataInputStream.readUTF();
                int birthday = dataInputStream.readInt();
                AnimalTypes type = AnimalTypes.valueOf(dataInputStream.readUTF());

                List<Rewards> rewards = new ArrayList<>();
                int sizeRewards = dataInputStream.readInt();
                for (int j = 0; j < sizeRewards; j++) {
                    int date = dataInputStream.readInt();
                    int place = dataInputStream.readInt();
                    String nameOfTournament = dataInputStream.readUTF();
                    rewards.add(new Rewards(date, place, nameOfTournament));
                }
                Animal animal = new Animal(age, name, birthday, type, rewards);
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
