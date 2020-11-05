package ru.mail.polis.homework.io.objects;

import java.util.List;
import java.util.ArrayList;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех способов записи.
 * Для тестирования надо создать список из 10 разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random или руками прописать разные значения переменных).
 * Потом получившийся список записать в один и тот же файл 10 раз (100 раз и более, если у вас это происходит очень быстро).
 * Далее этот список надо прочитать из файла.
 * Записывать в существующий файл можно с помощью специального конструктора для файловых потоков
 *
 * Результатом теста должно быть следующее: размер файла, время записи и время чтения.
 * Время считать через System.currentTimeMillis().
 * В итоговом пулРеквесте должна быть информация об этих значениях для каждого теста. (всего 4 теста, за каждый тест 1 балл)
 * Для тестов создайте классы в соотвествующем пакете в папке тестов. Используйте существующие тесты, как примеры.
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
            List<Animal> animals = new ArrayList<>();
            for (int i = 0; i < objectsCount; i++) {
                animals.add((Animal) ois.readObject());
            }
            return animals;
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
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
            List<AnimalWithMethods> animals = new ArrayList<>();
            for (int i = 0; i < objectsCount; i++) {
                animals.add((AnimalWithMethods) ois.readObject());
            }
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
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
            List<AnimalExternalizable> animals = new ArrayList<>();
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
                out.writeUTF(animal.getName());
                out.writeInt(animal.getAge());
                List<Color> colors = animal.getColors();
                out.writeInt(colors.size());
                for (Color color : colors) {
                    out.writeInt(color.ordinal());
                }
                out.writeBoolean(animal.getIsTame());
                out.writeInt(animal.getEatingStrategy().ordinal());

                Animal.Taxonomy taxonomy = animal.getTaxonomy();
                out.writeUTF(taxonomy.getDomain());
                out.writeUTF(taxonomy.getKingdom());
                out.writeUTF(taxonomy.getPhylum());
                out.writeUTF(taxonomy.getClassT());
                out.writeUTF(taxonomy.getOrder());
                out.writeUTF(taxonomy.getFamily());
                out.writeUTF(taxonomy.getGenus());
                out.writeUTF(taxonomy.getSpecies());
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
            List<Animal> animals = new ArrayList<>();
            int animalsCount = in.readInt();
            for (int i = 0; i < animalsCount; i++) {
                String name = in.readUTF();
                int age = in.readInt();
                int colorsCount = in.readInt();
                Color[] colors = new Color[colorsCount];
                for (int j = 0; j < colorsCount; j++) {
                    colors[j] = Color.values()[in.readInt()];
                }
                boolean isTame = in.readBoolean();
                EatingStrategy eatingStrategy = EatingStrategy.values()[in.readInt()];

                Animal.Builder builder = new Animal.Builder(name, age, isTame, eatingStrategy, colors);

                builder.setDomain(in.readUTF())
                .setKingdom(in.readUTF())
                .setPhylum(in.readUTF())
                .setClassT(in.readUTF())
                .setOrder(in.readUTF())
                .setFamily(in.readUTF())
                .setGenus(in.readUTF())
                .setSpecies(in.readUTF());      

                animals.add(builder.build());
            }

            return animals;
        }
    }
}
