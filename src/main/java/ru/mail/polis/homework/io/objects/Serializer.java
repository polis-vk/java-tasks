package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedInputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random). Важно, чтобы в списке животных попадались null-ы
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 *
 * Далее этот список надо прочитать из файла.
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    private <T> void serialize(List<T> animals, String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeInt(animals.size());
            for (T animal : animals) {
                outputStream.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @SuppressWarnings("unchecked")
    private <T> List<T> deserialize(String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return null;
        }
        List<T> animals = new ArrayList<>();
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            int size = ois.readInt();
            for (int i = 0; i < size; i++) {
                animals.add((T) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }
    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        serialize(animals, fileName);
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        return deserialize(fileName);
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        serialize(animals, fileName);
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        return deserialize(fileName);
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        serialize(animals, fileName);
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        return deserialize(fileName);
    }

    /**
     * 2 тугрика
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file)))) {
            dos.writeInt(animals.size());
            for (Animal animal : animals) {
                AnimalByte animalByte = new AnimalByte(animal);
                dos.writeByte(animalByte.writeByte());
                if (animalByte.animalIsNull()) {
                    continue;
                }
                if (animalByte.nameIsNotNull()) {
                    dos.writeUTF(animal.getName());
                }
                dos.writeInt(animal.getAge());
                if (animalByte.animalTypeIsNotNull()) {
                    dos.writeUTF(animal.getAnimalType().name());
                }
                Population population = animal.getPopulation();
                PopulationByte populationByte = new PopulationByte(population);
                dos.writeByte(populationByte.writeByte());
                if (populationByte.populationIsNull()) {
                    continue;
                }
                if (populationByte.nameIsNotNull()) {
                    dos.writeUTF(population.getName());
                }
                dos.writeLong(population.getSize());
                dos.writeInt(population.getDensity());
            }
        } catch (
                IOException e) {
            e.printStackTrace();
        }

    }

    /**
     * 2 тугрика
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream dis = new DataInputStream(new BufferedInputStream(Files.newInputStream(file)))) {
            int size = dis.readInt();
            for (int i = 0; i < size; i++) {
                AnimalByte animalByte = new AnimalByte(dis.readByte());
                if (animalByte.animalIsNull()) {
                    animals.add(null);
                    continue;
                }
                String name = null;
                if (animalByte.nameIsNotNull()) {
                    name = dis.readUTF();
                }
                int age = dis.readInt();
                boolean friendly = animalByte.isFriendly();
                boolean warmBlooded = animalByte.isWarmBlooded();
                AnimalType animalType = null;
                if (animalByte.animalTypeIsNotNull()) {
                    animalType = AnimalType.valueOf(dis.readUTF());
                }
                PopulationByte populationByte = new PopulationByte(dis.readByte());
                if (populationByte.populationIsNull()) {
                    animals.add(new Animal(name, age, friendly, warmBlooded, animalType, null));
                    continue;
                }
                if (populationByte.nameIsNotNull()) {
                    animals.add(new Animal(name, age, friendly, warmBlooded, animalType,
                            new Population(dis.readUTF(), dis.readLong(), dis.readInt())));
                } else {
                    animals.add(new Animal(name, age, friendly, warmBlooded, animalType,
                            new Population(null, dis.readLong(), dis.readInt())));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

}
