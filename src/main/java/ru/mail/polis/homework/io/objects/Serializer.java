package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

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
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (Animal animal : animals) {
                output.writeObject(animal);
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
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath))) {
            List<Animal> animals = new ArrayList<>();
            int size = input.readInt();
            for (int i = 0; i < size; ++i) {
                animals.add((Animal) input.readObject());
            }
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {

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
        return Collections.emptyList();
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {

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
        return Collections.emptyList();
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
        Path filePath = Paths.get(fileName);
        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (Animal animal : animals) {
                writeAnimal(output, animal);
            }
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
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        try (DataInputStream input = new DataInputStream(Files.newInputStream(filePath))) {
            int size = input.readInt();
            List<Animal> animals = new ArrayList<>();
            for (int i = 0; i < size; ++i) {
                animals.add(parseAnimal(input));
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }

    private static void writeAnimal(DataOutputStream output, Animal animal) throws IOException {
        output.writeInt(animal.getAge());
        output.writeUTF(animal.getAlias());
        output.writeUTF(animal.getColor().toString());
        writeParent(output, animal.getFather());
        writeParent(output, animal.getMother());
    }

    private static void writeParent(DataOutputStream output, Animal parent) throws IOException {
        if (parent == null) {
            output.writeBoolean(false);
        } else {
            output.writeBoolean(true);
            writeAnimal(output, parent);
        }
    }

    private static Animal parseAnimal(DataInputStream input) throws IOException {
        int age = input.readInt();
        String alias = input.readUTF();
        Color color = Color.valueOf(input.readUTF());
        Animal father = null;
        if (input.readBoolean()) {
            father = parseAnimal(input);
        }
        Animal mother = null;
        if (input.readBoolean()) {
            mother = parseAnimal(input);
        }
        return new Animal(age, alias, color, father, mother);
    }
}
