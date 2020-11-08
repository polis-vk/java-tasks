package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeObject(animals);
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
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            return (List<Animal>)objectInputStream.readObject();
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for(AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            List<AnimalWithMethods> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods)objectInputStream.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            List<AnimalExternalizable> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable)objectInputStream.readObject());
            }
            return animals;
        }
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeInt(animal.getAge());
                objectOutputStream.writeDouble(animal.getWeight());
                objectOutputStream.writeUTF(animal.getCharacteristic().toString());
                objectOutputStream.writeInt(animal.getNameChildren().size());
                for (String name : animal.getNameChildren()) {
                    objectOutputStream.writeUTF(name);
                }
                objectOutputStream.writeInt(animal.getAnimalInfo().getAverageAge());
                objectOutputStream.writeUTF(animal.getAnimalInfo().getInhabitancy());
                objectOutputStream.writeBoolean(animal.getAnimalInfo().isLonely());
            }
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                String name = objectInputStream.readUTF();
                int age = objectInputStream.readInt();
                double weight = objectInputStream.readDouble();
                Characteristic characteristic = Characteristic.valueOf(objectInputStream.readUTF());
                int count = objectInputStream.readInt();
                List<String> nameChildren = new ArrayList<>(count);
                for (int j = 0; j < count; j++) {
                    nameChildren.add(objectInputStream.readUTF());
                }
                int averageAge = objectInputStream.readInt();
                String inhabitancy = objectInputStream.readUTF();
                boolean lonely = objectInputStream.readBoolean();
                animals.add(new Animal(name, age,
                        weight, characteristic, nameChildren,
                        new AnimalInfo(averageAge,inhabitancy, lonely)));
            }
            return animals;
        }
    }
}
