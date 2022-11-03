package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInput;
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
                byte twoBools = (byte) ((animal.isAggressive() ? 0 : 1) << 1);
                twoBools += (animal.isVegetarian() ? 0 : 1);
                objectOutputStream.writeByte(FieldOfAnimal.IS_AGGRESSIVE_IS_VEGETARIAN.getOrd());
                objectOutputStream.writeByte(twoBools);
                objectOutputStream.writeByte(FieldOfAnimal.NUM_OF_LEGS.getOrd());
                objectOutputStream.writeInt(animal.getNumOfLegs());
                objectOutputStream.writeByte(FieldOfAnimal.MAX_VELOCITY.getOrd());
                objectOutputStream.writeDouble(animal.getMaxVelocity());
                if (animal.getType() != null) {
                    objectOutputStream.writeByte(FieldOfAnimal.TYPE.getOrd());
                    objectOutputStream.writeByte(animal.getType().ordinal());
                }
                if (animal.getName() != null) {
                    objectOutputStream.writeByte(FieldOfAnimal.NAME.getOrd());
                    objectOutputStream.writeUTF(animal.getName());
                }
                if (animal.getOwner() != null) {
                    objectOutputStream.writeByte(FieldOfAnimal.START_OF_OWNER.getOrd());
                    OwnerOfAnimal owner = animal.getOwner();
                    if (owner.getName() != null) {
                        objectOutputStream.writeByte(FieldOfOwner.NAME.getOrd());
                        objectOutputStream.writeUTF(owner.getName());
                    }
                    if (owner.getPhoneNumber() != null) {
                        objectOutputStream.writeByte(FieldOfOwner.NUMBER.getOrd());
                        objectOutputStream.writeUTF(owner.getPhoneNumber());
                    }
                    if (owner.getGender() != null) {
                        objectOutputStream.writeByte(FieldOfOwner.GENDER.getOrd());
                        objectOutputStream.writeByte(owner.getGender().ordinal());
                    }
                    if (owner.getHomeAddress() != null) {
                        objectOutputStream.writeByte(FieldOfOwner.START_OF_ADDRESS.getOrd());
                        Address address = owner.getHomeAddress();
                        if (address.getCountry() != null) {
                            objectOutputStream.writeByte(FieldOfAddress.COUNTRY.getOrd());
                            objectOutputStream.writeUTF(address.getCountry());
                        }
                        if (address.getCity() != null) {
                            objectOutputStream.writeByte(FieldOfAddress.CITY.getOrd());
                            objectOutputStream.writeUTF(address.getCity());
                        }
                        if (address.getStreet() != null) {
                            objectOutputStream.writeByte(FieldOfAddress.STREET.getOrd());
                            objectOutputStream.writeUTF(address.getStreet());
                        }
                        objectOutputStream.writeByte(FieldOfAddress.NUM_OF_HOUSE.getOrd());
                        objectOutputStream.writeInt(address.getNumOfHouse());
                        objectOutputStream.writeByte(FieldOfAddress.NUM_OF_BUILDING.getOrd());
                        objectOutputStream.writeInt(address.getNumOfBuilding());
                        if (address.getLiter() != null) {
                            objectOutputStream.writeByte(FieldOfAddress.LITER.getOrd());
                            objectOutputStream.writeUTF(address.getLiter());
                        }
                        objectOutputStream.writeByte(FieldOfAddress.ROOM.getOrd());
                        objectOutputStream.writeInt(address.getRoom());
                        objectOutputStream.writeByte(FieldOfAddress.END_OF_ADDRESS.getOrd());
                    }
                    objectOutputStream.writeByte(FieldOfOwner.END_OF_OWNER.getOrd());
                }
                objectOutputStream.writeByte(FieldOfAnimal.END_OF_ANIMAL.getOrd());
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
                    ans.add(customDeserializeAnimal(objectInputStream));
                }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ans;
    }

    private Animal customDeserializeAnimal(ObjectInputStream in) throws IOException {
        Animal ans = new Animal();

        while (true) {
            FieldOfAnimal animalKey = FieldOfAnimal.getByOrd(in.readByte());
            switch (animalKey) {
                case IS_AGGRESSIVE_IS_VEGETARIAN:
                    byte twoBools = in.readByte();
                    ans.setVegetarian((twoBools % 2) == 0);
                    twoBools >>= 1;
                    ans.setAggressive((twoBools % 2) == 0);
                    break;
                case NUM_OF_LEGS:
                    ans.setNumOfLegs(in.readInt());
                    break;
                case MAX_VELOCITY:
                    ans.setMaxVelocity(in.readDouble());
                    break;
                case TYPE:
                    ans.setType(TypeOfAnimal.values()[in.readByte()]);
                    break;
                case NAME:
                    ans.setName(in.readUTF());
                    break;
                case START_OF_OWNER:
                    ans.setOwner(customDeserializeOwner(in));
                    break;
            }

            if (animalKey == FieldOfAnimal.END_OF_ANIMAL) {
                return ans;
            }
        }
    }

    private OwnerOfAnimal customDeserializeOwner(ObjectInput in) throws IOException {
        OwnerOfAnimal ans = new OwnerOfAnimal();
        while (true) {
            FieldOfOwner field = FieldOfOwner.getByOrd(in.readByte());
            switch (field) {
                case NAME:
                    ans.setName(in.readUTF());
                    break;
                case NUMBER:
                    ans.setPhoneNumber(in.readUTF());
                    break;
                case GENDER:
                    ans.setGender(Gender.values()[in.readByte()]);
                    break;
                case START_OF_ADDRESS:
                    ans.setHomeAddress(customDeserializeAddress(in));
                    break;
            }
            if (field == FieldOfOwner.END_OF_OWNER) {
                return ans;
            }
        }
    }

    private Address customDeserializeAddress(ObjectInput in) throws IOException {
        Address ans = new Address();
        while (true) {
            FieldOfAddress field = FieldOfAddress.getByOrd(in.readByte());
            switch (field) {
                case COUNTRY:
                    ans.setCountry(in.readUTF());
                    break;
                case CITY:
                    ans.setCity(in.readUTF());
                    break;
                case STREET:
                    ans.setStreet(in.readUTF());
                    break;
                case NUM_OF_HOUSE:
                    ans.setNumOfHouse(in.readInt());
                    break;
                case NUM_OF_BUILDING:
                    ans.setNumOfBuilding(in.readInt());
                    break;
                case LITER:
                    ans.setLiter(in.readUTF());
                    break;
                case ROOM:
                    ans.setRoom(in.readInt());
                    break;
            }

            if (field == FieldOfAddress.END_OF_ADDRESS) {
                break;
            }
        }
        return ans;
    }

}