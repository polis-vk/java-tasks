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
    public static void defaultSerialize(List<Animal> animals, String fileName) {
        try(ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            out.writeObject(animals);
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
    public static List<Animal> defaultDeserialize(String fileName) {
        try(ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            return (List<Animal>) in.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public static void customSerialize(List<Animal> animals, String fileName) {
        try(ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {
            out.writeInt(animals.size());
            for(Animal animal : animals)
            {
                out.writeUTF(animal.getLivingPlace().getCountry());
                out.writeUTF(animal.getLivingPlace().getName());
                out.writeUTF(animal.getName());
                out.writeByte(animal.getAge());
                out.writeUTF(animal.getColour().toString());
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
    public static List<Animal> customDeserialize(String fileName) {
        try(ObjectInputStream in = new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))){
            int count = in.readInt();
            List<Animal> animals = new ArrayList<>(count);
            for(int i = 0; i < count; i++)
            {

                Animal animal = new Animal();
                Zoo zoo = new Zoo();
                zoo.setCountry(in.readUTF());
                zoo.setName(in.readUTF());
                animal.setLivingPlace(zoo);
                animal.setName(in.readUTF());
                animal.setAge(in.readByte());
                animal.setColour(Animal.Colour.valueOf(in.readUTF()));
                animals.add(animal);
            }
            return animals;
        } catch (IOException e) {
            e.printStackTrace();
            return Collections.emptyList();
        }
    }
}
