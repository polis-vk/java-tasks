package ru.mail.polis.homework.io.objects;


import java.util.Collections;
import java.util.List;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;


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
    public void defaultSerialize(List<Animal> animals, String fileName) throws FileNotFoundException {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add(i, (Animal) in.readObject());
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<AnimalWithMethods> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add(i, (AnimalWithMethods) in.readObject());
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        try (ObjectOutput out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<AnimalExternalizable> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animals.add(i, (AnimalExternalizable) in.readObject());
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
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
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            int size = animals.size();
            out.writeInt(size);
            for (Animal animal : animals) {
                out.writeUTF(animal.getName());
                out.writeInt(animal.getAge());
                out.writeDouble(animal.getWeight());
                out.writeUTF(animal.getKindOfAnimal().toString());

                List<String> list = animal.getFood();
                out.writeInt(list.size());
                for (String f : list) {
                    out.writeUTF(f);
                }

                Colour c = animal.getAnimalColor();
                out.writeInt(c.getMainColor());
                out.writeBoolean(c.getStains());
                out.writeBoolean(c.getStripes());
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        if (!Files.exists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
        try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
            int size = in.readInt();
            List<Animal> animals = new ArrayList<Animal>(size);
            for (int i = 0; i < size; i++) {
                String name = in.readUTF();
                int age = in.readInt();
                double weight = in.readDouble();
                boolean gender = in.readBoolean();
                animalType kindOfAnimal = animalType.valueOf(in.readUTF());

                int listSize = in.readInt();
                List<String> food = new ArrayList<String>(listSize);
                for (int j = 0; j < listSize; j++) {
                    food.add(j, in.readUTF());
                }

                int m = in.readInt();
                boolean sa = in.readBoolean();
                boolean st = in.readBoolean();
                Colour animalColor = new Colour(m, sa, st);

                Animal newAnimal = new Animal(age, weight, gender, name, kindOfAnimal, food, animalColor);
                animals.add(i, newAnimal);
            }
            return animals;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }
}
