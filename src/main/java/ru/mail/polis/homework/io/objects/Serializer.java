package ru.mail.polis.homework.io.objects;


import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random). Важно, чтобы в списке животных попадались null-ы
 * Потом получившийся список записать в файл (необходимо увеличить размер списка, если запись происходит менее 5 секунд).
 * НЕ должно быть ссылок на одни и те же объекты
 *
 * Далее этот список надо прочитать из файла.
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста,
 * за каждый тест 1 балл)  и 3 балла за правильное объяснение результатов
 * Для тестов создайте класс в соответствующем пакете в папке тестов. Используйте другие тесты - как примеры.
 *
 * В конце теста по чтению данных, не забывайте удалять файлы
 */
public class Serializer {

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        if (fileName == null || animals == null) {
            return;
        }

        Path filePath = Paths.get(fileName);

        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (Animal animal : animals) {
                output.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            return null;
        }

        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return null;
        }

        List<Animal> result = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath))) {
            int count = input.readInt();
            for (int i = 0; i < count; i++) {
                result.add((Animal) input.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        if (fileName == null || animals == null) {
            return;
        }

        Path filePath = Paths.get(fileName);

        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                output.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
            return null;
        }

        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return null;
        }

        List<AnimalWithMethods> result = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath))) {
            int count = input.readInt();
            for (int i = 0; i < count; i++) {
                result.add((AnimalWithMethods) input.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        if (fileName == null || animals == null) {
            return;
        }

        Path filePath = Paths.get(fileName);

        try (ObjectOutputStream output = new ObjectOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                output.writeObject(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
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
        if (fileName == null) {
            return null;
        }

        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return null;
        }

        List<AnimalExternalizable> result = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream(Files.newInputStream(filePath))) {
            int count = input.readInt();
            for (int i = 0; i < count; i++) {
                result.add((AnimalExternalizable) input.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }

        return result;
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
        if (fileName == null || animals == null) {
            return;
        }

        Path filePath = Paths.get(fileName);

        try (DataOutputStream output = new DataOutputStream(Files.newOutputStream(filePath))) {
            output.writeInt(animals.size());
            for (Animal animal : animals) {
                AnimalDataByte animalDataByte = new AnimalDataByte(animal);
                output.writeByte(animalDataByte.getByte());

                if (animalDataByte.isAnimalNotNull()) {
                    if (animalDataByte.isNameNotNull()) {
                        output.writeUTF(animal.getName());
                    }
                    output.writeInt(animal.getLegsCount());
                    if (animalDataByte.isTypeNotNull()) {
                        output.writeUTF(animal.getAnimalType().name());
                    }
                    if (animalDataByte.isOrgNotNull()) {
                        Organization org = animal.getOrganization();
                        OrganizationDataByte organizationDataByte = new OrganizationDataByte(org);
                        output.writeByte(organizationDataByte.getByte());
                        if (organizationDataByte.isTitleNotNull()) {
                            output.writeUTF(org.getTitle());
                        }
                        output.writeInt(org.getAnimalsCount());
                    }
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
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
        if (fileName == null) {
            return null;
        }

        Path filePath = Paths.get(fileName);
        if (Files.notExists(filePath)) {
            return null;
        }

        List<Animal> result = new ArrayList<>();
        try (DataInputStream input = new DataInputStream(Files.newInputStream(filePath))) {
            int count = input.readInt();
            for (int i = 0; i < count; i++) {
                AnimalDataByte animalDataByte = new AnimalDataByte(input.readByte());
                Animal animal = null;

                if (animalDataByte.isAnimalNotNull()) {
                    String name = animalDataByte.isNameNotNull() ? input.readUTF() : null;
                    boolean isDomestic = animalDataByte.isDomestic();
                    boolean isHaveClaws = animalDataByte.isHaveClaws();
                    int legsCount = input.readInt();
                    AnimalType animalType = animalDataByte.isTypeNotNull() ? AnimalType.valueOf(input.readUTF()) : null;
                    Organization organization = null;

                    if (animalDataByte.isOrgNotNull()) {
                        OrganizationDataByte organizationDataByte = new OrganizationDataByte(input.readByte());
                        String title = organizationDataByte.isTitleNotNull() ? input.readUTF() : null;
                        boolean isCommecial = organizationDataByte.isCommercial();
                        int animalsCount = input.readInt();
                        organization = new Organization(title, isCommecial, animalsCount);
                    }

                    animal = new Animal(name, isDomestic, isHaveClaws, legsCount, animalType, organization);
                }

                result.add(animal);
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        return result;
    }
}
