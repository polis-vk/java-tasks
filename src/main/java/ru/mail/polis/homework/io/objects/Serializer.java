package ru.mail.polis.homework.io.objects;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
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
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeObject(animal);
            }
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
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int itemNum = 0; itemNum < size; itemNum++) {
                animals.add((Animal) in.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
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
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            List<AnimalWithMethods> animals = new ArrayList<>(size);
            for (int itemNum = 0; itemNum < size; itemNum++) {
                animals.add((AnimalWithMethods) in.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(path))) {
            out.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
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
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (ObjectInputStream in = new ObjectInputStream(Files.newInputStream(path))) {
            int size = in.readInt();
            List<AnimalExternalizable> animals = new ArrayList<>(size);
            for (int itemNum = 0; itemNum < size; itemNum++) {
                animals.add((AnimalExternalizable) in.readObject());
            }
            return animals;
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (DataOutputStream out = new DataOutputStream(new BufferedOutputStream(Files.newOutputStream(path)))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                addAnimalToDataOutputStream(animal, out);
            }
        }
    }

    private void addAnimalToDataOutputStream(Animal animal, DataOutputStream out) throws IOException {
        if (animal.getName() != null) {
            out.writeBoolean(true);
            out.writeUTF(animal.getName());
        } else {
            out.writeBoolean(false);
        }
        if (animal.getBreed() != null) {
            out.writeBoolean(true);
            out.writeUTF(animal.getBreed().toString());
        } else {
            out.writeBoolean(false);
        }
        out.writeInt(animal.getAge());
        if (animal.getEat() != null) {
            out.writeInt(animal.getEat().size());
            for (Eat e : animal.getEat()) {
                out.writeUTF(e.toString());
            }
        } else {
            out.writeInt(0);
        }
        out.writeBoolean(animal.getInWild());
        if (animal.getLocation() != null) {
            out.writeBoolean(true);
            out.writeUTF(animal.getLocation());
        } else {
            out.writeBoolean(false);
        }
        if (animal.getMother() != null) {
            out.writeBoolean(true);
            addAnimalToDataOutputStream((Animal) animal.getMother(), out);
        } else {
            out.writeBoolean(false);
        }
        if (animal.getFather() != null) {
            out.writeBoolean(true);
            addAnimalToDataOutputStream((Animal) animal.getFather(), out);
        } else {
            out.writeBoolean(false);
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        if (!Files.exists(path)) {
            throw new IllegalArgumentException();
        }
        try (DataInputStream in = new DataInputStream(new BufferedInputStream((Files.newInputStream(path))))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int itemNum = 0; itemNum < size; itemNum++) {
                animals.add(getAnimalFromDataOutputStream(in));
            }
            return animals;
        }
    }

    private Animal getAnimalFromDataOutputStream(DataInputStream in) throws IOException {
        String name = in.readBoolean() ? in.readUTF() : null;
        Breeds breed = in.readBoolean() ? Breeds.valueOf(in.readUTF()) : null;
        int age = in.readInt();
        int eatSize = in.readInt();
        List<Eat> eat = eatSize != 0 ? new ArrayList<>(eatSize) : null;
        for (int itemNum = 0; itemNum < eatSize; itemNum++) {
            eat.add(Eat.valueOf(in.readUTF()));
        }
        boolean inWild = in.readBoolean();
        String location = in.readBoolean() ? in.readUTF() : null;
        Animal mother = null;
        if (in.readBoolean()) {
            mother = getAnimalFromDataOutputStream(in);
        }
        Animal father = null;
        if (in.readBoolean()) {
            father = getAnimalFromDataOutputStream(in);
        }
        return new Animal.Builder()
                .setName(name)
                .setBreed(breed)
                .setAge(age)
                .setEat(eat)
                .setInWild(inWild)
                .setLocation(location)
                .setMother(mother)
                .setFather(father)
                .buildAnimal();
    }
}
