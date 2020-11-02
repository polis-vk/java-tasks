package ru.mail.polis.homework.io.objects;


import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
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

  private final int numberToWrite = 100;

  /**
   * 1 балл
   * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
   *
   * @param animals  Список животных для сериализации
   * @param fileName файл в который "пишем" животных
   */
  public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
    Path path = Paths.get(fileName);

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        outputStream.writeObject(animals);
      }
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
    List<Animal> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        animalList = (ArrayList<Animal>) inputStream.readObject();
      }

      return animalList;
    }
  }

  /**
   * 1 балл
   * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
   *
   * @param animals  Список животных для сериализации
   * @param fileName файл в который "пишем" животных
   */
  public void serializeWithMethods(List<? super AnimalWithMethods> animals, String fileName) throws IOException {

    Path path = Paths.get(fileName);

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        outputStream.writeObject(animals);
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
  public List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
    List<AnimalWithMethods> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        animalList = (ArrayList<AnimalWithMethods>) inputStream.readObject();
      }
      return animalList;
    }
  }

  /**
   * 1 балл
   * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
   *
   * @param animals  Список животных для сериализации
   * @param fileName файл в который "пишем" животных
   */
  public void serializeWithExternalizable(List<? super AnimalExternalizable> animals, String fileName) throws IOException {
    Path path = Paths.get(fileName);

    try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        outputStream.writeObject(animals);
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
  public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
    List<AnimalExternalizable> animalList = new ArrayList<>();
    Path path = Paths.get(fileName);

    try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(path))) {

      for (int i = 0; i < numberToWrite; i++) {
        animalList = (ArrayList<AnimalExternalizable>) inputStream.readObject();
      }
      return animalList;
    }
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
    return Collections.emptyList();
  }

  private void serialize(List<Animal> animalList, Path path) {
  }

  private List<? extends Animal> deserialize() {
    return Collections.emptyList();
  }
}
