package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.InputStream;
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
        try (InputStream inputStream = Files.newInputStream(from);
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
        try (InputStream inputStream = Files.newInputStream(from);
             ObjectInputStream objectInputStream = new ObjectInputStream(inputStream)) {
            while (inputStream.available() > 0) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
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
        try (InputStream inputStream = Files.newInputStream(from);
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
        if (animals == null || fileName == null) {
            return;
        }
        Path to = Paths.get(fileName);
        if (Files.notExists(to)) {
            return;
        }
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(to))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    outputStream.writeByte(NULL_BYTE);
                    continue;
                }
                outputStream.writeByte(NOT_NULL_BYTE);
                byte booleansAsByte = (byte) (((animal.isPet() ? 1 : 0) << 1) + (animal.isPredator() ? 1 : 0));
                outputStream.writeByte(booleansAsByte);
                outputStream.writeInt(animal.getLegs());
                SerializationUtilMethods.writeString(animal.getColor(), outputStream);
                MoveType moveType = animal.getMoveType();
                SerializationUtilMethods.writeString(moveType == null ? null : moveType.toString(), outputStream);
                if (animal.getAnimalPassport() == null) {
                    outputStream.writeByte(NULL_BYTE);
                } else {
                    outputStream.writeByte(NOT_NULL_BYTE);
                    SerializationUtilMethods.writeString(animal.getAnimalPassport().getSpecies(), outputStream);
                    Sex sex = animal.getAnimalPassport().getSex();
                    SerializationUtilMethods.writeString(sex == null ? null : sex.toString(), outputStream);
                    SerializationUtilMethods.writeString(animal.getAnimalPassport().getName(), outputStream);
                    outputStream.writeInt(animal.getAnimalPassport().getAge());
                    outputStream.writeByte(animal.getAnimalPassport().isVaccinated() ? 1 : 0);
                    SerializationUtilMethods.writeString(animal.getAnimalPassport().getDescriptionOfAnimal(), outputStream);
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
        try (InputStream inputStream = Files.newInputStream(from);
             ObjectInputStream objInputStream = new ObjectInputStream(inputStream)) {
            while (objInputStream.available() > 0) {
                if (objInputStream.readByte() == NULL_BYTE) {
                    animals.add(null);
                    continue;
                }
                Animal animal = new Animal();
                byte booleansAsByte = objInputStream.readByte();
                animal.setPredator((booleansAsByte & 1) != 0);
                animal.setPet((booleansAsByte & 2) != 0);
                animal.setLegs(objInputStream.readInt());
                animal.setColor(SerializationUtilMethods.readString(objInputStream));
                String moveTypeValue = SerializationUtilMethods.readString(objInputStream);
                animal.setMoveType(moveTypeValue == null ? null : MoveType.valueOf(moveTypeValue));
                byte objIsNull = objInputStream.readByte();
                if (objIsNull == NOT_NULL_BYTE) {
                    AnimalPassport animalPassport = new AnimalPassport();
                    animal.setAnimalPassport(animalPassport);
                    animalPassport.setSpecies(SerializationUtilMethods.readString(objInputStream));
                    String sexValue = SerializationUtilMethods.readString(objInputStream);
                    animal.getAnimalPassport().setSex(sexValue == null ? null : Sex.valueOf(sexValue));
                    animalPassport.setName(SerializationUtilMethods.readString(objInputStream));
                    animalPassport.setAge(objInputStream.readInt());
                    animalPassport.setVaccinated(objInputStream.readByte() == 1);
                    animalPassport.setDescriptionOfAnimal(SerializationUtilMethods.readString(objInputStream));
                    animal.setAnimalPassport(animalPassport);
                }
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

}
