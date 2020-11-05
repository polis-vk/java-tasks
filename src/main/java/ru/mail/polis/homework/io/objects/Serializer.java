package ru.mail.polis.homework.io.objects;


import java.io.*;
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
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeObject(animals);
        } catch (IOException e){
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

        try (ObjectInputStream objectInputStream = new ObjectInputStream((new FileInputStream(fileName)))) {
            animals = (List<Animal>)objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return animals;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalWithMethods animal : animals) {
                animal.writeObject(objectOutputStream);
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
        AnimalWithMethods animal = new AnimalWithMethods();

        try (ObjectInputStream objectInputStream = new ObjectInputStream((new FileInputStream(fileName)))) {
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods)objectInputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return animals;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (AnimalExternalizable animal : animals) {
                try {
                    animal.writeExternal(objectOutputStream);
                } catch (IOException e) {
                    e.printStackTrace();
                }
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

        try (ObjectInputStream objectInputStream = new ObjectInputStream((new FileInputStream(fileName)))) {
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                AnimalExternalizable animal = new AnimalExternalizable();
                animal.readExternal(objectInputStream);
                animals.add(animal);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return new ArrayList<>();
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {
                try {
                    objectOutputStream.writeInt(animal.getAnimalClassification().ordinal());
                    objectOutputStream.writeUTF(animal.getName());
                    objectOutputStream.writeInt(animal.getAge());
                    objectOutputStream.writeInt(animal.getWeight());
                    objectOutputStream.writeBoolean(animal.isPredator());

                    objectOutputStream.writeInt(animal.getAreas().size());
                    for (String area : animal.getAreas()) {
                        objectOutputStream.writeUTF(area);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
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
        List<Animal> animals = new ArrayList<>();

        try (ObjectInputStream objectInputStream = new ObjectInputStream((new FileInputStream(fileName)))) {
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                Animal.AnimalClassification animalClassification = Animal.AnimalClassification.values()[objectInputStream.readInt()];
                String name = objectInputStream.readUTF();
                int age = objectInputStream.readInt();
                int weight = objectInputStream.readInt();
                boolean isPredator = objectInputStream.readBoolean();

                List<String> areas = new ArrayList<>();
                int areasSize = objectInputStream.readInt();
                for (int j = 0; j < size; j++) {
                    areas.add(objectInputStream.readUTF());
                }

                Animal animal = new Animal(animalClassification, name, age, weight, isPredator, areas);

                animals.add(animal);
            }

        } catch(IOException e) {
            e.printStackTrace();
            return new ArrayList<>();
        }

        return animals;
    }
}
