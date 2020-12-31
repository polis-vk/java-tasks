package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.lang.reflect.Array;
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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (Animal animal : animals)
                out.writeObject(animal);
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
    public List<Animal> defaultDeserialize(String fileName) throws FileNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((Animal) in.readObject());
            }
            return animals;
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
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (AnimalWithMethods animal : animals)
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<AnimalWithMethods> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods) in.readObject());
            }
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (AnimalExternalizable animal : animals)
                out.writeObject(animal);
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<AnimalExternalizable> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable) in.readObject());
            }
            return animals;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeUTF(animal.getName());
                out.writeInt(animal.getAge());
                out.writeObject(animal.getSizeFriend());
                List<String> friends = animal.getFriends();
                for (String friend : friends) {
                    out.writeUTF(friend);
                }
                Animal.Size size = animal.getSize();
                out.writeDouble(size.getWidth());
                out.writeDouble(size.getHeight());
                out.writeDouble(size.getLength());
                out.writeBoolean(animal.isPredator());
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                Animal newAnimal = new Animal();
                newAnimal.setName(in.readUTF());
                newAnimal.setAge(in.readInt());
                newAnimal.setSizeFriend(in.readInt());
                ArrayList<String> friends = new ArrayList<>(newAnimal.getSizeFriend());
                for (int j = 0; j < newAnimal.getSizeFriend(); j++) {
                    friends.add(in.readUTF());
                }
                newAnimal.setFriends(friends);
                Double width = in.readDouble();
                Double height = in.readDouble();
                Double length = in.readDouble();
                Animal.Size newSize = new Animal.Size(width, height, length);
                newAnimal.setSize(newSize);
                newAnimal.setPredator(in.readBoolean());

            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
