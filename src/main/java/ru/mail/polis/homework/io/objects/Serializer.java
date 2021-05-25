package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех (2-ух) способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Список лучше копировать, чтобы у вас не было ссылок на одни и те же объекты.
 *
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста/2 теста,
 * за каждый тест 1 балл и 1 бал за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты, как примеры.
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
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            os.writeObject(animals);
        } catch(IOException ex) {
            ex.printStackTrace();
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
        if (fileName == null || fileName.isEmpty()) {
            return Collections.emptyList();
        }

        List<Animal> animals = new ArrayList<>();

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
            animals = (List<Animal>) is.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        return Collections.emptyList();
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {

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
        return Collections.emptyList();
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        try (ObjectOutputStream os = new ObjectOutputStream(new FileOutputStream(fileName))) {
            for (Animal animal : animals) {
                serializeAnimal(animal, os);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void serializeAnimal(Animal animal, ObjectOutputStream os) throws IOException {
        os.writeInt(animal.getHP());
        os.writeUTF(animal.getName());
        os.writeUTF(animal.getType());

        Attack attack = animal.getAttack();
        os.writeUTF(attack.getName());
        os.writeInt(attack.getDamage());

        List<Animal> evolutions = animal.getEvolutions();
        os.writeInt(evolutions.size());
        for (Animal evolution : evolutions) {
            serializeAnimal(evolution, os);
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
        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }

        List<Animal> animals = new ArrayList<>();

        try (ObjectInputStream is = new ObjectInputStream(new FileInputStream(fileName))) {
            while (is.available() > 0) {
                animals.add(parseAnimal(is));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return animals;
    }

    private Animal parseAnimal(ObjectInputStream is) throws IOException {
        int HP = is.readInt();
        String name = is.readUTF();
        List<Type> type = Arrays.stream(is.readUTF().split("/")).map(Type::valueOf).collect(Collectors.toList());

        String attackName = is.readUTF();
        int attackDamage = is.readInt();
        Attack attack = new Attack(attackName, attackDamage);

        List<Animal> evolutions = new ArrayList<>();
        int evolveCount = is.readInt();
        for (int i = 0; i < evolveCount; i++) {
            evolutions.add(parseAnimal(is));
        }

        return new Animal(HP, name, type, attack, evolutions);
    }
}
