package ru.mail.polis.homework.io.objects;

import javax.xml.crypto.Data;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


// * Нужно создать тесты для этого файла
// * 1) тест на чтение и запись пустого списка
// * 2) тест на чтение и запись некоторого списка (список до и после процедуры должны быть равны)
// * 3) тест на чтение и запись некоторых разных списков (одинаковые списки до и после процедуры должны быть равны,
// * а разные списки до и после процедуры должны быть не равны)
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeObject(animals);
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
    public static List<Animal> defaultDeserialize(String fileName) {
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return (List<Animal>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public static void customSerialize(List<Animal> animals, String fileName) {
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeUTF(animal.getName());
                outputStream.writeInt(animal.getAge());
                outputStream.writeBoolean(animal.isFlying());
                outputStream.writeBoolean(animal.isSwimming());
                outputStream.writeUTF(animal.getFood().toString());
                if (!animal.getMom().equals(NullAnimal.getInstance())) {
                    outputStream.writeBoolean(true);                    //пишем маму
                    Animal mom = animal.getMom();
                    outputStream.writeUTF(mom.getName());
                    outputStream.writeInt(mom.getAge());
                    outputStream.writeBoolean(mom.isFlying());
                    outputStream.writeBoolean(mom.isSwimming());
                    outputStream.writeUTF(mom.getFood().toString());
                } else {
                    outputStream.writeBoolean(false);                   //не пишем маму
                }
                if (!animal.getDad().equals(NullAnimal.getInstance())) {
                    outputStream.writeBoolean(true);                    //пишем папу
                    Animal dad = animal.getDad();
                    outputStream.writeUTF(dad.getName());
                    outputStream.writeInt(dad.getAge());
                    outputStream.writeBoolean(dad.isFlying());
                    outputStream.writeBoolean(dad.isSwimming());
                    outputStream.writeUTF(dad.getFood().toString());
                } else {
                    outputStream.writeBoolean(false);                   //не пишем папу
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
    public static List<Animal> customDeserialize(String fileName) {
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            int count = inputStream.readInt();
            for (int i = 0; i < count; i++) {
                String name = inputStream.readUTF();
                int age = inputStream.readInt();
                boolean fly = inputStream.readBoolean();
                boolean swim = inputStream.readBoolean();
                Food food = Food.valueOf(inputStream.readUTF());
                Animal mom = NullAnimal.getInstance();
                if (inputStream.readBoolean()) {
                    mom = new Animal(
                            inputStream.readUTF(),
                            inputStream.readInt(),
                            inputStream.readBoolean(),
                            inputStream.readBoolean(),
                            Food.valueOf(inputStream.readUTF()),
                            NullAnimal.getInstance(),
                            NullAnimal.getInstance()
                    );
                }
                Animal dad = NullAnimal.getInstance();
                if (inputStream.readBoolean()) {
                    dad = new Animal(
                            inputStream.readUTF(),
                            inputStream.readInt(),
                            inputStream.readBoolean(),
                            inputStream.readBoolean(),
                            Food.valueOf(inputStream.readUTF()),
                            NullAnimal.getInstance(),
                            NullAnimal.getInstance()
                    );
                }
                animals.add(new Animal(name, age, fly, swim, food, mom, dad));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        crossLinks(animals);
        return animals;
    }

    private static void crossLinks(List<Animal> animals) {          //востанавливаем перекрестные ссылки
        for (Animal animal : animals) {
            for (Animal animal2 : animals) {
                animal.changeParentLink(animal2);
            }
        }
    }

}