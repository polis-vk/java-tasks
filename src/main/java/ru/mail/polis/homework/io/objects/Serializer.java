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
 * <p>
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 * <p>
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста/2 теста,
 * за каждый тест 1 балл и 1 бал за правильное объяснение результатов)
 * Для тестов создайте классы в соответствующем пакете в папке тестов. Используйте другие тесты, как примеры.
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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream((Files.newOutputStream(path)))) {
            outputStream.writeObject(animals);
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
        Path path = Paths.get(fileName);
        try (ObjectInputStream input = new ObjectInputStream((Files.newInputStream(path)))) {
            return (List<Animal>) input.readObject();
        }

    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
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
     *
     * @param animals  Список животных для сериализации
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        Path path = Paths.get(fileName);
        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(path))) {
            outputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                outputStream.writeInt(animal.getAge());
                outputStream.writeUTF(animal.getKind().name());
                outputStream.writeUTF(animal.getName());
                outputStream.writeUTF(animal.getGender().name());
                outputStream.writeUTF(animal.getHabitat().getClimateZone().name());
                outputStream.writeUTF(animal.getHabitat().getMainland().name());
                List<String> famMembersName = animal.getFamilyMembersNames();
                outputStream.writeInt(famMembersName.size());
                for (String memberName : famMembersName) {
                    outputStream.writeUTF(memberName);
                }
            }
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
    public List<Animal> customDeserialize(String fileName) throws IOException {
        Path path = Paths.get(fileName);
        List<Animal> resultList = new ArrayList<>();
        try (ObjectInputStream input = new ObjectInputStream((Files.newInputStream(path)))) {
            int listSize = input.readInt();
            for (int i = 0; i < listSize; i++) {
                Animal animal = new Animal();
                animal.setAge(input.readInt());
                animal.setKind(AnimalKind.valueOf(input.readUTF()));
                animal.setName(input.readUTF());
                animal.setGender(Gender.valueOf(input.readUTF()));
                animal.setHabitat(new Habitat(ClimateZone.valueOf(input.readUTF()), Mainland.valueOf(input.readUTF())));
                int sizeFamMemberList = input.readInt();
                List<String> famMemberList = new ArrayList<>(sizeFamMemberList);
                for (int j = 0; j < sizeFamMemberList; j++) {
                    famMemberList.add(input.readUTF());
                }
                animal.setFamilyMembersNames(famMemberList);
                resultList.add(animal);
            }
        }
        return resultList;
    }
}
