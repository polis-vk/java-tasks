package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
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
    Path path = Paths.get(fileName);

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
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
    List<Animal> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
      int deserializeNumber = inputStream.readInt();
      for (int i = 0; i < deserializeNumber; i++) {
        animalList.add((Animal) inputStream.readObject());

      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return animalList;

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

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
      outputStream.writeInt(animals.size());
      for (AnimalWithMethods animalWithMethods : animals) {
        outputStream.writeObject(animalWithMethods);
      }
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
    List<AnimalWithMethods> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
      int deserializeNumber = inputStream.readInt();
      for (int i = 0; i < deserializeNumber; i++) {
        animalList.add((AnimalWithMethods) inputStream.readObject());
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }

    return animalList;

  }

  /**
   * 1 балл
   * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
   *
   * @param animals  Список животных для сериализации
   * @param fileName файл в который "пишем" животных
   */
  public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
    Path path = Paths.get(fileName);

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
      outputStream.writeInt(animals.size());
      for (AnimalExternalizable animalExternalizable : animals) {
        outputStream.writeObject(animalExternalizable);
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
    List<AnimalExternalizable> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
      int deserializeNumber = inputStream.readInt();
      for (int i = 0; i < deserializeNumber; i++) {
        animalList.add((AnimalExternalizable) inputStream.readObject());
      }
    } catch (IOException | ClassNotFoundException e) {
      e.printStackTrace();
    }
    return animalList;
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

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
      outputStream.writeInt(animals.size());
      for (Animal animal : animals) {
        outputStream.writeInt(animal.getBrain().getSize());

        outputStream.writeInt(animal.getListName().size());
        for (String name : animal.getListName()) {
          outputStream.writeUTF(name);
        }

        outputStream.writeInt(animal.getWeight());
        outputStream.writeUTF(animal.getName());
        outputStream.writeUTF(animal.getHabitation().toString());
        outputStream.writeLong(animal.getDistanceTraveled());
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
    List<Animal> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {
      int deserializeNumber = inputStream.readInt();
      for (int i = 0; i < deserializeNumber; i++) {
        Brain brain = new Brain(inputStream.readInt());

        int nameListSize = inputStream.readInt();
        List<String> nameList = Arrays.asList(new String[nameListSize]);
        for (int j = 0; j < nameListSize; j++) {
          nameList.set(j, inputStream.readUTF());
        }

        int weight = inputStream.readInt();
        String name = inputStream.readUTF();
        Habitation habitation = Habitation.valueOf(inputStream.readUTF());
        long distanceTraveled = inputStream.readLong();
        animalList.add(new Animal(brain, nameList, weight, name, habitation, distanceTraveled));
      }
    } catch (IOException e) {
      e.printStackTrace();
    }

    return animalList;
  }
}
