package ru.mail.polis.homework.io.objects;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

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
    public static void defaultSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                objectOutputStream.writeObject(animal);
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
    public static List<Animal> defaultDeserialize(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<Animal> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                animals.add((Animal) objectInputStream.readObject());
            }
            objectInputStream.close();
            return animals;
        }
    }


    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalWithMethods animal : animals) {
                objectOutputStream.writeObject(animal);
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
    public static List<AnimalWithMethods> deserializeWithMethods(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<AnimalWithMethods> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                animals.add((AnimalWithMethods) objectInputStream.readObject());
            }
            objectInputStream.close();
            return animals;
        }
    }

    /**
     * 1 балл
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public static void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            for (AnimalExternalizable animal : animals) {
                objectOutputStream.writeObject(animal);
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
    public static List<AnimalExternalizable> deserializeWithExternalizable(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<AnimalExternalizable> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                animals.add((AnimalExternalizable) objectInputStream.readObject());
            }
            objectInputStream.close();
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

    public static void customSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            HashSet<Animal> hashSet = new HashSet<>();
            for (Animal animal : animals) {
                objectOutputStream.writeUTF(animal.getName());
                objectOutputStream.writeUTF(animal.getDiet().name());

                objectOutputStream.writeBoolean(animal.getGenotype().isImmutable());
                objectOutputStream.writeInt(animal.getGenotype().getChromosomes().size());
                ;
                for (Animal.Chromosome chromosome : animal.getGenotype().getChromosomes()) {
                    int[] genes = chromosome.getGenes();
                    objectOutputStream.writeBoolean(chromosome.isImmutable());
                    objectOutputStream.writeInt(genes.length);
                    for (int i = 0; i < genes.length; i++) {
                        objectOutputStream.writeInt(genes[i]);
                    }
                }

                objectOutputStream.writeInt(animal.getSpeciesId());
                objectOutputStream.writeInt(animal.getScaredOf().size());
                List<Integer> list = animal.getScaredOf();
                for (Integer val : list) {
                    objectOutputStream.writeInt(val);
                }
                objectOutputStream.writeBoolean(animal.isSingleCell());
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
    public static List<Animal> customDeserialize(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<Animal> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                String name = objectInputStream.readUTF();
                Animal.Diet diet = Animal.Diet.valueOf(objectInputStream.readUTF());

                List<Animal.Chromosome> chromosomes = new ArrayList<>();
                boolean isImmutable = objectInputStream.readBoolean();
                int size = objectInputStream.readInt();
                for (int j = 0; j < size; j++) {
                    boolean isImmutableChromosome = objectInputStream.readBoolean();
                    int chromosomeSize = objectInputStream.readInt();
                    int[] genes = new int[chromosomeSize];
                    for (int k = 0; k < chromosomeSize; k++) {
                        genes[j] = objectInputStream.readInt();
                    }
                    chromosomes.add(new Animal.Chromosome(genes, isImmutableChromosome));
                }
                Animal.Genotype genotype = new Animal.Genotype(chromosomes, isImmutable);

                int speciesId = objectInputStream.readInt();
                size = objectInputStream.readInt();
                List<Integer> scared = new ArrayList<>(size);
                for (int j = 0; j < size; j++) {
                    scared.add(objectInputStream.readInt());
                }
                boolean singleCell = objectInputStream.readBoolean();
                animals.add(new Animal(name, diet, genotype, speciesId, scared, singleCell));
            }
            objectInputStream.close();
            return animals;
        }
    }

    public static void customDeepSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            for (Animal animal : animals) {
                animal.writeObjectCustom(objectOutputStream);
            }
        }
    }

    public static List<Animal> customDeepDeserialize(String fileName) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<Animal> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                animals.add(Animal.readObjectCustom(objectInputStream));
            }
            objectInputStream.close();
            return animals;
        }
    }

    public static final int NEW_OBJECT = 0;
    public static final int REFERENCE = 1;

    public static class HashTable<T> {
        private final List<List<T>> buckets;
        private final List<List<Integer>> bucketsReferences;
        private final int size;

        public HashTable(int size) {
            buckets = new ArrayList<>(size);
            bucketsReferences = new ArrayList<>(size);
            for (int i = 0; i < size; i++) {
                buckets.add(new ArrayList<>());
                bucketsReferences.add(new ArrayList<>());
            }
            this.size = size;
        }

        public int getReference(T obj) {
            List<T> bucket = buckets.get(getHash(obj));
            List<Integer> reference = bucketsReferences.get(getHash(obj));
            for (int i = 0; i < bucket.size(); i++) {
                if (bucket.get(i) == obj) {
                    return reference.get(i);
                }
            }
            return -1;
        }

        public void add(T obj, int index) {
            buckets.get(getHash(obj)).add(obj);
            bucketsReferences.get(getHash(obj)).add(index);
        }

        private int getHash(T obj) {
            return Math.abs(obj.hashCode() % size);
        }
    }

    public static void customDeepStableSerialize(List<Animal> animals, String fileName) throws IOException {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(fileName))) {
            objectOutputStream.writeInt(animals.size());
            HashTable<Animal> hashTable = new HashTable<>(animals.size() / 10);
            for (int i = 0; i < animals.size(); i++) {
                int indexOfReference = hashTable.getReference(animals.get(i));
                if (indexOfReference != -1) {
                    objectOutputStream.writeInt(REFERENCE);
                    objectOutputStream.writeInt(indexOfReference);
                    continue;
                }
                objectOutputStream.writeInt(NEW_OBJECT);
                hashTable.add(animals.get(i), i);
                animals.get(i).writeObjectCustom(objectOutputStream);
            }
        }
    }

    public static List<Animal> customDeepStableDeserialize(String fileName) throws IOException {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(fileName))) {
            int count = objectInputStream.readInt();
            ArrayList<Animal> animals = new ArrayList<>(count);
            for (int i = 0; i < count; i++) {
                int flag = objectInputStream.readInt();
                if (flag == NEW_OBJECT) {
                    animals.add(Animal.readObjectCustom(objectInputStream));
                } else {
                    animals.add(animals.get(objectInputStream.readInt()));
                }
            }
            objectInputStream.close();
            return animals;
        }
    }
}
