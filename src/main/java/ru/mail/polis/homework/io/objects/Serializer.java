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
    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (animals == null || fileName == null) {
            return;
        }

        Path file = Paths.get(fileName);
        if (Files.notExists(file.getParent())) {
           return;
        }

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
            return null;
        }

        Path file = Paths.get(fileName);
        if (!Files.isRegularFile(file)) {
            return null;
        }

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
            return;
        }

        Path file = Paths.get(fileName);
        if (Files.notExists(file.getParent())) {
            return;
        }

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
            return null;
        }

        Path file = Paths.get(fileName);
        if (!Files.isRegularFile(file)) {
            return null;
        }

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
            return;
        }

        Path file = Paths.get(fileName);
        if (Files.notExists(file.getParent())) {
            return;
        }

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
            return null;
        }

        Path file = Paths.get(fileName);
        if (!Files.isRegularFile(file)) {
            return null;
        }

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
            return;
        }

        Path file = Paths.get(fileName);
        if (Files.notExists(file.getParent())) {
            return;
        }

        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(file), BUFFER_SIZE))) {
            for (AnimalWithMethods animal : animals) {
                boolean isNull = (animal == null);
                out.writeBoolean(isNull);
                if (!isNull) {
                    out.writeBoolean(animal.isHappy());
                    out.writeBoolean(animal.isAngry());
                    out.writeInt(animal.getLegs());
                    writeString(out, animal.getName());
                    AnimalWithMethods.MoveType moveType = animal.getMoveType();
                    writeString(out, (moveType == null ? null : moveType.name()));
                    writeAddressWithMethods(out, animal.getHomeAddress());
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
            return null;
        }

        Path file = Paths.get(fileName);
        if (!Files.isRegularFile(file)) {
            return null;
        }

        List<AnimalWithMethods> animals = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(new BufferedInputStream(Files.newInputStream(file), BUFFER_SIZE))) {
            try {
                while (true) {
                    boolean isNull = in.readBoolean();
                    if (isNull) {
                        animals.add(null);
                    } else {
                        boolean isHappy = in.readBoolean();
                        boolean isAngry = in.readBoolean();
                        int legs = in.readInt();
                        String name = readString(in);
                        String moveStr = readString(in);
                        AnimalWithMethods.MoveType moveType;
                        if (moveStr != null) {
                            moveType = AnimalWithMethods.MoveType.valueOf(moveStr);
                        } else {
                            moveType = null;
                        }
                        AnimalWithMethods.AddressWithMethods homeAddress = readAddressWithMethods(in);
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

    private static void writeString(DataOutputStream out, String str) throws IOException{
        boolean isStrNull = (str == null);
        out.writeBoolean(isStrNull);
        if (!isStrNull) {
            out.writeUTF(str);
        }
    }

    private static String readString(DataInputStream in) throws IOException {
        boolean isStrNull = in.readBoolean();
        if (isStrNull) {
            return null;
        }
        return in.readUTF();
    }

    private static void writeAddressWithMethods(DataOutputStream out,
                                                AnimalWithMethods.AddressWithMethods address) throws IOException{
        boolean isAddressNull = (address == null);
        out.writeBoolean(isAddressNull);
        if (!isAddressNull) {
            writeString(out, address.getStreet());
            out.writeInt(address.getHouse());
            writeString(out, address.getPhoneNumber());
        }
    }

    private static AnimalWithMethods.AddressWithMethods readAddressWithMethods(DataInputStream in) throws IOException {
        boolean isAddressNull = in.readBoolean();
        if (isAddressNull) {
            return null;
        }
        String street = readString(in);
        int house = in.readInt();
        String phoneNumber = readString(in);
        return new AnimalWithMethods.AddressWithMethods(street, house, phoneNumber);
    }
}
