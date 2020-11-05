package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {
    public static void main(String[] args) {
        Animal animal = new Animal("Hoba", 10, 100, AnimalType.HERBIVOUS, new Person("Alex", 30), Collections.singletonList(new Food("Milk", 300)));
        List<Animal> list = Collections.singletonList(animal);
    }
    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName))))
        {
            stream.writeObject(animals);
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
        List<Animal> list = new ArrayList<>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            list = (List<Animal>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeInt(animals.size());
            animals.forEach(animal -> animal.writeObject(stream));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        List<AnimalWithMethods> list = new ArrayList<>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int amount = stream.readInt();
            for (int i = 0; i < amount; ++i) {
                AnimalWithMethods animal = new AnimalWithMethods();
                animal.ReadObject(stream);
                list.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        List<AnimalExternalizable> list = new ArrayList<>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            list = (List<AnimalExternalizable>) stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        try(DataOutputStream stream = new DataOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeInt(animals.size());
            animals.forEach(animal -> {
                try {
                    stream.writeUTF(animal.getName());
                    stream.writeInt(animal.getAge());
                    stream.writeDouble(animal.getWeight());
                    stream.writeInt(animal.getType().ordinal());

                    Person person = animal.getOwner();
                    stream.writeUTF(person.getName());
                    stream.writeInt(person.getAge());

                    List<Food> nutrition = animal.getNutrition();
                    stream.writeInt(nutrition.size());
                    for (Food food : nutrition) {
                        stream.writeUTF(food.getName());
                        stream.writeInt(food.getCalories());

                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        List<Animal> list = new ArrayList<>();
        try(DataInputStream stream = new DataInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int amount = stream.readInt();
            for (int i = 0; i < amount; ++i) {
                String name = stream.readUTF();
                int age = stream.readInt();
                double weight = stream.readDouble();
                AnimalType type = AnimalType.values()[stream.readInt()];
                Person person = new Person(stream.readUTF(), stream.readInt());
                int foodAmount = stream.readInt();
                List<Food> food = new ArrayList<>();
                for (int j = 0; j < foodAmount; ++j) {
                    food.add(new Food(stream.readUTF(), stream.readInt()));
                }
                list.add(new Animal(name, age, weight, type, person, food));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
