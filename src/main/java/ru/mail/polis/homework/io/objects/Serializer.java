package ru.mail.polis.homework.io.objects;


import java.io.*;
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
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeObject(animal);
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
        List<Animal> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = inputStream.readInt();
            for (int i = 0; i < size; ++i) {
                animals.add((Animal) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return animals;
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
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                outputStream.writeObject(animal);
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
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = inputStream.readInt();
            for (int i = 0; i < size; ++i) {
                animals.add((AnimalWithMethods) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return animals;
        }
        return animals;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (ObjectOutputStream outputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                outputStream.writeObject(animal);
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
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = inputStream.readInt();
            for (int i = 0; i < size; ++i) {
                animals.add((AnimalExternalizable) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return animals;
        }
        return animals;
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
        try (DataOutputStream outputStream = new DataOutputStream(new FileOutputStream(fileName))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeInt(animal.getAge());
                outputStream.writeUTF(animal.getName());
                List<String> friendNames = animal.getFriendName();
                outputStream.writeInt(friendNames.size());
                for (String name : friendNames) {
                    outputStream.writeUTF(name);
                }
                outputStream.writeInt(animal.getAnimalSize().getHeight());
                outputStream.writeInt(animal.getAnimalSize().getWidth());
                outputStream.writeUTF(animal.getStructure().name());
                outputStream.writeBoolean(animal.isPredator());
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
        List<Animal> animals = null;
        try (DataInputStream inputStream = new DataInputStream(new FileInputStream(fileName))) {
            int size = inputStream.readInt();
            animals = new ArrayList<>(size);
            for (int i = 0; i < size; ++i) {
                Animal animal = new Animal();
                animal.setAge(inputStream.readInt());
                animal.setName(inputStream.readUTF());
                int nameSize = inputStream.readInt();
                List<String> friendNames = new ArrayList<>(nameSize);
                for (int j = 0; j < nameSize; j++) {
                    friendNames.add(inputStream.readUTF());
                }
                animal.setFriendName(friendNames);
                animal.setAnimalSize(new Size(inputStream.readInt(), inputStream.readInt()));
                animal.setStructure(AnimalStructure.valueOf(inputStream.readUTF()));
                animal.setPredator(inputStream.readBoolean());
                animals.add(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
            return animals;
        }
        return animals;
    }
}
