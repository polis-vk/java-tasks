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

    private final static int ANIMAL_BIT = 0;
    private final static int ANIMAL_TYPE_BIT = 1;
    private final static int ANIMAL_NAME_BIT = 2;
    private final static int ANIMAL_IS_SEEK_BIT = 3;
    private final static int ANIMAL_IN_NURSERY_BIT = 4;
    private final static int NURSERY_BIT = 5;
    private final static int NURSERY_ADDRESS_BIT = 6;
    private final static int NURSERY_IS_WORK_BIT = 7;


    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (fileName == null || animals == null) {
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (Animal obj : animals) {
                outputStream.writeObject(obj);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
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
        List<Animal> animalList = new ArrayList<>();
        try (InputStream input = Files.newInputStream(filePath)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(input)) {
                while (input.available() > 0) {
                    animalList.add((Animal) inputStream.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalList;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        if (fileName == null || animals == null) {
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalWithMethods obj : animals) {
                outputStream.writeObject(obj);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
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
        List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
        try (InputStream input = Files.newInputStream(filePath)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(input)) {
                while (input.available() > 0) {
                    animalWithMethodsList.add((AnimalWithMethods) inputStream.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalWithMethodsList;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        if (fileName == null || animals == null) {
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalExternalizable obj : animals) {
                outputStream.writeObject(obj);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
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
        List<AnimalExternalizable> animalExternalizableList = new ArrayList<>();
        try (InputStream input = Files.newInputStream(filePath)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(input)) {
                while (input.available() > 0) {
                    animalExternalizableList.add((AnimalExternalizable) inputStream.readObject());
                }
            }
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalExternalizableList;
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
        if (fileName == null || animals == null) {
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))) {
            for (Animal obj : animals) {
                byte zip = Animal.byteZip(obj == null, ANIMAL_BIT, (byte) 0);

                if (Animal.trueOrNotNullContain(ANIMAL_BIT, zip)) {
                    zip = Animal.byteZip(obj.getType() == null, ANIMAL_TYPE_BIT, zip); // Проверка на null в if.
                    zip = Animal.byteZip(obj.getName() == null, ANIMAL_NAME_BIT, zip);
                    zip = Animal.byteZip(!obj.isSeek(), ANIMAL_IS_SEEK_BIT, zip);
                    zip = Animal.byteZip(!obj.isInNursery(), ANIMAL_IN_NURSERY_BIT, zip);

                    AnimalNursery animalNursery = obj.getNursery();
                    zip = Animal.byteZip(animalNursery == null, NURSERY_BIT, zip);
                    if (Animal.trueOrNotNullContain(NURSERY_BIT, zip)) {
                        zip = Animal.byteZip(animalNursery.getAddress() == null, NURSERY_ADDRESS_BIT, zip); // Аналогично
                        zip = Animal.byteZip(!animalNursery.isWork(), NURSERY_IS_WORK_BIT, zip);
                    }

                    outputStream.writeByte(zip);
                    outputStream.writeInt(obj.getAge());

                    if (Animal.trueOrNotNullContain(ANIMAL_TYPE_BIT, zip)) {
                        outputStream.writeUTF(obj.getType().name());
                    }
                    if (Animal.trueOrNotNullContain(ANIMAL_NAME_BIT, zip)) {
                        outputStream.writeUTF(obj.getName());
                    }
                    if (Animal.trueOrNotNullContain(NURSERY_BIT, zip)) {
                        if (Animal.trueOrNotNullContain(NURSERY_ADDRESS_BIT, zip)) {
                            outputStream.writeUTF(animalNursery.getAddress()); // Аналогично
                        }
                    }
                } else {
                    outputStream.writeByte(zip);
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
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
        List<Animal> animalList = new ArrayList<>();
        try (InputStream input = Files.newInputStream(filePath)) {
            try (DataInputStream inputStream = new DataInputStream(input)) {
                while (input.available() > 0) {
                    byte zip = inputStream.readByte();
                    if (!Animal.trueOrNotNullContain(ANIMAL_BIT, zip)) {
                        animalList.add(null);
                    } else {
                        int age = inputStream.readInt();
                        AnimalType type = Animal.trueOrNotNullContain(ANIMAL_TYPE_BIT, zip) ? AnimalType.valueOf(inputStream.readUTF()) : AnimalType.NONE;
                        String animalName = Animal.trueOrNotNullContain(ANIMAL_NAME_BIT, zip) ? inputStream.readUTF() : null;
                        boolean isSeek = Animal.trueOrNotNullContain(ANIMAL_IS_SEEK_BIT, zip);
                        boolean inNursery = Animal.trueOrNotNullContain(ANIMAL_IN_NURSERY_BIT, zip);

                        AnimalNursery nursery = null;
                        if (Animal.trueOrNotNullContain(NURSERY_BIT, zip)) {
                            String nurseryAddress = Animal.trueOrNotNullContain(NURSERY_ADDRESS_BIT, zip) ? inputStream.readUTF() : null;
                            boolean isWork = Animal.trueOrNotNullContain(NURSERY_IS_WORK_BIT, zip);
                            nursery = new AnimalNursery(nurseryAddress, isWork);
                        }

                        animalList.add(new Animal(nursery, type, animalName, age, isSeek, inNursery));
                    }
                }
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
        return animalList;
    }
}
