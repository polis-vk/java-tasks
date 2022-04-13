package ru.mail.polis.homework.io.blocking;


import java.io.EOFException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и написать сравнительное тестирование 2-ух способов записи и чтения.
 * Для тестирования надо создать список из 10 разных структур (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Список и объекты внутри лучше копировать, чтобы у вас не было ссылок на одни и те же объекты.
 *
 * Далее этот список надо прочитать из файла.
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 2 теста,
 * за правильную генерацию данных 1 тугрик, 1 тугрик за каждый тест и 1 тугрик за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class StructureSerializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param structures Список структур для сериализации
     * @param fileName файл в который "пишем" структуры
     */
    public static void defaultSerialize(List<Structure> structures, String fileName) throws IOException {
        try (ObjectOutputStream outputStream =
                     new ObjectOutputStream(Files.newOutputStream(Paths.get(fileName)))) {

            for (Structure structure : structures) {
                outputStream.writeObject(structure);
            }
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public static List<Structure> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        List<Structure> structures = new ArrayList<>();

        try (ObjectInputStream inputStream =
                     new ObjectInputStream(Files.newInputStream(Paths.get(fileName)))) {
            while (true) {
                try {
                    structures.add((Structure) inputStream.readObject());
                } catch (EOFException e) {
                    break;
                }
            }
        }

        return structures;
    }


    /**
     * 1 тугрик
     * Реализовать сериализацию, с помощью StructureOutputStream
     * @param structures Список структур для сериализации
     * @param fileName файл в который "пишем" структуры
     */
    public static void serialize(List<Structure> structures, String fileName) throws IOException {
        try (StructureOutputStream outputStream = new StructureOutputStream(Paths.get(fileName).toFile())) {
            outputStream.write(structures.toArray(Structure[]::new));
        }
    }

    /**
     * 1 тугрик
     * Реализовать дисериализацию, с помощью StructureInputStream
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public static List<Structure> deserialize(String fileName) throws IOException {
        List<Structure> structures = new ArrayList<>();

        try (StructureInputStream structureInputStream = new StructureInputStream(Paths.get(fileName).toFile())) {
            while (structureInputStream.available() != 0) {
                structures = Arrays.asList(structureInputStream.readStructures());
            }
        }

        return structures;
    }
}
