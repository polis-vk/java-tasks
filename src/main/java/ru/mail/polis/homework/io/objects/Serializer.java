package ru.mail.polis.homework.io.objects;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream((Files.newOutputStream(path)))) {
            outputStream.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectInputStream inputStream = new ObjectInputStream((Files.newInputStream(path)))) {
            return (List<Animal>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {

    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        return Collections.emptyList();
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {

    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        return Collections.emptyList();
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeUTF(animal.getName());
                outputStream.writeInt(animal.getAge());
                outputStream.writeUTF(animal.getGender().name());
                outputStream.writeUTF(animal.getType().name());
                outputStream.writeUTF(animal.getHome().getCountryName());
                outputStream.writeDouble(animal.getHome().getLatitude());
                outputStream.writeDouble(animal.getHome().getLongitude());
                List<Ability> abilities = animal.getAbilities();
                outputStream.writeInt(abilities.size());
                for (Ability ability : abilities) {
                    outputStream.writeUTF(ability.name());
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        Path path = Paths.get(fileName);
        List<Animal> deserializedList = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream((Files.newInputStream(path)))) {
            int listSize = input.readInt();
            for (int i = 0; i < listSize; i++) {
                Animal animal = new Animal();
                animal.setName(input.readUTF());
                animal.setAge(input.readInt());
                animal.setGender(Gender.valueOf(input.readUTF()));
                animal.setType(ru.mail.polis.homework.io.objects.Type.valueOf(input.readUTF()));
                animal.setHome(new Place(input.readUTF(), input.readDouble(), input.readDouble()));
                int abilitiesListSize = input.readInt();
                List<Ability> abilities = new ArrayList<>(abilitiesListSize);
                for (int j = 0; j < abilitiesListSize; j++) {
                    abilities.add(Ability.valueOf(input.readUTF()));
                }
                animal.setAbilities(abilities);
                deserializedList.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return deserializedList;
    }
}
