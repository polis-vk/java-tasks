package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.util.ArrayList;
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

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

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
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                animals.add((Animal) in.readObject());
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
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods) in.readObject());
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
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable) in.readObject());
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
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                writeUTF(out, animal.getName());
                writeUTF(out, animal.getType().toString());
                out.writeInt(animal.getAge());
                out.writeDouble(animal.getWeight());
                writeUTF(out, animal.getMeal().getFavouriteMeal());
                writeUTF(out, animal.getMeal().getLastMeal().toString());
                out.writeInt(animal.getMeal().getMealsPerDay());
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
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            for (int i = 0; i < size; i++) {
                String name = readUTF(in);
                String animalType = readUTF(in);
                AnimalType type = animalType != null ? AnimalType.valueOf(animalType) : null;
                int age = in.readInt();
                double weight = in.readDouble();
                String favouriteMeal = readUTF(in);
                String lastMealString = readUTF(in);
                LocalDateTime lastMeal = null;
                if (lastMealString != null) {
                    lastMeal = LocalDateTime.parse(lastMealString);
                }
                int mealsPerDay = in.readInt();
                animals.add(new Animal(name, type, age, weight, new Meal(favouriteMeal, lastMeal, mealsPerDay)));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private void writeUTF(ObjectOutputStream out, String string) throws IOException {
        if (string == null) {
            out.write(NULL_VALUE);
        } else {
            out.write(NOT_NULL_VALUE);
            out.writeUTF(string);
        }
    }

    private String readUTF(ObjectInputStream in) throws IOException {
        return in.readByte() == 1 ? in.readUTF() : null;
    }
}
