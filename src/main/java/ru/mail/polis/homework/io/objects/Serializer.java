package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.util.ArrayList;
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
    public void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeInt(animals.size());
            for (Animal animal : animals) {
                oos.writeObject(animal);
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            int objectsCount = ois.readInt();
            List<Animal> animals = new ArrayList<>(objectsCount);
            for (int i = 0; i < objectsCount; i++) {
                animals.add((Animal) ois.readObject());
            }
            return animals;
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                oos.writeObject(animal);
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            int objectsCount = ois.readInt();
            List<AnimalWithMethods> animals = new ArrayList<>(objectsCount);
            for (int i = 0; i < objectsCount; i++) {
                animals.add((AnimalWithMethods) ois.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fileName))) {
            oos.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                oos.writeObject(animal);
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
        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fileName))) {
            int objectsCount = ois.readInt();
            List<AnimalExternalizable> animals = new ArrayList<>(objectsCount);
            for (int i = 0; i < objectsCount; i++) {
                animals.add((AnimalExternalizable) ois.readObject());
            }
            return animals;
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
    public void customSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(animals.size());
            for (Animal animal : animals) {
                out.writeUTF(animal.getGroup().toString());
                out.writeUTF(animal.getName());
                out.writeBoolean(animal.isWarmBlooded());

                // Behavior serialization
                Animal.Behavior behavior = animal.getBehavior();
                out.writeBoolean(behavior.canBeTamed());
                out.writeBoolean(behavior.isPredator());
                out.writeUTF(behavior.getMovementType().toString());

                out.writeInt(behavior.enemies().size());
                for (String enemy : behavior.enemies()) {
                    out.writeUTF(enemy);
                }

                out.writeInt(behavior.friends().size());
                for (String friend : behavior.friends()) {
                    out.writeUTF(friend);
                }

                out.writeInt(behavior.favouriteFoodList().size());
                for (String foodItem : behavior.favouriteFoodList()) {
                    out.writeUTF(foodItem);
                }

                out.writeInt(animal.habitatEnvironments().size());
                for (HabitatEnvironment environment : animal.habitatEnvironments()) {
                    out.writeUTF(environment.toString());
                }

                out.writeInt(animal.getAge());
                out.writeInt(animal.getColor());
                out.writeDouble(animal.getWeight());
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
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            int animalsCount = in.readInt();
            List<Animal> animals = new ArrayList<>(animalsCount);
            for (int i = 0; i < animalsCount; i++) {
                AnimalGroup group = AnimalGroup.valueOf(in.readUTF());
                String name = in.readUTF();
                boolean isWarmBlooded = in.readBoolean();
                boolean canBeTamed = in.readBoolean();
                boolean isPredator = in.readBoolean();
                AnimalMovementType movementType = AnimalMovementType.valueOf(in.readUTF());

                Animal.Builder builder = new Animal.Builder(group, name, isWarmBlooded, canBeTamed, isPredator, movementType);

                int enemiesCount = in.readInt();
                for (int j = 0; j < enemiesCount; j++) {
                    builder.withEnemies(in.readUTF());
                }
                int friendsCount = in.readInt();
                for (int j = 0; j < friendsCount; j++) {
                    builder.withFriends(in.readUTF());
                }
                int favouriteFoodItemsCount = in.readInt();
                for (int j = 0; j < favouriteFoodItemsCount; j++) {
                    builder.withFavouriteFood(in.readUTF());
                }
                int habitatEnvironmentsCount = in.readInt();
                for (int j = 0; j < habitatEnvironmentsCount; j++) {
                    builder.withHabitatEnvironments(HabitatEnvironment.valueOf(in.readUTF()));
                }
                builder.withAge(in.readInt());
                builder.withColor(in.readInt());
                builder.withWeight(in.readDouble());

                animals.add(builder.build());
            }

            return animals;
        }
    }
}
