package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeInt(animals.size());
        for (Animal animal : animals) {
            objectOutputStream.writeObject(animal);
        }
        objectOutputStream.close();
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        int count = objectInputStream.readInt();
        ArrayList<Animal> animals = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            animals.add((Animal) objectInputStream.readObject());
        }
        objectInputStream.close();
        return animals;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeInt(animals.size());
        for (AnimalWithMethods animal : animals) {
            animal.write(objectOutputStream);
        }
        objectOutputStream.close();
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        int count = objectInputStream.readInt();
        ArrayList<AnimalWithMethods> animals = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            animals.add(AnimalWithMethods.read(objectInputStream));
        }
        objectInputStream.close();
        return animals;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeInt(animals.size());
        for (AnimalExternalizable animal : animals) {
            animal.writeExternal(objectOutputStream);
        }
        objectOutputStream.close();
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        int count = objectInputStream.readInt();
        ArrayList<AnimalExternalizable> animals = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            AnimalExternalizable animal = new AnimalExternalizable();
            animal.readExternal(objectInputStream);
            animals.add(animal);
        }
        objectInputStream.close();
        return animals;
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public static void customSerialize(List<Animal> animals, String fileName) throws IOException {
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName));
        objectOutputStream.writeInt(animals.size());
        for (Animal animal : animals) {
            objectOutputStream.writeUTF(animal.getName());
            objectOutputStream.writeObject(animal.getDiet());
            objectOutputStream.writeObject(animal.getGenotype());
            objectOutputStream.writeInt(animal.getSpeciesId());
            objectOutputStream.writeInt(animal.getScaredOf().size());
            for (Integer integer : animal.getScaredOf()) {
                objectOutputStream.writeInt(integer);
            }
            objectOutputStream.writeBoolean(animal.isSingleCell());
        }
        objectOutputStream.close();
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<Animal> customDeserialize(String fileName) throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName));
        int count = objectInputStream.readInt();
        ArrayList<Animal> animals = new ArrayList<>(count);
        for (int i = 0; i < count; i++) {
            String name = objectInputStream.readUTF();
            Animal.Diet diet = (Animal.Diet) objectInputStream.readObject();
            Genotype genotype = (Genotype) objectInputStream.readObject();
            int speciesId = objectInputStream.readInt();
            int size = objectInputStream.readInt();
            List<Integer> scared = new ArrayList<>(size);
            for (int j = 0; j < size; j++) {
                scared.add(objectInputStream.readInt());
            }
            boolean singleCell = objectInputStream.readBoolean();
            animals.add(new Animal(name, diet, genotype, speciesId, scared, singleCell));
        }
        objectInputStream.close();
        return animals;
    }
}
