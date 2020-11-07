package ru.mail.polis.homework.io.objects;


import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
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
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {
    
    private <T> void serialize(List<T> animals, String fileName) {
        try (ObjectOutput objectOutput = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutput.writeInt(animals.size());
            for (T animal : animals) {
                objectOutput.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private <T> List<T> deserialize(String fileName) {
        if (Files.exists(Paths.get(fileName))) {
            try (ObjectInput objectInput = new ObjectInputStream(new FileInputStream(fileName))) {
                int size = objectInput.readInt();
                List<T> animals = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    animals.add((T) objectInput.readObject());
                }
                return animals;
            } catch (IOException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    
    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        serialize(animals, fileName);
    }
    
    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        return deserialize(fileName);
    }
    
    
    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        serialize(animals, fileName);
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
        return deserialize(fileName);
    }
    
    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        serialize(animals, fileName);
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
        return deserialize(fileName);
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
        try (DataOutputStream dos = new DataOutputStream(new BufferedOutputStream(new FileOutputStream(fileName)))) {
            dos.writeInt(animals.size());
            for (Animal animal : animals) {
                dos.writeInt(animal.getAge());
                dos.writeBoolean(animal.isFriendly());
                dos.writeUTF(animal.getName());
                List<Integer> weightByLastDays = animal.getWeightByLastDays();
                dos.writeInt(weightByLastDays.size());
                for (Integer weight : weightByLastDays) {
                    dos.writeInt(weight);
                }
                Kind kind = animal.getKind();
                dos.writeUTF(kind.getName());
                dos.writeLong(kind.getPopulationSize());
                dos.writeUTF(animal.getOwner().name());
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
        if (Files.exists(Paths.get(fileName))) {
            try (DataInputStream dis = new DataInputStream(new BufferedInputStream(new FileInputStream(fileName)))) {
                int animalsSize = dis.readInt();
                List<Animal> animals = new ArrayList<>(animalsSize);
                
                for (int i = 0; i < animalsSize; i++) {
                    int age = dis.readInt();
                    boolean isFriendly = dis.readBoolean();
                    String name = dis.readUTF();
                    int daysListSize = dis.readInt();
                    List<Integer> weightByLastDays = new ArrayList<>(daysListSize);
                    for (int j = 0; j < daysListSize; j++) {
                        weightByLastDays.add(dis.readInt());
                    }
                    Kind kind = new Kind(dis.readUTF(), dis.readLong());
                    Owner owner = Owner.valueOf(dis.readUTF());
                    
                    animals.add(new Animal(age, isFriendly, name, weightByLastDays, kind, owner));
                }
                
                return animals;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return null;
    }
}
