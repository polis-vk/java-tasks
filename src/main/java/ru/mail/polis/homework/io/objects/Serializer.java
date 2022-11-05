package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех (2-ух) способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random).
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 *
 * <p>
 * Далее этот список надо прочитать из файла.
 *
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 *
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
        Path fileTo = Paths.get(fileName);
        if (Files.notExists(fileTo)) {
            return;
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(fileTo))) {
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

        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }

        List<Animal> animals = new ArrayList<>();
        try (InputStream fileInputStream = Files.newInputStream(fileFrom);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                animals.add((Animal) inputStream.readObject());
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
        Path fileTo = Paths.get(fileName);
        if (Files.notExists(fileTo)) {
            return;
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(fileTo))) {
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

        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }

        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream fileInputStream = Files.newInputStream(fileFrom);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                animals.add((AnimalWithMethods) inputStream.readObject());
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
        Path fileTo = Paths.get(fileName);
        if (Files.notExists(fileTo)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(fileTo))) {
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
        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream fileInputStream = Files.newInputStream(fileFrom);
             ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                animals.add((AnimalExternalizable) inputStream.readObject());
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
        Path fileTo = Paths.get(fileName);
        if (Files.notExists(fileTo)) {
            return;
        }
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(fileTo))) {
            for (Animal animal : animals) {
                outputStream.writeUTF(writeString(animal.getName()));
                outputStream.writeUTF(writeString(String.valueOf(animal.getAnimalType())));
                outputStream.writeInt(animal.getCountLegs());
                outputStream.writeByte(animal.isDomesticated() ? 1 : 0);
                outputStream.writeByte(animal.isHerbivore() ? 1 : 0);
                Owner owner = animal.getOwner();
                if (owner != null) {
                    outputStream.writeUTF(writeString(owner.getName()));
                    outputStream.writeBoolean(owner.isOrganization());
                } else {
                    outputStream.writeUTF("null");
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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

        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream fileInputStream = Files.newInputStream(fileFrom);
             DataInputStream inputStream = new DataInputStream(fileInputStream)) {
            while (fileInputStream.available() != 0) {
                Animal animal = new Animal();
                animal.setName(readString(inputStream));
                String type = readString(inputStream);
                if (type != null) {
                    animal.setAnimalType(AnimalType.valueOf(type));
                }
                animal.setCountLegs(inputStream.readInt());
                animal.setDomesticated(inputStream.readByte() == 1);
                animal.setHerbivore(inputStream.readByte() == 1);
                String stringOwner = inputStream.readUTF();
                if (!stringOwner.equals("null")) {
                    Owner owner = new Owner();
                    owner.setName(stringOwner);
                    owner.setOrganization(inputStream.readBoolean());
                    animal.setOwner(owner);
                }
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static String writeString(String str) {
        return str == null ? "null" : str;
    }

    private static String readString(DataInputStream inputStream) throws IOException {
        String str = inputStream.readUTF();
        return str.equals("null") ? null : str;
    }
}