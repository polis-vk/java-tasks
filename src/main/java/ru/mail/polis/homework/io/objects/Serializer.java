package ru.mail.polis.homework.io.objects;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
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
    private static final byte NAME_NULL = 0b1;
    private static final byte IS_WILD = 0b10;
    private static final byte IS_FED = 0b100;
    private static final byte ANIMAL_ABILITY_TYPE_NULL = 0b1000;
    private static final byte ANIMAL_LOCATION_NULL = 0b10000;
    private static final byte ANIMAL_LOCATION_LONGITUDE_NULL = 0b100000;
    private static final byte ANIMAL_LOCATION_LATITUDE_NULL = 0b1000000;
    private static final byte NULL = -1;
    private static final byte NOT_NULL = 0;

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
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
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalWithMethods animal : animals) {
                if (animal == null) {
                    objectOutputStream.writeByte(NULL);
                    objectOutputStream.flush();
                    continue;
                }
                objectOutputStream.writeByte(NOT_NULL);
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
        List<AnimalWithMethods> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (objectInputStream.available() > 0) {
                if (objectInputStream.readByte() == NULL) {
                    list.add(null);
                    continue;
                }
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
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalExternalizable animal : animals) {
                if (animal == null) {
                    objectOutputStream.writeByte(NULL);
                    objectOutputStream.flush();
                    continue;
                }
                objectOutputStream.writeByte(NOT_NULL);
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
        List<AnimalExternalizable> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (objectInputStream.available() > 0) {
                if (objectInputStream.readByte() == NULL) {
                    list.add(null);
                    continue;
                }
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
        try (final ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    objectOutputStream.writeByte(NULL);
                    objectOutputStream.flush();
                    continue;
                }
                objectOutputStream.writeByte(NOT_NULL);
                byte info = 0;
                if (animal.getName() == null) {
                    info |= NAME_NULL;
                }
                if (animal.isWild()) {
                    info |= IS_WILD;
                }
                if (animal.isFed()) {
                    info |= IS_FED;
                }
                if (animal.getAnimalAbilityType() == null) {
                    info |= ANIMAL_ABILITY_TYPE_NULL;
                }
                if (animal.getAnimalLocation() == null) {
                    info |= ANIMAL_LOCATION_NULL;
                } else {
                    if (animal.getAnimalLocation().getLongitude() == null) {
                        info |= ANIMAL_LOCATION_LONGITUDE_NULL;
                    }
                    if (animal.getAnimalLocation().getLatitude() == null) {
                        info |= ANIMAL_LOCATION_LATITUDE_NULL;
                    }
                }
                objectOutputStream.writeByte(info);
                // Write Location first as it's an object
                if (animal.getAnimalLocation() != null) {
                    if (animal.getAnimalLocation().getLongitude() != null) {
                        objectOutputStream.writeUTF(animal.getAnimalLocation().getLongitude());
                    }
                    if (animal.getAnimalLocation().getLatitude() != null) {
                        objectOutputStream.writeUTF(animal.getAnimalLocation().getLatitude());
                    }
                }
                // Write other fields of superclass
                if (animal.getName() != null) {
                    objectOutputStream.writeUTF(animal.getName());
                }
                objectOutputStream.writeInt(animal.getAge());
                if (animal.getAnimalAbilityType() != null) {
                    objectOutputStream.writeUTF(animal.getAnimalAbilityType().toString());
                }
                objectOutputStream.flush();
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
        if (Files.notExists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
        List<Animal> list = new ArrayList<>();
        try (final FileInputStream fileInputStream = new FileInputStream(fileName);
             final ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream)) {
            while (objectInputStream.available() > 0) {
                if (objectInputStream.readByte() == NULL) {
                    list.add(null);
                    continue;
                }
                Animal animal = new Animal();
                int info = objectInputStream.readByte();
                // Read Location first
                if ((info & ANIMAL_LOCATION_NULL) == 0) {
                    animal.setAnimalLocation(new Location());
                    if ((info & ANIMAL_LOCATION_LONGITUDE_NULL) == 0) {
                        animal.getAnimalLocation().setLongitude(objectInputStream.readUTF());
                    }
                    if ((info & ANIMAL_LOCATION_LATITUDE_NULL) == 0) {
                        animal.getAnimalLocation().setLatitude(objectInputStream.readUTF());
                    }
                }
                // Read other fields
                if ((info & NAME_NULL) == 0) {
                    animal.setName(objectInputStream.readUTF());
                }
                animal.setAge(objectInputStream.readInt());
                if ((info & IS_WILD) == IS_WILD) {
                    animal.setWild(true);
                }
                if ((info & IS_FED) == IS_FED) {
                    animal.setFed(true);
                }
                if ((info & ANIMAL_ABILITY_TYPE_NULL) == 0) {
                    animal.setAnimalAbilityType(AnimalAbilityType.valueOf(objectInputStream.readUTF()));
                }
                list.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return list;
    }
}
