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
        Path path = Paths.get(fileName);

        try (ObjectInputStream objectInputStream =
                     new ObjectInputStream(Files.newInputStream(path))) {
            return (ArrayList) objectInputStream.readObject();
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
            for (AnimalWithMethods animal : animals) {
                animal.writeObject(objectOutputStream);
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
            AnimalWithMethods animalWithMethods;
            while (objectInputStream.available() != 0) {
                animalWithMethods = new AnimalWithMethods();
                animalWithMethods.readObject(objectInputStream);
                animals.add(animalWithMethods);
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
            for (AnimalExternalizable animal : animals) {
                animal.writeExternal(objectOutputStream);
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
            AnimalExternalizable animalExternalizable;
            while (objectInputStream.available() != 0) {
                animalExternalizable = new AnimalExternalizable();
                animalExternalizable.readExternal(objectInputStream);
                animals.add(animalExternalizable);
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
            while (true) {
                try {
                    String name = objectInputStream.readUTF();
                    double weight = objectInputStream.readDouble();
                    String mother = objectInputStream.readUTF();
                    String father = objectInputStream.readUTF();
                    Parents parents = new Parents(mother, father);
                    int n = objectInputStream.readInt();

                    List<Parents> genericOfRelatives = new ArrayList<>(n);
                    for (int i = 0; i < n; ++i) {
                        String mother1 = objectInputStream.readUTF();
                        String father1 = objectInputStream.readUTF();
                        Parents parents1 = new Parents(mother1, father1);
                        genericOfRelatives.add(parents1);
                    }
                    Colour colour = Colour.valueOf(objectInputStream.readUTF());
                    animals.add(new Animal(name, weight, parents, genericOfRelatives, colour));
                } catch (IOException e) {
                    break;
                }
            }

        }
        return animals;
    }
}
