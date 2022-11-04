package ru.mail.polis.homework.io.objects;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.EOFException;
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
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            Object currObject;
            while (true) {
                currObject = inputStream.readObject();
                if (currObject instanceof Animal) {
                    animals.add((Animal) currObject);
                } else {
                    animals.add(null);
                }
            }
        } catch (EOFException e) {
//            ignored
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
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            Object currObject;
            while (true) {
                currObject = inputStream.readObject();
                if (currObject instanceof AnimalWithMethods) {
                    animals.add((AnimalWithMethods) currObject);
                } else {
                    animals.add(null);
                }
            }
        } catch (EOFException e) {
//            ignored
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
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(fileFrom))) {
            Object currObject;
            while (true) {
                currObject = inputStream.readObject();
                if (currObject instanceof AnimalExternalizable) {
                    animals.add((AnimalExternalizable) currObject);
                } else {
                    animals.add(null);
                }
            }
        } catch (EOFException e) {
//            ignored
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
                if (animal == null) {
                    outputStream.writeByte(0);
                    continue;
                }

                outputStream.writeByte(1);
                writeString(outputStream, animal.getAlias());
                outputStream.writeInt(animal.getLegs());
                Animal.Organization organization = animal.getOrganization();
                outputStream.writeByte(setBooleanFlags(animal.isWild(), animal.isFurry(), organization != null));
                if (organization != null) {
                    writeString(outputStream, organization.getName());
                    writeString(outputStream, organization.getOwner());
                    outputStream.writeBoolean(organization.isForeign());
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
            while (inputStream.available() != 0) {
                if (inputStream.readByte() == 0) {
                    animals.add(null);
                    continue;
                }

                Animal animal = new Animal();
                animal.setAlias(readString(inputStream));
                animal.setLegs(inputStream.readInt());
                byte booleanFlags = inputStream.readByte();
                animal.setWild((booleanFlags & 1) != 0);
                animal.setFurry((booleanFlags & 2) != 0);
                boolean hasOrganization = ((booleanFlags & 4) != 0);
                if (hasOrganization) {
                    Animal.Organization organization = new Animal.Organization();
                    organization.setName(readString(inputStream));
                    organization.setOwner(readString(inputStream));
                    organization.setForeign(inputStream.readBoolean());
                    animal.setOrganization(organization);
                }
                animal.setMoveType(MoveType.valueOf(readString(inputStream)));
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static void writeString(DataOutput outputStream, String str) throws IOException {
        if (str == null) {
            outputStream.writeByte(0);
        } else {
            outputStream.writeByte(1);
            outputStream.writeUTF(str);
        }
    }

    private static String readString(DataInput inputStream) throws IOException {
        return inputStream.readByte() == 0 ? null : inputStream.readUTF();
    }

    private static byte setBooleanFlags(boolean wild, boolean furry, boolean hasOrganization) {
        byte result = 0;
        if (wild) {
            result = (byte) (result | 1);
        }
        if (furry) {
            result = (byte) (result | 2);
        }
        if (hasOrganization) {
            result = (byte) (result | 4);
        }
        return result;
    }
}

