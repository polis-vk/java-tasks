package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.List;
import java.nio.file.Path;
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
  public void defaultSerialize(List<Animal> animals, String fileName) {
    Path path = Paths.get(fileName);
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
      for (Animal a : animals) {
        objectOutputStream.writeObject(a);
      }
    } catch (Exception e) {
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
    List<Animal> animals = new ArrayList<>();

    try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
      while (true) {
        animals.add((Animal) objectInputStream.readObject());
      }
    } catch (Exception e) {
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
      for (AnimalWithMethods animal : animals) {
        objectOutputStream.writeObject(animal);
      }
    } catch (Exception e) {
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
    List<AnimalWithMethods> animals = new ArrayList<>();

    try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {

      while (true) {
        animals.add((AnimalWithMethods) objectInputStream.readObject());
      }

    } catch (Exception e) {
      e.printStackTrace();
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
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
      for (AnimalExternalizable animal : animals) {
        animal.writeExternal(objectOutputStream);
      }
    } catch (Exception e) {
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
    List<AnimalExternalizable> animals = new ArrayList<>();

    try (ObjectInputStream objectInputStream = new ObjectInputStream(Files.newInputStream(path))) {
      while (true) {
        AnimalExternalizable animal = new AnimalExternalizable();

        animal.readExternal(objectInputStream);

        animals.add(animal);
      }
    } catch (Exception e) {
      e.printStackTrace();
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
    Path path = Paths.get(fileName);
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(Files.newOutputStream(path))) {

      for (Animal a : animals) {
        objectOutputStream.writeInt(a.getAge());
        objectOutputStream.writeUTF(a.getName());
        objectOutputStream.writeBoolean(a.getDemon().isActive());

        int size = a.getFriendNames().size();
        objectOutputStream.writeInt(size);

        for (int i = 0; i < size; i++) {
          objectOutputStream.writeUTF(a.getFriendNames().get(i));
        }

        objectOutputStream.writeUTF(a.getDiet().name());
        objectOutputStream.writeBoolean(a.isAlive());
      }
    } catch (Exception e) {
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

      while (true) {
        Animal animal = new Animal(
            objectInputStream.readInt(),
            objectInputStream.readUTF(),
            new SerializableInnerDemon(objectInputStream.readBoolean()),
            deserializeStringList(objectInputStream.readInt(), objectInputStream),
            Animal.Diet.valueOf(objectInputStream.readUTF()),
            objectInputStream.readBoolean()
        );

        animals.add(animal);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }

    return animals;
  }

  private List<String> deserializeStringList(int amount, ObjectInputStream objectInputStream) throws IOException {
    List<String> strings = new ArrayList<>();

    for (int i = 0; i < amount; i++) {
      strings.add(objectInputStream.readUTF());
    }

    return strings;
  }
}
