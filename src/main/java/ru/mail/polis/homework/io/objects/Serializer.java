package ru.mail.polis.homework.io.objects;


import java.io.*;
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
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
            }
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
        ArrayList<Animal> animals = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                animals.add((Animal) objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return animals;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            int size = objectInputStream.readInt();
            ArrayList<AnimalWithMethods> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
            }
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            int size = objectInputStream.readInt();
            ArrayList<AnimalExternalizable> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
            }
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return null;
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
    public void customSerialize(List<Animal> animals, String fileName) {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeInt(animal.getAge());
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeUTF(animal.getColor().name());
                objectOutputStream.writeInt(animal.getFriends().size());
                for (Animal.Friend friend : animal.getFriends()) {
                    objectOutputStream.writeUTF(friend.getName());
                    objectOutputStream.writeInt(friend.getAge());
                    objectOutputStream.writeUTF(friend.getColor().name());
                }
                objectOutputStream.writeObject(animal.getMother());
                objectOutputStream.writeObject(animal.getFather());
                objectOutputStream.writeDouble(animal.getStrong());
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
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
            int size = objectInputStream.readInt();
            for (int i = 0; i < size; i++) {
                int age = objectInputStream.readInt();
                String name = objectInputStream.readUTF();
                Animal.Color color = Animal.Color.valueOf(objectInputStream.readUTF());

                int sizeFriends = objectInputStream.readInt();
                List<Animal.Friend> friends = new ArrayList<>(sizeFriends);
                for (int j = 0; j < sizeFriends; j++) {
                    String nameFriend = objectInputStream.readUTF();
                    int ageFriend = objectInputStream.readInt();
                    Animal.Color colorFriend = Animal.Color.valueOf(objectInputStream.readUTF());
                    friends.add(new Animal.Friend(nameFriend, ageFriend, colorFriend));
                }

                Animal mother = (Animal) objectInputStream.readObject();
                Animal father = (Animal) objectInputStream.readObject();
                double strong = objectInputStream.readDouble();

                animals.add(new Animal(age, name, color, friends, mother, father, strong));
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return animals;
    }
}
