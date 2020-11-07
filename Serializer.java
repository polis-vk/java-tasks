package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
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
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeObject(animals);
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
    public List<Animal> defaultDeserialize(String fileName){
        List<Animal> result = null;
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            result = (List<Animal>)stream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeInt(animals.size());
            for (AnimalWithMethods currentAnimal: animals) {
                stream.writeObject(currentAnimal);
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
        List<AnimalWithMethods> result = new ArrayList<AnimalWithMethods>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = stream.readInt();
            for (int i = 0; i < size; i++) {
                result.add((AnimalWithMethods) stream.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeInt(animals.size());
            for (AnimalExternalizable currentAnimal: animals) {
                currentAnimal.writeExternal(stream);
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
        List<AnimalExternalizable> result = new ArrayList<AnimalExternalizable>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = stream.readInt();
            for (int i = 0; i < size; i++) {
                AnimalExternalizable current = new AnimalExternalizable();
                current.readExternal(stream);
                result.add(current);
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return result;
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
        try(ObjectOutputStream stream = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            stream.writeInt(animals.size());
            for (Animal currentAnimal: animals) {
                stream.writeUTF(currentAnimal.getName());
                stream.writeBoolean(currentAnimal.isMale());
                stream.writeInt(currentAnimal.getNutritionType().ordinal());
                stream.writeInt(currentAnimal.getAge());
                List<String> childNames = currentAnimal.getChildNames();
                stream.writeInt(childNames.size());
                for (String currentChild: childNames) {
                    stream.writeUTF(currentChild);
                }
                Heart currentHeart = currentAnimal.getHeart();
                stream.writeDouble(currentHeart.getWeight());
                stream.writeDouble(currentHeart.getPower());
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
    public List<Animal> customDeserialize(String fileName)  {
        List<Animal> result = new ArrayList<Animal>();
        try(ObjectInputStream stream = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            int size = stream.readInt();
            for (int i = 0; i < size; i++) {
                String name = stream.readUTF();
                boolean isMale = stream.readBoolean();
                Animal.NutritionType nutritionType = Animal.NutritionType.values()[stream.readInt()];
                int age = stream.readInt();
                int childNamesSize = stream.readInt();
                List childNames = new ArrayList<String>();
                for (int j = 0; j < childNamesSize; j++) {
                    childNames.add(stream.readUTF());
                }
                Heart heart = new Heart(stream.readDouble(), stream.readDouble());
                result.add(new Animal(name, isMale, nutritionType, age, childNames, heart));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }

}
