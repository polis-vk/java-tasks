package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectOutput;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
        try (InputStream is = Files.newInputStream(file);
             ObjectInputStream ois = new ObjectInputStream(is)) {
            while (is.available() > 0) {
                Object o = ois.readObject();
                if (o instanceof Animal) {
                    animals.add((T) o);
                } else {
                    animals.add(null);
                }
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
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    oos.writeByte(0);
                    continue;
                }
                oos.writeByte(1);
                writeString(oos, animal.getName());
                oos.writeInt(animal.getAge());
                oos.writeByte((byte) ((animal.isFriendly() ? 1 : 0) << 1 + (animal.isWarmBlooded() ? 1 : 0)));
                oos.writeByte(animal.getAnimalType().getOrdinal());

                Population population = animal.getPopulation();
                if (population != null) {
                    writeString(oos, population.getName());
                    oos.writeLong(population.getSize());
                    oos.writeInt(population.getDensity());
                }
            }
        } catch (IOException e) {
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
        try (ObjectInputStream ois = new ObjectInputStream(Files.newInputStream(file))) {
            while (ois.available() > 0) {
                if (ois.readByte() == 0) {
                    animals.add(null);
                    continue;
                }
                String name = readString(ois);
                int age = ois.readInt();
                boolean friendly = ois.readBoolean();
                boolean warmBlooded = ois.readBoolean();
                AnimalType animalType = AnimalType.getOrdinal(ois.readByte());
                if (ois.readByte() != 0) {
                    animals.add(new Animal(name, age, friendly, warmBlooded, animalType,
                            new Population(readString(ois), ois.readLong(), ois.readInt())));
                } else {
                    animals.add(new Animal(name, age, friendly, warmBlooded, animalType, null));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static void writeString(ObjectOutput out, String str) throws IOException {
        if (str == null) {
            out.writeByte(0);
        } else {
            out.writeByte(1);
            out.writeUTF(str);
        }
    }

    private static String readString(ObjectInput in) throws IOException {
        if (in.readByte() == 0) {
            return null;
        }
        return in.readUTF();
    }
    
}
