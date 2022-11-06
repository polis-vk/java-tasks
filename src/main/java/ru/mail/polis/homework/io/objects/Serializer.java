package ru.mail.polis.homework.io.objects;


import java.io.*;
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

    private static final byte NULL_VALUE = 0;
    private static final byte NOT_NULL_VALUE = 1;

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (Animal animal : animals) {
                oos.writeObject(animal);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return null;
        }

        List<Animal> animals = new ArrayList<>();
        try (InputStream is = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is)) {
            while (is.available() > 0) {
                animals.add((Animal)ois.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return animals;
    }


    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и специальных методов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithMethods(List<AnimalWithMethods> animals, String fileName) {
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (AnimalWithMethods animal : animals) {
                oos.writeObject(animal);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return null;
        }

        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream is = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is)) {
            while (is.available() > 0) {
                animals.add((AnimalWithMethods)ois.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return animals;
    }

    /**
     * 1 тугрик
     * Реализовать простую ручную сериализацию, с помощью специального потока для сериализации объектов и интерфейса Externalizable
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void serializeWithExternalizable(List<AnimalExternalizable> animals, String fileName) {
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (AnimalExternalizable animal : animals) {
                oos.writeObject(animal);
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return null;
        }

        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream is = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is)) {
            while (is.available() > 0) {
                animals.add((AnimalExternalizable) ois.readObject());
            }
        } catch (IOException | ClassNotFoundException ex) {
            ex.printStackTrace();
        }

        return animals;
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
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return;
        }

        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(file))) {
            for (Animal animal : animals) {
                if (animal == null) {
                    oos.writeByte(NULL_VALUE);
                } else {
                    oos.writeByte(NOT_NULL_VALUE);

                    String say = animal.getSay();
                    if (say == null) {
                        oos.writeByte(NULL_VALUE);
                    } else {
                        oos.writeByte(NOT_NULL_VALUE);
                        oos.writeUTF(say);
                    }

                    oos.writeInt(animal.getLegs());

                    Gender gender = animal.getGender();
                    if (gender == null) {
                        oos.writeByte(NULL_VALUE);
                    } else {
                        oos.writeByte(NOT_NULL_VALUE);
                        oos.writeUTF(gender.toString());
                    }

                    Сlassification classification = animal.getClassification();
                    if (classification == null) {
                        oos.writeByte(NULL_VALUE);
                    } else {
                        oos.writeByte(NOT_NULL_VALUE);
                        String type = classification.getType();
                        if (type == null) {
                            oos.writeByte(NULL_VALUE);
                        } else {
                            oos.writeByte(NOT_NULL_VALUE);
                            oos.writeUTF(type);
                        }

                        String family = classification.getFamily();
                        if (family == null) {
                            oos.writeByte(NULL_VALUE);
                        } else {
                            oos.writeByte(NOT_NULL_VALUE);
                            oos.writeUTF(family);
                        }
                    }
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
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
        Path file = Paths.get(fileName);

        if (!Files.exists(file)) {
            return null;
        }

        List<Animal> animals = new ArrayList<>();
        try(InputStream is = Files.newInputStream(file);
            ObjectInputStream ois = new ObjectInputStream(is)) {
            while (is.available() > 0) {
                if (ois.readByte() == NULL_VALUE) {
                    animals.add(null);
                } else {
                    Animal animal = new Animal();
                    if (ois.readByte() == NULL_VALUE) {
                        animal.setSay(null);
                    } else {
                        animal.setSay(ois.readUTF());
                    }

                    animal.setLegs(ois.readInt());

                    if (ois.readByte() == NULL_VALUE) {
                        animal.setGender(null);
                    } else {
                        animal.setGender(Gender.valueOf(ois.readUTF()));
                    }

                    if (ois.readByte() == NULL_VALUE) {
                        animal.setClassification(null);
                    } else {
                        Сlassification classification = new Сlassification();
                        if (ois.readByte() == NULL_VALUE) {
                            classification.setType(null);
                        } else {
                            classification.setType(ois.readUTF());
                        }

                        if (ois.readByte() == NULL_VALUE) {
                            classification.setFamily(null);
                        } else {
                            classification.setFamily(ois.readUTF());
                        }
                        animal.setClassification(classification);
                    }
                    animals.add(animal);
                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        }

        return animals;
    }
}
