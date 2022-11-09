package ru.mail.polis.homework.io.objects;


import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
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
    public List<Animal> defaultDeserialize(String fileName) throws IOException {
        List<Animal> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream((Paths.get(fileName)));
            ObjectInputStream obj = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                try {
                    Animal animal = (Animal) obj.readObject();
                    animals.add(animal);
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }
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
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
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
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException {
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream((Paths.get(fileName)));
             ObjectInputStream obj = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                try {
                    AnimalWithMethods animal = (AnimalWithMethods) obj.readObject();
                    animals.add(animal);
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }
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
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException {
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream((Paths.get(fileName)));
             ObjectInputStream obj = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                try {
                    AnimalExternalizable animal = (AnimalExternalizable) obj.readObject();
                    animals.add(animal);
                } catch(ClassNotFoundException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        try (DataOutputStream in = new DataOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            for(Animal animal : animals) {
                if (animal == null) {
                    in.writeByte(0);
                    continue;
                }
                in.writeByte(1);
                in.writeBoolean(animal.isWild());
                in.writeBoolean(animal.isAquaticAnimal());

                if (animal.getName() == null) {
                    in.writeByte(0);
                } else {
                    in.writeByte(1);
                    in.writeUTF(animal.getName());
                }

                byte indexOfType = (byte) Arrays.stream(AnimalType.values()).collect(Collectors.toList()).indexOf(animal.getAnimalType());
                in.writeByte(indexOfType);

                LocalDate date = animal.getDateOfBirth();
                if (date == null) {
                    in.writeByte(0);
                } else {
                    in.writeByte(1);
                    in.writeInt(date.getYear());
                    in.writeInt(date.getMonthValue());
                    in.writeInt(date.getDayOfMonth());
                }

                in.writeInt(animal.getAge());

                Owner owner = animal.getOwner();
                if (owner == null) {
                    in.writeByte(0);
                } else {
                    in.writeByte(1);
                    in.writeUTF(owner.getSurname());
                    in.writeUTF(owner.getName());
                    in.writeInt(owner.getAge());
                }
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
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream out = new DataInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (out.available() > 0) {
                try {
                    if (out.readByte() == 0) {
                        animals.add(null);
                        continue;
                    }

                    boolean isWild = out.readBoolean();
                    boolean isAquaticAnimal = out.readBoolean();
                    String name = null;
                    LocalDate dateOfBirth = null;
                    int age = 0;
                    Owner owner = null;

                    byte nameHere = out.readByte();
                    if (nameHere != 0) {
                        name = out.readUTF();
                    }

                    AnimalType animalType = AnimalType.values()[out.readByte()];

                    byte dateHere = out.readByte();
                    if (dateHere != 0) {
                        dateOfBirth = LocalDate.of(out.readInt(), out.readInt(), out.readInt());
                    }

                    age = out.readInt();
                    animals.add(new Animal(name, age, isWild, isAquaticAnimal, animalType, dateOfBirth));
                    byte ownerHere = out.readByte();
                    if (ownerHere != 0) {
                        owner = new Owner(out.readUTF(), out.readUTF(), out.readInt());
                    }
                    animals.get(animals.size() - 1).setOwner(owner);
                } catch(IllegalArgumentException e) {
                    e.printStackTrace();
                    return Collections.emptyList();
                }
            }
        }
        return animals;
    }
}
