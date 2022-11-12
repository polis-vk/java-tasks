package ru.mail.polis.homework.io.objects;


import java.io.DataOutputStream;
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
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random). Важно, чтобы в списке животных попадались null-ы
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 * <p>
 * Далее этот список надо прочитать из файла.
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 * <p>
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (fileName == null || animals == null) {
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        // Создать директории
        if (Files.notExists(filePath.getParent())) {
            return;
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for (Animal obj : animals) {
                outputStream.writeObject(obj);
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        if (fileName == null) {
            return Collections.emptyList();
        }

        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return Collections.emptyList();
        }

        List<Animal> animalList = new ArrayList<>();
        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (inputStream.available() > 0) {
                animalList.add((Animal) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalList;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        if (fileName == null || animals == null){
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath.getParent())){
            return;
        }

        try(ObjectOutputStream  outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            for(AnimalWithMethods obj : animals){
                outputStream.writeObject(obj);
            }
        }catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и специальных методов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalWithMethods> deserializeWithMethods(String fileName) {
        if (fileName == null) {
            return Collections.emptyList();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath)){
            return Collections.emptyList();
        }

        List<AnimalWithMethods> animalWithMethodsList = new ArrayList<>();
        try(ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath))){
            while(inputStream.available() > 0){
                animalWithMethodsList.add((AnimalWithMethods) inputStream.readObject());
            }
        } catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalWithMethodsList;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        if(fileName == null || animals == null){
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath.getParent())){
            return;
        }

        try(ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(filePath))) {
                for (AnimalExternalizable obj : animals){
                    outputStream.writeObject(obj);
                }
        } catch (IOException exc) {
            exc.printStackTrace();
        }
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную дисериализацию, с помощью специального потока для дисериализации объектов
     * и интерфейса Externalizable
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<AnimalExternalizable> deserializeWithExternalizable(String fileName) {
        if(fileName == null) {
            return Collections.emptyList();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath)){
            return Collections.emptyList();
        }

        List<AnimalExternalizable> animalExternalizableList = new ArrayList<>();
        try(ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(filePath))) {
            while (inputStream.available() > 0) {
                animalExternalizableList.add((AnimalExternalizable) inputStream.readObject());
            }
        }catch (IOException | ClassNotFoundException exc) {
            exc.printStackTrace();
        }
        return animalExternalizableList;
    }

    /**
     * 2 тугрика
     * Реализовать ручную сериализацию, с помощью высокоуровневых потоков. Сами ручками пишем поля,
     * без использования методов writeObject
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {
        if(fileName == null || animals == null){
            throw new IllegalArgumentException();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath.getParent())){
            return;
        }

        try(DataOutputStream outputStream = new DataOutputStream(Files.newOutputStream(filePath))){
            for(Animal obj : animals){
                boolean nullAnimal = obj == null;
//                byte zip = AnimalNurseryWithMethods.byteZip();
            }
        } catch (IOException exc) {
            exc.printStackTrace();
        }

        /// Свой код.

    }

    /**
     * 2 тугрика
     * Реализовать ручную дисериализацию, с помощью высокоуровневых потоков. Сами ручками читаем поля,
     * без использования методов readObject
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {
        if(fileName == null) {
            return Collections.emptyList();
        }

        Path filePath = Paths.get(fileName);
        if(Files.notExists(filePath)){
            return Collections.emptyList();
        }

        /// Свой код!!
        return null;
    }
}
