package ru.mail.polis.homework.io.objects;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
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
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random).
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 * <p>
 * Далее этот список надо прочитать из файла.
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {
    private static final byte ANIMAL_IS_NULL = 1;
    private static final byte HAS_ALIAS = 2;
    private static final byte HAS_MOVE_TYPE = 4;
    private static final byte HAS_ORGANIZATION = 8;
    private static final byte IS_WILD = 16;
    private static final byte IS_FURRY = 32;
    private static final byte HAS_ORGANIZATION_NAME = 1;
    private static final byte HAS_ORGANIZATION_OWNER = 2;
    private static final byte ORGANIZATION_IS_FOREIGN = 4;

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
        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }

        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
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
        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }

        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
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
        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return Collections.emptyList();
        }

        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
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
                outputStream.writeByte(setAnimalFlags(animal));
                if (animal == null) {
                    continue;
                }

                writeString(outputStream, animal.getAlias());
                outputStream.writeInt(animal.getLegs());
                Animal.Organization organization = animal.getOrganization();
                if (organization != null) {
                    outputStream.writeByte(setOrganizationFlags(organization));
                    writeString(outputStream, organization.getName());
                    writeString(outputStream, organization.getOwner());
                }
                writeString(outputStream, animal.getMoveType().toString());
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
        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(fileFrom))) {
            byte animalFlags;
            while (inputStream.available() != 0) {
                animalFlags = inputStream.readByte();
                if ((animalFlags & ANIMAL_IS_NULL) != 0) {
                    animals.add(null);
                    continue;
                }

                Animal animal = new Animal();
                animal.setAlias(readString(inputStream, animalFlags, HAS_ALIAS));
                animal.setLegs(inputStream.readInt());
                animal.setWild((animalFlags & IS_WILD) != 0);
                animal.setFurry((animalFlags & IS_FURRY) != 0);
                if ((animalFlags & HAS_ORGANIZATION) != 0) {
                    byte organizationFlags = inputStream.readByte();
                    Animal.Organization organization = new Animal.Organization();
                    organization.setName(readString(inputStream, organizationFlags, HAS_ORGANIZATION_NAME));
                    organization.setOwner(readString(inputStream, organizationFlags, HAS_ORGANIZATION_OWNER));
                    organization.setForeign((organizationFlags & ORGANIZATION_IS_FOREIGN) != 0);
                    animal.setOrganization(organization);
                }
                animal.setMoveType(MoveType.valueOf(readString(inputStream, animalFlags, HAS_MOVE_TYPE)));
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static void writeString(DataOutput outputStream, String str) throws IOException {
        if (str != null) {
            outputStream.writeUTF(str);
        }
    }

    private static String readString(DataInput input, byte flags, byte category) throws IOException {
        return (flags & category) != 0 ? input.readUTF() : null;
    }

    private static byte setAnimalFlags(Animal animal) {
        byte result = 0;
        if (animal == null) {
            return (byte) (result | ANIMAL_IS_NULL);
        }
        if (animal.getAlias() != null) {
            result = (byte) (result | HAS_ALIAS);
        }
        if (animal.getMoveType() != null) {
            result = (byte) (result | HAS_MOVE_TYPE);
        }
        if (animal.getOrganization() != null) {
            result = (byte) (result | HAS_ORGANIZATION);
        }
        if (animal.isWild()) {
            result = (byte) (result | IS_WILD);
        }
        if (animal.isFurry()) {
            result = (byte) (result | IS_FURRY);
        }
        return result;
    }

    private static byte setOrganizationFlags(Animal.Organization organization) {
        byte result = 0;
        if (organization.getName() != null) {
            result = (byte) (result | HAS_ORGANIZATION_NAME);
        }
        if (organization.getOwner() != null) {
            result = (byte) (result | HAS_ORGANIZATION_OWNER);
        }
        if (organization.isForeign()) {
            result = (byte) (result | ORGANIZATION_IS_FOREIGN);
        }
        return result;
    }
}

