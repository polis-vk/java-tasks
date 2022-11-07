package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
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
    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animals.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return null;
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return null;
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    objectOutputStream.writeByte(NULL_VALUE);
                } else {
                    objectOutputStream.writeByte(NOT_NULL_VALUE);
                    if (animal.getName() == null) {
                        objectOutputStream.writeByte(NULL_VALUE);
                    } else {
                        objectOutputStream.writeByte(NOT_NULL_VALUE);
                        objectOutputStream.writeUTF(animal.getName());
                    }

                    objectOutputStream.writeInt(animal.getAge());
                    objectOutputStream.writeDouble(animal.getWeight());
                    objectOutputStream.writeBoolean(animal.isAlive());
                    if (animal.getType() == null) {
                        objectOutputStream.writeByte(NULL_VALUE);
                    } else {
                        objectOutputStream.writeUTF(animal.getType().toString());
                    }
                    objectOutputStream.writeBoolean(animal.isPet());
                    if (animal.getPlaceOfResidence() == null) {
                        objectOutputStream.writeByte(NULL_VALUE);
                    } else {
                        objectOutputStream.writeByte(NOT_NULL_VALUE);
                        String country = animal.getPlaceOfResidence().getCountry();
                        if (country == null) {
                            objectOutputStream.writeByte(NULL_VALUE);
                        } else {
                            objectOutputStream.writeByte(NOT_NULL_VALUE);
                            objectOutputStream.writeUTF(country);
                        }
                        String terrain = animal.getPlaceOfResidence().getTerrain();
                        if (terrain == null) {
                            objectOutputStream.writeByte(NULL_VALUE);
                        } else {
                            objectOutputStream.writeByte(NOT_NULL_VALUE);
                            objectOutputStream.writeUTF(terrain);
                        }
                    }
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
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(path);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                if (objectInputStream.readByte() == NULL_VALUE) {
                    animals.add(null);
                } else {
                    String name;
                    if (objectInputStream.readByte() == NULL_VALUE) {
                        name = null;
                    } else {
                        name = objectInputStream.readUTF();
                    }
                    int age = objectInputStream.readInt();
                    double weight = objectInputStream.readDouble();
                    boolean alive = objectInputStream.readBoolean();
                    AnimalType type;
                    if (objectInputStream.readByte() == NULL_VALUE) {
                        type = null;
                    } else {
                        type = AnimalType.valueOf(objectInputStream.readUTF());
                    }
                    boolean isPet = objectInputStream.readBoolean();
                    PlaceOfResidence placeOfResidence = new PlaceOfResidence();
                    if (objectInputStream.readByte() != NULL_VALUE) {
                        if (objectInputStream.readByte() == NULL_VALUE) {
                            placeOfResidence.setCountry(null);
                        } else {
                            placeOfResidence.setCountry(objectInputStream.readUTF());
                        }
                        if (objectInputStream.readByte() == NULL_VALUE) {
                            placeOfResidence.setTerrain(null);
                        } else {
                            placeOfResidence.setTerrain(objectInputStream.readUTF());
                        }
                    }
                    animals.add(new Animal(name, age, weight, alive, type, isPet, placeOfResidence));
                }

            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
