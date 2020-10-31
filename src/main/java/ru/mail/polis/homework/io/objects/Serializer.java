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
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {

      animals.forEach(a -> {
        try {
          objectOutputStream.writeObject(a);
        } catch (Exception ignored) {
        }
      });

      objectOutputStream.flush();
    } catch (Exception ignored) {
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

    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
      while (objectInputStream.available() != 0) {
        animals.add((Animal) objectInputStream.readObject());
      }
    } catch (Exception ignored) {
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
    try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {

      animals.forEach(a -> {
        try {
          objectOutputStream.writeObject(a);
        } catch (Exception ignored) {

        }
      });
    } catch (Exception ignored) {
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

    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
      while (objectInputStream.available() != 0) {
        animals.add((AnimalWithMethods) objectInputStream.readObject());
      }
    } catch (Exception ignored) {
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

      animals.forEach(a -> {
        try {
          a.writeExternal(objectOutputStream);
        } catch (Exception ignored) {

        }
      });
    } catch (Exception ignored) {
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

    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {

      while (objectInputStream.available() != 0) {
        AnimalExternalizable animal = new AnimalExternalizable();

        animal.readExternal(objectInputStream);
        animals.add(animal);
      }
    } catch (Exception ignored) {
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

      animals.forEach(a -> {
        try {
          objectOutputStream.writeInt(a.age);
          objectOutputStream.writeUTF(a.name);
          objectOutputStream.writeObject(a.demon);
          objectOutputStream.writeObject(a.friendNames);
          objectOutputStream.writeObject(a.diet);
          objectOutputStream.writeBoolean(a.isAlive);
        } catch (IOException e) {
          e.printStackTrace();
        }
      });
    } catch (Exception ignored) {
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

    try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {

      while (objectInputStream.available() != 0) {
        Animal animal = new Animal(
            objectInputStream.readInt(),
            objectInputStream.readUTF(),
            (InnerDemon) objectInputStream.readObject(),
            (List<String>) objectInputStream.readObject(),
            (Animal.Diet) objectInputStream.readObject(),
            objectInputStream.readBoolean()
        );

        animals.add(animal);
      }
    } catch (Exception ignored) {
    }

    return animals;
  }
}
