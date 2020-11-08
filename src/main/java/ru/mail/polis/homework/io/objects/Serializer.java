package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
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
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for(Animal animal : animals){
                objectOutputStream.writeObject(animal);}
        }catch (IOException e) {
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
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            List<Animal> animals = new ArrayList<>();
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                animals.add((Animal) objectInputStream.readObject());
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
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try(ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for(AnimalWithMethods animal : animals){
                objectOutputStream.writeObject(animal);}
        }catch (IOException e) {
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
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            List<AnimalWithMethods> animals = new ArrayList<>();
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
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
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
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
        try(ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            List<AnimalExternalizable> animals = new ArrayList<>();
            int size = objectInputStream.readInt();

            for (int i = 0; i < size; i++) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
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
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeUTF(animal.getType().toString());
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeInt(animal.getAge());
                objectOutputStream.writeBoolean(animal.getSex());
                List<Date> dateOfVaccination = animal.getDateOfVaccination();
                objectOutputStream.writeInt(dateOfVaccination.size());
                for (Date date : dateOfVaccination) {
                    objectOutputStream.writeUTF(date.toString());
                }
                objectOutputStream.writeUTF(animal.getfplan().getFtype().toString());
                objectOutputStream.writeDouble(animal.getfplan().getWeight());
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
        if (!Files.exists(Paths.get(fileName))) {
            return Collections.emptyList();
        }
        try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = objectInputStream.readInt();
            ArrayList<Animal> animals = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                animalType type = animalType.valueOf(objectInputStream.readUTF());
                String name = objectInputStream.readUTF();
                int age = objectInputStream.readInt();
                boolean sex = objectInputStream.readBoolean();
                int sizeList = objectInputStream.readInt();
                ArrayList<Date> dateOfVaccination = new ArrayList<Date>(size);
                for (int j = 0; j < sizeList; j++) {
                    try {
                        Date date1 = new SimpleDateFormat("dd/MM/yyyy").parse(objectInputStream.readUTF());
                        dateOfVaccination.add(date1);
                    } catch (ParseException e) {
                        e.printStackTrace();
                    }
                }
                String f = objectInputStream.readUTF();
                double w = objectInputStream.readDouble();
                Food fplan = new Food(w, Food.foodType.valueOf(f));
                Animal animal = new Animal(type, name, age, sex, dateOfVaccination, fplan);
                animals.add(animal);
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }}
