package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
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

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
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
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
                animals.add((Animal) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                outputStream.writeObject(animal);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
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
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
                animals.add((AnimalWithMethods) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                outputStream.writeObject(animal);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
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
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
                animals.add((AnimalExternalizable) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException exception) {
            throw new RuntimeException(exception);
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
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                byte animalIsNull = (byte) (Objects.equals(animal, null) ? 1 : 0);
                outputStream.writeByte(animalIsNull);
                if (animalIsNull == 1) {
                    continue;
                }

                outputStream.writeInt(animal.getLegs());

                byte hairAndVertebrate = (byte) ((animal.isHair() ? 1 : 0) << 1);
                hairAndVertebrate += animal.isVertebrate() ? 1 : 0;
                outputStream.writeByte(hairAndVertebrate);

                byte nameIsNull = (byte) (animal.getName() == null ? 1 : 0);
                outputStream.writeByte(nameIsNull);
                if (nameIsNull == 0) {
                    outputStream.writeUTF(animal.getName());
                }

                byte typeIsNull = (byte) (animal.getType() == null ? 1 : 0);
                outputStream.writeByte(typeIsNull);
                if (typeIsNull == 0) {
                    outputStream.writeUTF(animal.getType().name());
                }

                byte ownerIsNull = (byte) (animal.getOwner() == null ? 1 : 0);
                outputStream.writeByte(ownerIsNull);
                if (ownerIsNull == 0) {
                    byte ownerNameIsNull = (byte) (animal.getOwner().getName() == null ? 1 : 0);
                    outputStream.writeByte(ownerNameIsNull);
                    if (ownerNameIsNull == 0) {
                        outputStream.writeUTF(animal.getOwner().getName());
                    }

                    byte ownerAddressIsNull = (byte) (animal.getOwner().getAddress() == null ? 1 : 0);
                    outputStream.writeByte(ownerAddressIsNull);
                    if (ownerAddressIsNull == 0) {
                        outputStream.writeUTF(animal.getOwner().getAddress());
                    }

                    outputStream.writeLong(animal.getOwner().getPhoneNumber());
                }
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
    }

    /**
     * 2 тугрика
     * Реализовать ручную десериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int animalsCount = inputStream.readInt();
            for (int i = 0; i < animalsCount; i++) {
                if (inputStream.readByte() == 1) {
                    animals.add(null);
                    continue;
                }

                int legs = inputStream.readInt();

                byte hairAndVertebrate = inputStream.readByte();
                boolean hair = hairAndVertebrate >> 1 % 2 == 1;
                boolean vertebrate = hairAndVertebrate % 2 == 1;

                String name = null;
                if (inputStream.readByte() == 0) {
                    name = inputStream.readUTF();
                }

                AnimalType type = null;
                if (inputStream.readByte() == 0) {
                    type = AnimalType.valueOf(inputStream.readUTF());
                }

                AnimalOwner owner = null;
                if (inputStream.readByte() == 0) {
                    String ownerName = null;
                    if (inputStream.readByte() == 0) {
                        ownerName = inputStream.readUTF();
                    }

                    String ownerAddress = null;
                    if (inputStream.readByte() == 0) {
                        ownerAddress = inputStream.readUTF();
                    }

                    long ownerPhoneNumber = inputStream.readLong();
                    owner = new AnimalOwner(ownerName, ownerAddress, ownerPhoneNumber);
                }

                Animal animal = new Animal(legs, hair, vertebrate, name, type, owner);
                animals.add(animal);
            }
        } catch (IOException exception) {
            throw new RuntimeException(exception);
        }
        return animals;
    }
}
