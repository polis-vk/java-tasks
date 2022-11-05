package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import static ru.mail.polis.homework.io.objects.SerializationUtils.NULL;
import static ru.mail.polis.homework.io.objects.SerializationUtils.booleanToByte;
import static ru.mail.polis.homework.io.objects.SerializationUtils.byteToBoolean;
import static ru.mail.polis.homework.io.objects.SerializationUtils.enumToString;
import static ru.mail.polis.homework.io.objects.SerializationUtils.readUTFOrNull;
import static ru.mail.polis.homework.io.objects.SerializationUtils.stringToEnum;
import static ru.mail.polis.homework.io.objects.SerializationUtils.writeUTFOrNull;

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

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
                objectOutputStream.flush();
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
        List<Animal> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                list.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
                objectOutputStream.flush();
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
        List<AnimalWithMethods> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                list.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
                objectOutputStream.flush();
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
        List<AnimalExternalizable> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                list.add((AnimalExternalizable) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return list;
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
        try (final DataOutputStream dataOutputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {
                // Write Location first as it's an object
                if (animal.getAnimalLocation() == null) {
                    dataOutputStream.writeByte(SerializationUtils.NULL);
                } else {
                    dataOutputStream.writeByte(SerializationUtils.NOT_NULL);
                    writeUTFOrNull(animal.getAnimalLocation().getLongitude(), dataOutputStream);
                    writeUTFOrNull(animal.getAnimalLocation().getLatitude(), dataOutputStream);
                }
                // Write other fields of superclass
                writeUTFOrNull(animal.getName(), dataOutputStream);
                dataOutputStream.writeInt(animal.getAge());
                dataOutputStream.writeByte(booleanToByte(animal.isWild()));
                dataOutputStream.writeByte(booleanToByte(animal.isFed()));
                writeUTFOrNull(enumToString(animal.getAnimalAbilityType()), dataOutputStream);
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
        List<Animal> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final DataInputStream dataInputStream = new DataInputStream(fileInputStream)) {
            while (fileInputStream.available() > 0) {
                Animal animal = new Animal(
                        dataInputStream.readByte() == NULL ? null :
                                new Location(
                                        readUTFOrNull(dataInputStream),
                                        readUTFOrNull(dataInputStream)
                                ),
                        readUTFOrNull(dataInputStream),
                        dataInputStream.readInt(),
                        byteToBoolean(dataInputStream.readByte()),
                        byteToBoolean(dataInputStream.readByte()),
                        stringToEnum(readUTFOrNull(dataInputStream), AnimalAbilityType.class)
                );
                list.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
