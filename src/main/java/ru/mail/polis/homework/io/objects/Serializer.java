package ru.mail.polis.homework.io.objects;


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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream output =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            output.writeObject(animals);
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        try (ObjectInputStream input =
                     new ObjectInputStream(Files.newInputStream(path))) {
            return (List<Animal>) input.readObject();
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream output =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalWithMethods animal : animals) {
                animal.writeObject(output);
            }
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<AnimalWithMethods> resultList = new ArrayList<>();
        try (ObjectInputStream input =
                     new ObjectInputStream(Files.newInputStream(path))) {
            while (true) {
                try {
                    AnimalWithMethods animal = new AnimalWithMethods();
                    animal.readObject(input);
                    resultList.add(animal);
                } catch (IOException e) {
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream output =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            for (AnimalExternalizable animal : animals) {
                animal.writeExternal(output);
            }
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<AnimalExternalizable> resultList = new ArrayList<>();
        try (ObjectInputStream input =
                     new ObjectInputStream(Files.newInputStream(path))) {
            while (true) {
                try {
                    AnimalExternalizable animalExternalizable = new AnimalExternalizable();
                    animalExternalizable.readExternal(input);
                    resultList.add(animalExternalizable);
                } catch (IOException e) {
                    break;
                }
            }
        }
        return resultList;
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out =
                     new ObjectOutputStream(Files.newOutputStream(path))) {
            for (Animal animal : animals) {
                out.writeUTF(animal.getAnimalKind().name());
                out.writeUTF(animal.getName());
                out.writeInt(animal.getAge());
                out.writeInt(animal.getWeight());
                List<String> locationList= animal.getLocationsList();
                out.writeInt(locationList.size());
                for (String location : locationList)
                    out.writeUTF(location);
                out.writeUTF(animal.getColour().name());
            }
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
    public List<Animal> customDeserialize(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);
        List<Animal> resultList = new ArrayList<>();
        try (ObjectInputStream in =
                     new ObjectInputStream(Files.newInputStream(path))) {
            while (true) {
                try {
                    Animal.Builder animal = Animal.newBuilder();
                    animal.setAnimalKind(AnimalKind.valueOf(in.readUTF()));
                    animal.setName(in.readUTF());
                    animal.setAge(in.readInt());
                    animal.setWeight(in.readInt());
                    List<String> locationList = new ArrayList<>();
                    int size = in.readInt();
                    for (int i = 0; i < size; i++) {
                        locationList.add(in.readUTF());
                    }
                    animal.setLocationList(locationList);
                    animal.setColour(Colour.valueOf(in.readUTF()));
                    resultList.add(animal.build());
                } catch (IOException e) {
                    break;
                }
            }
        }
        return resultList;
    }
}
