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
    private static final byte ANIMAL_BYTE = 0b0000001;
    private static final byte NAME_BYTE = 0b0000010;
    private static final byte INFORMATION_BYTE = 0b0000100;
    private static final byte HABITAT_BYTE = 0b0001000;
    private static final byte IS_INVERTEBRATE_BYTE = 0b0010000;
    private static final byte IS_AGGRESSIVE_BYTE = 0b0100000;
    private static final byte ANIMAL_TYPE_BYTE = 0b1000000;
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
                if (animal == null) {
                    out.writeByte(0b0);
                    continue;
                }
                out.writeByte(getByteFromData(animal));
                if (animal.getName() != null) {
                    out.writeUTF(animal.getName());
                }
                out.writeInt(animal.getAge());
                if (animal.getAnimalType() != null) {
                    out.writeUTF(String.valueOf(animal.getAnimalType()));
                }
                GeneralInformation information = animal.getInformation();
                if (information != null) {
                    if (information.getHabitat() != null) {
                        out.writeUTF(String.valueOf(information.getHabitat()));
                    }
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
                byte byteOfAnimalDataFromInput = in.readByte();
                if ((byteOfAnimalDataFromInput & ANIMAL_BYTE) == 0) {
                    animals.add(null);
                    continue;
                }
                String currentName;
                if ((byteOfAnimalDataFromInput & NAME_BYTE) != 0) {
                    currentName = in.readUTF();
                }
                else {
                    currentName = null;
                }
                int currentAge = in.readInt();
                AnimalType currentAnimalType;
                if ((byteOfAnimalDataFromInput & ANIMAL_TYPE_BYTE) != 0) {
                    currentAnimalType = AnimalType.valueOf(in.readUTF());
                }
                else {
                    currentAnimalType = null;
                }
                boolean isAggressive = (byteOfAnimalDataFromInput & IS_AGGRESSIVE_BYTE) != 0;
                boolean isInvertebrate = (byteOfAnimalDataFromInput & IS_INVERTEBRATE_BYTE) != 0;

                GeneralInformation information;
                if ((byteOfAnimalDataFromInput & INFORMATION_BYTE) != 0) {
                    Habitat currentHabitat;
                    if ((byteOfAnimalDataFromInput & HABITAT_BYTE) == 0) {
                        currentHabitat = null;
                    } else {
                        currentHabitat = Habitat.valueOf(in.readUTF());
                    }
                    long currentPopulationSize = in.readLong();
                    boolean listedInTheRedBook = in.readBoolean();
                    boolean isDangerous = in.readBoolean();
                    information = new GeneralInformation(currentHabitat, currentPopulationSize, listedInTheRedBook, isDangerous);
                }
                else {
                    information = null;
                }
                Animal currentAnimal = new Animal(currentName, currentAge, isAggressive, isInvertebrate, currentAnimalType, information);
                animals.add(currentAnimal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static byte getByteFromData(Animal animal) {
        return (byte) (getBooleanData(animal) | getNullableElements(animal));
    }

    private static byte getBooleanData(Animal animal) {
        byte result = 0;
        if (animal.isAggressive()) {
            result |= IS_AGGRESSIVE_BYTE;
        }
        if (animal.isInvertebrate()) {
            result |= IS_INVERTEBRATE_BYTE;
        }
        return result;
    }

    private static byte getNullableElements(Animal animal) {
        byte result = 0;
        if (animal != null) {
            result |= ANIMAL_BYTE;
        }
        else {
            return result;
        }
        if (animal.getName() != null) {
            result |= NAME_BYTE;
        }
        if (animal.getAnimalType() != null) {
            result |= ANIMAL_TYPE_BYTE;
        }
        if (animal.getInformation() != null) {
            result |= INFORMATION_BYTE;
        }
        else {
            return result;
        }
        if (animal.getInformation().getHabitat() != null) {
            result |= HABITAT_BYTE;
        }
        return result;
    }
}
