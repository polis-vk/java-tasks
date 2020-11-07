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
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. спользуйте существующие тесты, как примеры.
 * <p>
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(animals);
        } catch (IOException ignored) {
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            animals = (List<Animal>) ois.readObject();
        } catch (ClassNotFoundException | IOException ignored) {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeObject(animals);
        } catch (IOException ignored) {
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            animals = (List<AnimalWithMethods>) ois.readObject();
        } catch (ClassNotFoundException | IOException ignored) {
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
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            try {
                oos.writeObject(animals);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (IOException ignored) {
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            animals = (List<AnimalExternalizable>) ois.readObject();
        } catch (ClassNotFoundException | IOException ignored) {
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
        try (ObjectOutputStream dos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            dos.writeInt(animals.size());
            for (Animal animal : animals) {
                try {
                    dos.writeInt(animal.getAge());
                    dos.writeUTF(animal.getName());
                    dos.writeUTF(animal.getHabitat().name());
                    dos.writeInt(animal.getFood().size());
                    for (String f : animal.getFood()) {
                        dos.writeUTF(f);
                    }
                    dos.writeBoolean(animal.isSexIsMale());
                    dos.writeDouble(animal.getHeight());
                    dos.writeBoolean(animal.getHeart().isAlive());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (IOException ignored) {
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
        List<Animal> animals = Collections.emptyList();
        try (ObjectInputStream dis = new ObjectInputStream(new FileInputStream(fileName))) {
            int sizeAnimals = dis.readInt();
            animals = new ArrayList<>(sizeAnimals);
            for (int i = 0; i < sizeAnimals; i++) {
                Animal.Builder animalBuilder = new Animal().createBuilder();
                animalBuilder.setAge(dis.readInt());
                animalBuilder.setName(dis.readUTF());
                animalBuilder.setHabitat(Animal.Habitat.valueOf(dis.readUTF()));

                int sizeFood = dis.readInt();
                List<String> food = new ArrayList<>(sizeFood);
                for (int j = 0; j < sizeFood; j++) {
                    food.add(dis.readUTF());
                }

                animalBuilder.setFood(food);
                animalBuilder.setSex(dis.readBoolean());
                animalBuilder.setHeight(dis.readDouble());
                animalBuilder.setHeart(new Heart(dis.readBoolean()));

                animals.add(animalBuilder.build());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }

}
