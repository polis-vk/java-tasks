package ru.mail.polis.homework.io.objects;


import java.io.DataInput;
import java.io.DataInputStream;
import java.io.DataOutput;
import java.io.DataOutputStream;
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
    private static final byte NULL_BYTE = 0;
    private static final byte NOT_NULL_BYTE = 1;

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (animals == null || fileName == null) {
            return;
        }
        Path to = Paths.get(fileName);
        if (Files.notExists(to)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(to))) {
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
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
        Path from = Paths.get(fileName);
        if (Files.notExists(from)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(from))) {
            while (inputStream.available() > 0) {
                animals.add((Animal) inputStream.readObject());
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
        if (animals == null || fileName == null) {
            return;
        }
        Path to = Paths.get(fileName);
        if (Files.notExists(to)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(to))) {
            for (AnimalWithMethods animal : animals) {
                outputStream.writeObject(animal);
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
        Path from = Paths.get(fileName);
        if (Files.notExists(from)) {
            return null;
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(from))) {
            while (inputStream.available() > 0) {
                animals.add((AnimalWithMethods) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        if (animals == null || fileName == null) {
            return;
        }
        Path to = Paths.get(fileName);
        if (Files.notExists(to)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(to))) {
            for (AnimalExternalizable animal : animals) {
                outputStream.writeObject(animal);
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
        Path from = Paths.get(fileName);
        if (Files.notExists(from)) {
            return null;
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(from))) {
            while (inputStream.available() > 0) {
                animals.add((AnimalExternalizable) inputStream.readObject());
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
        if (animals == null || fileName == null) {
            return;
        }
        Path to = Paths.get(fileName);
        if (Files.notExists(to)) {
            return;
        }
        try (DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(to))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    outputStream.writeByte(NULL_BYTE);
                    continue;
                }
                outputStream.writeByte(NOT_NULL_BYTE);
                byte booleansAsByte = (byte) (((animal.isPet() ? 1 : 0) * 2) + (animal.isPredator() ? 1 : 0));
                outputStream.writeByte(booleansAsByte);
                outputStream.writeInt(animal.getLegs());
                writeString(animal.getColor(), outputStream);
                writeString(animal.getMoveType().toString(), outputStream);
                if (animal.getAnimalPassport() == null) {
                    outputStream.writeByte(NULL_BYTE);
                } else {
                    outputStream.writeByte(NOT_NULL_BYTE);
                    writeString(animal.getAnimalPassport().getSpecies(), outputStream);
                    writeString(animal.getAnimalPassport().getSex().toString(), outputStream);
                    writeString(animal.getAnimalPassport().getName(), outputStream);
                    outputStream.writeInt(animal.getAnimalPassport().getAge());
                    outputStream.writeByte(animal.getAnimalPassport().isVaccinated() ? 1 : 0);
                    writeString(animal.getAnimalPassport().getDescriptionOfAnimal(), outputStream);
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
        if (fileName == null) {
            return null;
        }
        Path from = Paths.get(fileName);
        if (Files.notExists(from)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (DataInputStream inputStream = new DataInputStream(Files.newInputStream(from))) {
            while (inputStream.available() > 0) {
                if (inputStream.readByte() == NULL_BYTE) {
                    animals.add(null);
                    continue;
                }
                Animal animal = new Animal();
                byte booleansAsByte = inputStream.readByte();
                animal.setPet((booleansAsByte & 1) != 0);
                animal.setPredator((booleansAsByte & 2) != 0);
                animal.setLegs(inputStream.readInt());
                animal.setColor(readString(inputStream));
                animal.setMoveType(MoveType.valueOf(readString(inputStream)));
                byte objIsNull = inputStream.readByte();
                if (objIsNull == NOT_NULL_BYTE) {
                    Animal.AnimalPassport animalPassport = new Animal.AnimalPassport();
                    animalPassport.setSpecies(readString(inputStream));
                    animalPassport.setSex(Sex.valueOf(readString(inputStream)));
                    animalPassport.setName(readString(inputStream));
                    animalPassport.setAge(inputStream.readInt());
                    animalPassport.setVaccinated(inputStream.readByte() == 1);
                    animalPassport.setDescriptionOfAnimal(readString(inputStream));
                    animal.setAnimalPassport(animalPassport);
                }
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

    private static void writeString(String str, DataOutput out) throws IOException {
        if (str == null) {
            out.writeByte(NULL_BYTE);
            return;
        }
        out.writeByte(NOT_NULL_BYTE);
        out.writeUTF(str);
    }

    private static String readString(DataInput in) throws IOException {
        byte stringIsNull = in.readByte();
        if (stringIsNull == NULL_BYTE) {
            return null;
        }
        return in.readUTF();
    }
}
