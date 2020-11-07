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
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeObject(animals);
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
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            return (List<Animal>) objectInputStream.readObject();
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeInt(animals.size());
            for (AnimalWithMethods animalWithMethods : animals) {
                objectOutputStream.writeObject(animalWithMethods);
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
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException {
        List<AnimalWithMethods> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int countOfObjects = objectInputStream.readInt();
            for (int i = 0; i < countOfObjects; i++) {
                list.add((AnimalWithMethods) objectInputStream.readObject());
            }
        } catch (ClassNotFoundException ignored) { }
        return list;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
            objectOutputStream.writeInt(animals.size());
            for (AnimalExternalizable animal: animals) {
                animal.writeExternal(objectOutputStream);
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
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException {
        List<AnimalExternalizable> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int countOfObjects = objectInputStream.readInt();
            for (int i = 0; i < countOfObjects; i++) {
                AnimalExternalizable current = new AnimalExternalizable();
                current.readExternal(objectInputStream);
                list.add(current);
            }
        } catch (ClassNotFoundException ignored) { }
        return list;
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
        try (FileOutputStream outputStream = new FileOutputStream(fileName)) {
            try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream)) {
                objectOutputStream.writeInt(animals.size());
                for (Animal animal : animals) {
                    objectOutputStream.writeUTF(animal.getTypeAnimal().name());
                    objectOutputStream.writeUTF(animal.getNickname());
                    objectOutputStream.writeInt(animal.getWeight());
                    objectOutputStream.writeInt(animal.getAge());
                    //Animal sizes
                    objectOutputStream.writeInt(animal.getAnimalSizes().size());
                    for (int size : animal.getAnimalSizes()) {
                        objectOutputStream.writeInt(size);
                    }
                    objectOutputStream.writeUTF(animal.getMainColor().name());
                    objectOutputStream.writeUTF(animal.getEyeColor().name());
                    objectOutputStream.writeInt(animal.getNumberOfLimbs());
                    objectOutputStream.writeUTF(animal.getSex().name());
                    //Habitat
                    objectOutputStream.writeUTF(animal.getHabitat().getPlaceName());
                    objectOutputStream.writeInt(animal.getHabitat().getAverageTemperature());
                    objectOutputStream.writeInt(animal.getHabitat().getHeightAboveSeaLevel());
                }
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        List<Animal> list = new ArrayList<>();
        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            int countOfObjects = objectInputStream.readInt();
            for (int i = 0; i < countOfObjects; i++) {
                Animal current = new Animal();
                current.setTypeAnimal(Animal.AnimalTypeClass.valueOf(objectInputStream.readUTF()));
                current.setNickname(objectInputStream.readUTF());
                current.setWeight(objectInputStream.readInt());
                current.setAge(objectInputStream.readInt());
                int size = objectInputStream.readInt();
                List<Integer> listSize = new ArrayList<>();
                for (int j = 0; j < size; j++) {
                    listSize.add(objectInputStream.readInt());
                }
                current.setAnimalSizes(listSize);
                current.setMainColor(Animal.Color.valueOf(objectInputStream.readUTF()));
                current.setEyeColor(Animal.Color.valueOf(objectInputStream.readUTF()));
                current.setNumberOfLimbs(objectInputStream.readInt());
                current.setSex(Animal.Sex.valueOf(objectInputStream.readUTF()));
                current.getHabitat().setPlaceName(objectInputStream.readUTF());
                current.getHabitat().setAverageTemperature(objectInputStream.readInt());
                current.getHabitat().setHeightAboveSeaLevel(objectInputStream.readInt());
                list.add(current);
            }
        } catch (EOFException ignored) { }
        return list;
    }
}
