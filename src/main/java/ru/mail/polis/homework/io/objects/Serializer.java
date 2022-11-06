package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            while (objectInputStream.available() > 0) {
                animals.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            while (objectInputStream.available() > 0) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            while (objectInputStream.available() > 0) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream o = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (Animal animal : animals) {
                o.writeUTF(animal.name);
                o.writeUTF(animal.type.toString());
                o.writeInt(animal.age);
                o.writeDouble(animal.weight);
                o.writeUTF(animal.meal.favouriteMeal);
                o.writeUTF(animal.meal.lastMeal.toString());
                o.writeInt(animal.meal.allergicTo.size());
                o.writeUTF(animal.meal.allergicTo.toString());
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
        Path path = Path.of(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream o = new ObjectInputStream(Files.newInputStream(path))) {
            while (o.available() > 0) {
                String name = o.readUTF();
                AnimalType type = AnimalType.valueOf(o.readUTF());
                int age = o.readInt();
                double weight = o.readDouble();
                String favouriteMeal = o.readUTF();
                LocalDateTime lastMeal = LocalDateTime.parse(o.readUTF());
                Set<String> allergicTo = new HashSet<>();
                for (int i = 0; i < o.readInt(); i++) {
                    allergicTo.add(o.readUTF());
                }
                animals.add(new Animal(name, type, age, weight, new Meal(favouriteMeal, lastMeal, allergicTo)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
