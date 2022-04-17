package ru.mail.polis.homework.io.blocking;


import java.io.File;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.io.IOException;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и написать сравнительное тестирование 2-ух способов записи и чтения.
 * Для тестирования надо создать список из 10 разных структур (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Список и объекты внутри лучше копировать, чтобы у вас не было ссылок на одни и те же объекты.
 * <p>
 * Далее этот список надо прочитать из файла.
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 2 теста,
 * за правильную генерацию данных 1 тугрик, 1 тугрик за каждый тест и 1 тугрик за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class StructureSerializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param structures Список структур для сериализации
     * @param fileName   файл в который "пишем" структуры
     */
    public void defaultSerialize(List<Structure> structures, String fileName) {
        Path fileTo = Paths.get(fileName);
        if (Files.notExists(fileTo)) {
            return;
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(fileTo))) {
            for (Structure structure : structures) {
                outputStream.writeObject(structure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public List<Structure> defaultDeserialize(String fileName) {
        Path fileFrom = Paths.get(fileName);
        if (Files.notExists(fileFrom)) {
            return null;
        }

        List<Structure> structures = new ArrayList<>();
        try (InputStream fileInputStream = Files.newInputStream(fileFrom)) {
            try (ObjectInputStream inputStream = new ObjectInputStream(fileInputStream)) {
                while (fileInputStream.available() != 0) {
                    structures.add((Structure) inputStream.readObject());
                }
            }
        } catch (ClassNotFoundException | IOException e) {
            e.printStackTrace();
        }
        return structures;
    }


    /**
     * 1 тугрик
     * Реализовать сериализацию, с помощью StructureOutputStream
     *
     * @param structures Список структур для сериализации
     * @param fileName   файл в который "пишем" структуры
     */
    public void serialize(List<Structure> structures, String fileName) {
        File fileTo = new File(fileName);
        if (!fileTo.exists()) {
            return;
        }

        try (StructureOutputStream outputStream = new StructureOutputStream(fileTo)) {
            for (Structure structure : structures) {
                outputStream.write(structure);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать дисериализацию, с помощью StructureInputStream
     *
     * @param fileName файл из которого "читаем" животных
     * @return список структур
     */
    public List<Structure> deserialize(String fileName) {
        File fileFrom = new File(fileName);
        if (!fileFrom.exists()) {
            return null;
        }

        List<Structure> structures = new ArrayList<>();
        try (StructureInputStream inputStream = new StructureInputStream(fileFrom)) {
            while (inputStream.available() != 0) {
                structures.add(inputStream.readStructure());
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return structures;
    }


}
