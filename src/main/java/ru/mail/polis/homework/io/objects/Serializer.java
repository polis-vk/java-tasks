package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех (2-ух) способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Список лучше копировать, чтобы у вас не было ссылок на одни и те же объекты.
 * <p>
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста/2 теста,
 * за каждый тест 1 балл и 1 бал за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты, как примеры.
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }

        List<Animal> animalsList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = inputStream.readInt();
            for (int i = 0; i < size; ++i) {
                animalsList.add((Animal) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalsList;
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
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                writeAnimal(animal, outputStream);
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

        List<Animal> animalsList = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(filePath))) {
            {
                int size = inputStream.readInt();
                for (int i = 0; i < size; ++i) {
                    animalsList.add(parseAnimal(inputStream));
                }
            }
            return animalsList;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static void writeAnimal(Animal animal, DataOutputStream outputStream) throws IOException {
        outputStream.writeInt(animal.getAge());
        outputStream.writeUTF(animal.getName());
        outputStream.writeUTF(animal.getSpecies().toString());
        outputStream.writeUTF(animal.getAreal().toString());
        writeAnimalAncestor(animal.getAncestor(), outputStream);
    }

    private static void writeAnimalAncestor(Animal animal, DataOutputStream outputStream) throws IOException {
        if (animal == null) {
            outputStream.writeBoolean(false);
            return;
        }
        outputStream.writeBoolean(true);
        writeAnimal(animal, outputStream);
    }

    private static Animal parseAnimal(DataInputStream inputStream) throws IOException {
        int age = inputStream.readInt();
        String name = inputStream.readUTF();
        Species species = Species.valueOf(inputStream.readUTF());
        Areal areal = Areal.valueOf(inputStream.readUTF());
        Animal ancestor = null;
        if (inputStream.readBoolean()) {
            ancestor = parseAnimal(inputStream);
        }
        return new Animal(age, name, species, areal, ancestor);
    }
}
