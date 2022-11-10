package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно реализовать методы этого класса, и реализовать тестирование 4-ех способов записи.
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
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
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
        Path filePath = Paths.get(fileName);
        List<Animal> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
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
        Path filePath = Paths.get(fileName);
        List<AnimalWithMethods> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalList;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path filePath = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
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
        Path filePath = Paths.get(fileName);
        List<AnimalExternalizable> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animalList.add((AnimalExternalizable) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return animalList;
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
        Path filePath = Paths.get(fileName);
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(filePath))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    out.writeByte(0);
                } else {
                    out.writeByte(1);

                    out.writeByte(animal.isPet() ? 1 : 0);
                    out.writeByte(animal.isWild() ? 1 : 0);

                    Integer legsCount = animal.getLegsCount();
                    out.writeByte(assertNullGetBit(legsCount));
                    if (legsCount != null) {
                        out.writeByte(legsCount);
                    }

                    String name = animal.getName();
                    out.writeByte(assertNullGetBit(name));
                    if (name != null) {
                        out.writeUTF(name);
                    }

                    Double age = animal.getAge();
                    out.writeByte(assertNullGetBit(age));
                    if (age != null) {
                        out.writeDouble(age);
                    }

                    MoveType moveType = animal.getMoveType();
                    out.writeByte(assertNullGetBit(moveType));
                    if (moveType != null) {
                        int enumIndex = -1;
                        for (int i = 0; i < MoveType.values().length; i++) {
                            if (MoveType.values()[i] == moveType) {
                                enumIndex = i;
                                break;
                            }
                        }
                        out.writeByte(enumIndex);
                    }

                    Byte id = animal.getId();
                    out.writeByte(assertNullGetBit(id));
                    if (id != null) {
                        out.writeByte(id);
                    }

                    Animal.LivingEnvironment lE = animal.getLivingEnvironment();
                    out.writeByte(assertNullGetBit(lE));
                    if (lE != null) {
                        out.writeDouble(lE.getTemperature());
                        Weather weather = lE.getWeather();
                        out.writeByte(assertNullGetBit(weather));
                        if (weather != null) {
                            int enumIndex = -1;
                            for (int i = 0; i < Weather.values().length; i++) {
                                if (Weather.values()[i] == weather) {
                                    enumIndex = i;
                                    break;
                                }
                            }
                            out.writeByte(enumIndex);
                        }
                    }
                }
                out.flush();
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
        Path filePath = Paths.get(fileName);
        List<Animal> animalList = new ArrayList<>();
        try (InputStream inputStream = Files.newInputStream(filePath);
             DataInputStream dataInputStream = new DataInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                Animal newAnimal = null;
                if (dataInputStream.readByte() > 0) {
                    boolean isPet = dataInputStream.readByte() > 0;
                    boolean isWild = dataInputStream.readByte() > 0;
                    Integer legsCount = null;
                    String name = null;
                    Double age = null;
                    MoveType moveType = null;
                    Byte id = null;

                    if (dataInputStream.readByte() > 0) {
                        legsCount = (int) dataInputStream.readByte();
                    }
                    if (dataInputStream.readByte() > 0) {
                        name = dataInputStream.readUTF();
                    }
                    if (dataInputStream.readByte() > 0) {
                        age = dataInputStream.readDouble();
                    }
                    if (dataInputStream.readByte() > 0) {
                        moveType = MoveType.values()[dataInputStream.readByte()];
                    }
                    if (dataInputStream.readByte() > 0) {
                        id = dataInputStream.readByte();
                    }

                    Animal.LivingEnvironment lE = null;
                    if (dataInputStream.readByte() > 0) {
                        double temperature = dataInputStream.readDouble();
                        Weather weather = null;
                        if (dataInputStream.readByte() > 0) {
                            weather = Weather.values()[dataInputStream.readByte()];
                        }
                        lE = new Animal.LivingEnvironment(temperature, weather);
                    }

                    newAnimal = new Animal(isPet, isWild, legsCount, name, age, moveType, id, lE);
                }
                animalList.add(newAnimal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animalList;
    }

    private static int assertNullGetBit(Object object) {
        return object == null ? 0 : 1;
    }
}
