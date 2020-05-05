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
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutput objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
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
        if (Files.notExists(Paths.get(fileName))) {
            return animals;
        }
        try (ObjectInput objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                Animal animal = (Animal) objectInputStream.readObject();
                animals.add(animal);
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
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            dos.writeInt(animals.size());
            for (Animal animal : animals) {
                dos.writeInt(animal.getAge());
                dos.writeUTF(animal.getName());
                
                List<Integer> weightByLastTenDays = animal.getWeightByLastTenDays();
                dos.writeInt(weightByLastTenDays.size());
                for (Integer weight : weightByLastTenDays) {
                    dos.writeInt(weight);
                }
                
                Kind kind = animal.getKind();
                dos.writeUTF(kind.getName());
                dos.writeLong(kind.getPopulationSize());
                
                dos.writeInt(animal.getOwner().ordinal());
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
        if (Files.notExists(Paths.get(fileName))) {
            return animals;
        }
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
            int animalsSize = dis.readInt();
            for (int i = 0; i < animalsSize; i++) {
                int age = dis.readInt();
                String name = dis.readUTF();
                List<Integer> weightByLastTenDays = new ArrayList<>();
                int daysListSize = dis.readInt();
                for (int j = 0; j < daysListSize; j++) {
                    weightByLastTenDays.add(dis.readInt());
                }
                
                String kindName = dis.readUTF();
                long populationSize = dis.readLong();
                Kind kind = new Kind(kindName, populationSize);
                
                Animal.Owner owner = Animal.Owner.values()[dis.readInt()];
                
                animals.add(new Animal(age, name, weightByLastTenDays, kind, owner));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
