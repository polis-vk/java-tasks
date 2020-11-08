package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
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
        Path path = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
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
        Path path = Paths.get(fileName);

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(Files.newInputStream(path))) {
            int n = objectInputStream.readInt();
            List<Animal> animals = new ArrayList<>(n);
            for (int i = 0; i < n; ++i) {
                animals.add((Animal) objectInputStream.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
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
        Path path = Paths.get(fileName);

        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(Files.newInputStream(path))) {
            int n = objectInputStream.readInt();
            for (int i = 0; i < n; ++i) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
            }
        }
        return animals;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
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
        Path path = Paths.get(fileName);

        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(Files.newInputStream(path))) {
            int n = objectInputStream.readInt();
            for (int i = 0; i < n; ++i) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
            }
        }
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream objectOutputStream =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeDouble(animal.getWeight());
                objectOutputStream.writeUTF(animal.getParents().getMother());
                objectOutputStream.writeUTF(animal.getParents().getFather());
                objectOutputStream.writeInt(animal.getGenericOfRelatives().size());
                for (Parents i : animal.getGenericOfRelatives()) {
                    objectOutputStream.writeUTF(i.getMother());
                    objectOutputStream.writeUTF(i.getFather());
                }

                objectOutputStream.writeUTF(animal.getColor().name());
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
    public List<Animal> customDeserialize(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);

        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(Files.newInputStream(path))) {
            int n = objectInputStream.readInt();
            for (int k = 0; k < n; ++k) {
                String name = objectInputStream.readUTF();
                double weight = objectInputStream.readDouble();
                String mother = objectInputStream.readUTF();
                String father = objectInputStream.readUTF();
                Parents parents = new Parents(mother, father);
                int size = objectInputStream.readInt();

                List<Parents> genericOfRelatives = new ArrayList<>(n);
                for (int i = 0; i < size; ++i) {
                    String mother1 = objectInputStream.readUTF();
                    String father1 = objectInputStream.readUTF();
                    Parents parents1 = new Parents(mother1, father1);
                    genericOfRelatives.add(parents1);
                }

                animals.add(new Animal(name, weight, parents, genericOfRelatives, Colour.valueOf(objectInputStream.readUTF())));
            }

        }
        return animals;
    }
}