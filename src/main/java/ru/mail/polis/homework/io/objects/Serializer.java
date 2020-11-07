package ru.mail.polis.homework.io.objects;


import java.io.DataInputStream;
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
    public static void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(animals);
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public static List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<Animal>) in.readObject();
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(animals);
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
    public static List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<AnimalWithMethods>) in.readObject();
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeObject(animals);
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
    public static List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        Path path = Paths.get(fileName);

        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            return (List<AnimalExternalizable>) in.readObject();
        }
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public static void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);

        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (Animal a : animals) {
                writeAnimal(a, out);
            }
        }
    }

    public static void writeAnimal(Animal a, DataOutputStream out) throws IOException {
        out.writeInt(a.getHabitat().size());
        for (String s : a.getHabitat()) {
            out.writeUTF(s);
        }
        out.writeUTF(a.getSpecies());
        out.writeShort(a.getAge());
        out.writeUTF(a.getGender().toString());
        out.writeBoolean(a.isRealExistence());

        out.writeInt(a.getChildren().size());
        for (Animal ch : a.getChildren()) {
            writeAnimal(ch, out);
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
    public static List<Animal> customDeserialize(String fileName) throws IOException {
        Path path = Paths.get(fileName);

        List<Animal> list = new ArrayList<>();
        try (DataInputStream in = new DataInputStream(Files.newInputStream(path))) {
            for(int i = in.readInt(); i > 0; i--){
                list.add(readAnimal(in));
            }
        }
        return list;
    }

    public static Animal readAnimal(DataInputStream in) throws IOException {

        ArrayList<String> habitat = new ArrayList<>();

        for (int i = in.readInt(); i > 0; i--) {
            habitat.add(in.readUTF());
        }

        String species = in.readUTF();
        short age = in.readShort();
        Animal.Gender gender = Animal.Gender.valueOf(in.readUTF());
        boolean realExistence = in.readBoolean();
        ArrayList<Animal> children = new ArrayList<>();
        for (int i = in.readInt(); i > 0; i--) {
            children.add(readAnimal(in));
        }
        return new Animal(habitat, species, age, gender, realExistence, children);
    }
}
