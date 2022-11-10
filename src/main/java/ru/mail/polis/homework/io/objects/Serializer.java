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

    private static final byte TRUE = 1;
    private static final byte FALSE = 0;

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
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
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file))) {
            int size = inputStream.readInt();
            animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((Animal) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                outputStream.writeObject(animal);
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
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return null;
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file))) {
            int size = inputStream.readInt();
            animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animals;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(file))) {
            outputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                outputStream.writeObject(animal);
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
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return null;
        }
        List<AnimalExternalizable> animals = null;
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(file))) {
            int size = inputStream.readInt();
            animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
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
        Path file = Paths.get(fileName);
        if (Files.notExists(file)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream (Files.newOutputStream(file))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeByte(animal != null ? TRUE : FALSE);
                if (animal == null) {
                    continue;
                }

                String name = animal.getName();
                outputStream.writeByte(name != null ? TRUE : FALSE);
                if (name != null) {
                    outputStream.writeUTF(name);
                }

                outputStream.writeInt(animal.getLegs());
                outputStream.writeDouble(animal.getWeight());
                outputStream.writeByte(animal.isChild() ? TRUE : FALSE);
                outputStream.writeByte(animal.isWild() ? TRUE : FALSE);

                AnimalType animalType = animal.getType();
                outputStream.writeByte(animalType != null ? TRUE : FALSE);
                if (animalType != null) {
                    outputStream.writeUTF(animalType.name());
                }

                Organization organization = animal.getOrganization();
                outputStream.writeByte(organization != null ? TRUE : FALSE);
                if (organization != null) {
                    String organizationName = organization.getName();
                    outputStream.writeByte(organizationName != null ? TRUE : FALSE);
                    if (organizationName != null) {
                        outputStream.writeUTF(organizationName);
                    }

                    outputStream.writeByte(organization.isVolunteer() ? TRUE : FALSE);
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
        try (ObjectInputStream inputStream = new ObjectInputStream (Files.newInputStream(file))) {
            int size = inputStream.readInt();
            animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                if (inputStream.readByte() == FALSE) {
                    animals.add(null);
                    continue;
                }

                String name = null;
                if (inputStream.readByte() == TRUE) {
                    name = inputStream.readUTF();
                }

                int legs = inputStream.readInt();
                double weight = inputStream.readDouble();
                boolean isChild = inputStream.readByte() == TRUE;
                boolean isWild = inputStream.readByte() == TRUE;

                AnimalType type = null;
                if (inputStream.readByte() == TRUE) {
                    type = AnimalType.valueOf(inputStream.readUTF());
                }

                Organization organization = null;
                if (inputStream.readByte() == TRUE) {
                    String organizationName = null;
                    if (inputStream.readByte() == TRUE) {
                        organizationName = inputStream.readUTF();
                    }

                    boolean isVolunteer = inputStream.readByte() == TRUE;

                    organization = new Organization(organizationName, isVolunteer);
                }

                Animal animal = new Animal(name, legs, weight, isChild, isWild, type, organization);
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
