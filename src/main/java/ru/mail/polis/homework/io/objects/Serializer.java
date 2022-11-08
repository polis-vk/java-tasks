package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random). Важно, чтобы в списке животных попадались null-ы
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 * <p>
 * Далее этот список надо прочитать из файла.
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath.getParent())) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        if (fileName == null) {
            return Collections.emptyList();
        }
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(filePath);
             ObjectInputStream objIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((Animal) objIn.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (fileName == null) {
            return Collections.emptyList();
        }
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(filePath);
             ObjectInputStream objIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((AnimalWithMethods) objIn.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
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
        if (fileName == null) {
            return Collections.emptyList();
        }
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(filePath);
             ObjectInputStream objIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((AnimalExternalizable) objIn.readObject());
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return animals;
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
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return;
        }
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                out.writeUTF(convertValueToString(animal.getName()));
                out.writeInt(animal.getAge());
                out.writeBoolean(animal.isAggressive());
                out.writeBoolean(animal.isInvertebrate());
                out.writeUTF(convertValueToString(String.valueOf(animal.getAnimalType())));
                GeneralInformation information = animal.getInformation();
                if (information == null) {
                    out.writeUTF("null");
                } else {
                    out.writeUTF(convertValueToString(String.valueOf(information.getHabitat())));
                    out.writeLong(information.getPopulationSize());
                    out.writeBoolean(information.isListedInTheRedBook());
                    out.writeBoolean(information.isDangerous());
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
        if (fileName == null) {
            return Collections.emptyList();
        }
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(Files.newInputStream(filePath))) {
            while (in.available() > 0) {
                String currentName = in.readUTF();
                currentName = currentName.equals("null") ? null : currentName;
                int currentAge = in.readInt();
                boolean isAggressive = in.readBoolean();
                boolean isInvertebrate = in.readBoolean();
                String currentStringForAnimalType = in.readUTF();
                AnimalType currentAnimalType;
                if (currentStringForAnimalType.equals("null")) {
                    currentAnimalType = null;
                } else {
                    currentAnimalType = AnimalType.valueOf(currentStringForAnimalType);
                }
                String informationString = in.readUTF();
                GeneralInformation information;
                if (informationString.equals("null")) {
                    information = null;
                } else {
                    Habitat currentHabitat;
                    String currentHabitatString = in.readUTF();
                    if (currentHabitatString.equals("null")) {
                        currentHabitat = null;
                    } else {
                        currentHabitat = Habitat.valueOf(currentHabitatString);
                    }
                    long currentPopulationSize = in.readLong();
                    boolean listedInTheRedBook = in.readBoolean();
                    boolean isDangerous = in.readBoolean();
                    information = new GeneralInformation(currentHabitat, currentPopulationSize, listedInTheRedBook, isDangerous);
                }
                Animal currentAnimal = new Animal(currentName, currentAge, isAggressive, isInvertebrate, currentAnimalType, information);
                animals.add(currentAnimal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static String convertValueToString(String value) {
        return value == null ? "null" : value;
    }

}
