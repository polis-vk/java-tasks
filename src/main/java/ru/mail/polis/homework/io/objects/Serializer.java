package ru.mail.polis.homework.io.objects;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        List<Animal> ans = new LinkedList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (true) {
                try {
                    ans.add((Animal) objectInputStream.readObject());
                } catch(EOFException e) {
                    return ans;
                }
            }
        }
    }



    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        List<AnimalWithMethods> ans = new LinkedList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (true) {
                try {
                    ans.add((AnimalWithMethods) objectInputStream.readObject());
                } catch(EOFException e) {
                    return ans;
                }
            }
        }
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        List<AnimalExternalizable> ans = new LinkedList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (true) {
                try {
                    ans.add((AnimalExternalizable) objectInputStream.readObject());
                } catch(EOFException e) {
                    return ans;
                }
            }
        }
    }

    /**
     * 2 тугрика
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (Animal animal : animals) {
                objectOutputStream.writeBoolean(animal.isAggressive());
                objectOutputStream.writeBoolean(animal.isVegetarian());
                objectOutputStream.writeInt(animal.getNumOfLegs());
                objectOutputStream.writeDouble(animal.getMaxVelocity());
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeByte(animal.getType().ordinal());
                OwnerOfAnimal owner = animal.getOwner();
                objectOutputStream.writeUTF(owner.getName());
                objectOutputStream.writeUTF(owner.getPhoneNumber());
                objectOutputStream.writeByte(owner.getGender().ordinal());
                Address ownderAddress = owner.getHomeAddress();
                objectOutputStream.writeUTF(ownderAddress.getCountry());
                objectOutputStream.writeUTF(ownderAddress.getCity());
                objectOutputStream.writeUTF(ownderAddress.getStreet());
                objectOutputStream.writeInt(ownderAddress.getNumOfHouse());
                objectOutputStream.writeInt(ownderAddress.getNumOfBuilding());
                objectOutputStream.writeUTF(ownderAddress.getLiter());
                objectOutputStream.writeInt(ownderAddress.getRoom());
            }
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        List<Animal> ans = new LinkedList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (true) {
                try {
                    Animal newAnimal = new Animal(
                            objectInputStream.readBoolean(),
                            objectInputStream.readBoolean(),
                            objectInputStream.readInt(),
                            objectInputStream.readDouble(),
                            objectInputStream.readUTF(),
                            TypeOfAnimal.values()[objectInputStream.readByte()],
                            new OwnerOfAnimal(
                                    objectInputStream.readUTF(),
                                    objectInputStream.readUTF(),
                                    Gender.values()[objectInputStream.readByte()],
                                    new Address(
                                            objectInputStream.readUTF(),
                                            objectInputStream.readUTF(),
                                            objectInputStream.readUTF(),
                                            objectInputStream.readInt(),
                                            objectInputStream.readInt(),
                                            objectInputStream.readUTF(),
                                            objectInputStream.readInt()
                                    )
                            )
                    );
                    ans.add(newAnimal);
                } catch (EOFException e) {
                    return ans;
                }
            }
        }
    }


}
