package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.InputStream;
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
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        List<Animal> ans = new LinkedList<>();
        try (InputStream in = Files.newInputStream(Paths.get(fileName)); ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                ans.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        List<AnimalWithMethods> ans = new LinkedList<>();
        try (InputStream in = Files.newInputStream(Paths.get(fileName));
             ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                ans.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        List<AnimalExternalizable> ans = new LinkedList<>();
        try (InputStream in = Files.newInputStream(Paths.get(fileName));
             ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                ans.add((AnimalExternalizable) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return ans;
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (Animal animal : animals) {
                AnimalNullables animalNullables = new AnimalNullables(animal);
                byte complexData = animalNullables.toByte();
                if (animalNullables.isNull()) {
                    objectOutputStream.writeByte(complexData);
                    continue;
                }
                complexData += ((byte) (((animal.isAggressive() ? 1 : 0) << 1) + (animal.isVegetarian() ? 1 : 0))) << 4;
                objectOutputStream.writeByte(complexData);
                objectOutputStream.writeInt(animal.getNumOfLegs());
                objectOutputStream.writeDouble(animal.getMaxVelocity());
                if (!animalNullables.isNameNull()) {
                    objectOutputStream.writeUTF(animal.getName());
                }
                if (!animalNullables.isTypeNull()) {
                    objectOutputStream.writeByte(animal.getType().getOrd());
                }
                if (!animalNullables.isOwnerNull()) {
                    OwnerOfAnimal owner = animal.getOwner();
                    OwnerNullables ownerNullables = new OwnerNullables(owner);
                    objectOutputStream.writeByte(ownerNullables.toByte());
                    if (!ownerNullables.isNameNull()) {
                        objectOutputStream.writeUTF(owner.getName());
                    }
                    if (!ownerNullables.isPhoneNull()) {
                        objectOutputStream.writeUTF(owner.getPhoneNumber());
                    }
                    if (!ownerNullables.isGenderNull()) {
                        objectOutputStream.writeByte(owner.getGender().ordinal());
                    }
                    if (!ownerNullables.isAddressNull()) {
                        Address address = owner.getHomeAddress();
                        AddressNullables addressNullables = new AddressNullables(address);
                        objectOutputStream.writeByte(addressNullables.toByte());
                        if (!addressNullables.isCountryNull()) {
                            objectOutputStream.writeUTF(address.getCountry());
                        }
                        if (!addressNullables.isCityNull()) {
                            objectOutputStream.writeUTF(address.getCity());
                        }
                        if (!addressNullables.isStreetNull()) {
                            objectOutputStream.writeUTF(address.getStreet());
                        }
                        objectOutputStream.writeInt(address.getNumOfHouse());
                        objectOutputStream.writeInt(address.getNumOfBuilding());
                        if (!addressNullables.isLiterNull()) {
                            objectOutputStream.writeUTF(address.getLiter());
                        }
                        objectOutputStream.writeInt(address.getRoom());
                    }
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
        LinkedList<Animal> ans = new LinkedList<>();
        try (InputStream in = Files.newInputStream(Paths.get(fileName));
             ObjectInputStream objectInputStream = new ObjectInputStream(in)) {
            while (objectInputStream.available() > 0) {
                byte complexData = objectInputStream.readByte();
                AnimalNullables animalNullables = new AnimalNullables((byte) (complexData % 16));
                if (animalNullables.isNull()) {
                    ans.add(null);
                    continue;
                }
                Animal animal = new Animal();
                complexData >>= 4;
                animal.setVegetarian(complexData % 2 == 1);
                complexData >>= 1;
                animal.setAggressive(complexData % 2 == 1);
                animal.setNumOfLegs(objectInputStream.readInt());
                animal.setMaxVelocity(objectInputStream.readDouble());
                if (!animalNullables.isNameNull()) {
                    animal.setName(objectInputStream.readUTF());
                }
                if (!animalNullables.isTypeNull()) {
                    animal.setType(TypeOfAnimal.getByOrd(objectInputStream.readByte()));
                }
                if (!animalNullables.isOwnerNull()) {
                    OwnerOfAnimal owner = new OwnerOfAnimal();
                    animal.setOwner(owner);
                    OwnerNullables ownerNullables = new OwnerNullables(objectInputStream.readByte());
                    if (!ownerNullables.isNameNull()) {
                        owner.setName(objectInputStream.readUTF());
                    }
                    if (!ownerNullables.isPhoneNull()) {
                        owner.setPhoneNumber(objectInputStream.readUTF());
                    }
                    if (!ownerNullables.isGenderNull()) {
                        owner.setGender(Gender.values()[objectInputStream.readByte()]);
                    }
                    if (!ownerNullables.isAddressNull()) {
                        Address address = new Address();
                        owner.setHomeAddress(address);
                        AddressNullables addressNullables = new AddressNullables(objectInputStream.readByte());
                        if (!addressNullables.isCountryNull()) {
                            address.setCountry(objectInputStream.readUTF());
                        }
                        if (!addressNullables.isCityNull()) {
                            address.setCity(objectInputStream.readUTF());
                        }
                        if (!addressNullables.isStreetNull()) {
                            address.setStreet(objectInputStream.readUTF());
                        }
                        address.setNumOfHouse(objectInputStream.readInt());
                        address.setNumOfBuilding(objectInputStream.readInt());
                        if (!addressNullables.isLiterNull()) {
                            address.setLiter(objectInputStream.readUTF());
                        }
                        address.setRoom(objectInputStream.readInt());
                    }
                }
                ans.add(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

}