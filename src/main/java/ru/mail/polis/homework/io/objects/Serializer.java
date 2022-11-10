package ru.mail.polis.homework.io.objects;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
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
    private static final int BUFFER_SIZE = 256 * 1024;
    private final static int IS_ANIMAL_NUL_POS = 0;
    private final static int IS_HAPPY_POS = 1;
    private final static int IS_ANGRY_POS = 2;
    private final static int IS_NAME_NULL_POS = 3;
    private final static int IS_MOVE_NULL_POS = 4;
    private final static int IS_ADDRESS_NULL_POS = 5;
    private static final int IS_STREET_NULL_POS = 6;
    private static final int IS_PHONE_NULL_POS = 7;
    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (animals == null || fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file), BUFFER_SIZE))) {
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
        if (fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE))) {
            try {
                while (true) {
                    animals.add((Animal) in.readObject());
                }
            } catch (EOFException e) {
                return animals;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        if (animals == null || fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file), BUFFER_SIZE))) {
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
        if (fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE))) {
            try {
                while (true) {
                    animals.add((AnimalWithMethods) in.readObject());
                }
            } catch (EOFException e) {
                return animals;
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        if (animals == null || fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(new BufferedOutputStream(Files.newOutputStream(file), BUFFER_SIZE))) {
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
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
        if (fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream in = new ObjectInputStream(new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE))) {
            try {
                while (true) {
                    animals.add((AnimalExternalizable) in.readObject());
                }
            } catch (EOFException e) {
                return animals;
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
    public void customSerialize(List<AnimalWithMethods> animals, String fileName) {
        if (animals == null || fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file), BUFFER_SIZE))) {
            for (AnimalWithMethods animal : animals) {
                byte booleans = setBoolAt((byte) 0, IS_ANIMAL_NUL_POS, animal == null);
                if (!getBoolAt(booleans, IS_ANIMAL_NUL_POS)) {
                    booleans = setBoolAt(booleans, IS_HAPPY_POS, animal.isHappy());
                    booleans = setBoolAt(booleans, IS_ANGRY_POS, animal.isAngry());
                    booleans = setBoolAt(booleans, IS_NAME_NULL_POS, animal.getName() == null);
                    booleans = setBoolAt(booleans, IS_MOVE_NULL_POS, animal.getMoveType() == null);

                    AnimalWithMethods.AddressWithMethods address = animal.getHomeAddress();
                    booleans = setBoolAt(booleans, IS_ADDRESS_NULL_POS, address == null);
                    if (!getBoolAt(booleans, IS_ADDRESS_NULL_POS)) {
                        booleans = setBoolAt(booleans, IS_STREET_NULL_POS, address.getStreet() == null);
                        booleans = setBoolAt(booleans, IS_PHONE_NULL_POS, address.getPhoneNumber() == null);
                    }

                    out.writeByte(booleans);
                    out.writeInt(animal.getLegs());
                    if (!getBoolAt(booleans, IS_NAME_NULL_POS)) out.writeUTF(animal.getName());
                    if (!getBoolAt(booleans, IS_MOVE_NULL_POS)) out.writeUTF(animal.getMoveType().name());
                    if (!getBoolAt(booleans, IS_ADDRESS_NULL_POS)) {
                        if (!getBoolAt(booleans, IS_STREET_NULL_POS)) out.writeUTF(address.getStreet());
                        out.writeInt(address.getHouse());
                        if (!getBoolAt(booleans, IS_PHONE_NULL_POS)) out.writeUTF(address.getPhoneNumber());
                    }
                }  else {
                    out.writeByte(booleans);
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
    public List<AnimalWithMethods> customDeserialize(String fileName) {
        if (fileName == null) {
            throw new IllegalArgumentException();
        }

        Path file = Paths.get(fileName);
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE))) {
            try {
                while (true) {
                    byte booleans = in.readByte();
                    if (getBoolAt(booleans, IS_ANIMAL_NUL_POS)) {
                        animals.add(null);
                    } else {
                        boolean isHappy = getBoolAt(booleans, IS_HAPPY_POS);
                        boolean isAngry = getBoolAt(booleans, IS_ANGRY_POS);
                        int legs = in.readInt();
                        String name = (getBoolAt(booleans, IS_NAME_NULL_POS) ? null : in.readUTF());
                        AnimalWithMethods.MoveType moveType = (getBoolAt(booleans, IS_MOVE_NULL_POS) ? null :
                                AnimalWithMethods.MoveType.valueOf(in.readUTF()));
                        AnimalWithMethods.AddressWithMethods homeAddress = null;
                        if (!getBoolAt(booleans, IS_ADDRESS_NULL_POS)) {
                            String street = (getBoolAt(booleans, IS_STREET_NULL_POS) ? null : in.readUTF());
                            int house = in.readInt();
                            String phone = (getBoolAt(booleans, IS_PHONE_NULL_POS) ? null : in.readUTF());
                            homeAddress = new AnimalWithMethods.AddressWithMethods(street, house, phone);
                        }
                        animals.add(new AnimalWithMethods(isHappy, isAngry, legs, name, moveType, homeAddress));
                    }
                }
            } catch (EOFException e) {
                return animals;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animals;
    }

    private static byte setBoolAt(byte byt, int i, boolean bool) {
        if (bool) {
            return (byte) (byt | (1 << i));
        }
        return (byte) (byt & ~(1 << i));
    }

    private static boolean getBoolAt(byte byt, int i) {
        return (byt & (1 << i)) != 0;
    }
}
