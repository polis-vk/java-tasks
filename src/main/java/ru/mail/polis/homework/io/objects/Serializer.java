package ru.mail.polis.homework.io.objects;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно реализовать методы этого класса и реализовать тестирование 4-ех (2-ух) способов записи.
 * Для тестирования надо создать список из 1000+ разных объектов (заполнять объекты можно рандомом,
 * с помощью класса Random).
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
    private static final byte ANIMAL_NULLABLE_BIT = 0b1;
    private static final byte ANIMAL_POISONOUS_BIT = 0b10;
    private static final byte ANIMAL_WILD_BIT = 0b100;
    private static final byte ANIMAL_ALIAS_NULLABLE_BIT = 0b1000;
    private static final byte ANIMAL_GENDER_NULLABLE_BIT = 0b10000;
    private static final byte ORGANIZATION_NULLABLE_BIT = 0b1;
    private static final byte ORGANIZATION_NAME_NULLABLE_BIT = 0b10;
    private static final byte ORGANIZATION_COUNTRY_NULLABLE_BIT = 0b100;

    private static byte getMetaByte(Animal animal) {
        if (animal == null) {
            return ANIMAL_NULLABLE_BIT;
        }
        byte metaByte = 0;
        if (animal.isPoisonous()) {
            metaByte |= ANIMAL_POISONOUS_BIT;
        }
        if (animal.isWild()) {
            metaByte |= ANIMAL_WILD_BIT;
        }
        if (animal.getAlias() == null) {
            metaByte |= ANIMAL_ALIAS_NULLABLE_BIT;
        }
        if (animal.getGender() == null) {
            metaByte |= ANIMAL_GENDER_NULLABLE_BIT;
        }
        return metaByte;
    }

    private static byte getMetaByte(Organization organization) {
        if (organization == null) {
            return ORGANIZATION_NULLABLE_BIT;
        }
        byte metaByte = 0;
        if (organization.getName() == null) {
            metaByte |= ORGANIZATION_NAME_NULLABLE_BIT;
        }
        if (organization.getCountry() == null) {
            metaByte |= ORGANIZATION_COUNTRY_NULLABLE_BIT;
        }
        return metaByte;
    }

    /**
     * 1 тугрик
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     * @param animals Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(fileNamePath))) {
            for (Animal animal : animals) {
                out.writeObject(animal);
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
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(fileNamePath);
             ObjectInputStream objectIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((Animal) objectIn.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(fileNamePath))) {
            for (AnimalWithMethods animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return null;
        }
        List<AnimalWithMethods> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(fileNamePath);
             ObjectInputStream objectIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((AnimalWithMethods) objectIn.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return;
        }
        try (ObjectOutputStream out = new ObjectOutputStream(Files.newOutputStream(fileNamePath))) {
            for (AnimalExternalizable animal : animals) {
                out.writeObject(animal);
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return null;
        }
        List<AnimalExternalizable> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(fileNamePath);
             ObjectInputStream objectIn = new ObjectInputStream(in)) {
            while (in.available() > 0) {
                animals.add((AnimalExternalizable) objectIn.readObject());
            }
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return;
        }
        try (DataOutputStream out = new DataOutputStream(Files.newOutputStream(fileNamePath))) {
            for (Animal animal : animals) {
                out.writeByte(getMetaByte(animal));
                if (animal == null) {
                    out.flush();
                    continue;
                }
                int legsCount = animal.getLegsCount();
                String alias = animal.getAlias();
                Organization organization = animal.getOrganization();
                Gender gender = animal.getGender();
                out.writeInt(legsCount);
                if (alias != null) {
                    out.writeUTF(alias);
                }
                if (gender != null) {
                    out.writeUTF(gender.name());
                }
                out.writeByte(getMetaByte(organization));
                if (organization == null) {
                    out.flush();
                    continue;
                }
                long licenseNumber = organization.getLicenseNumber();
                String name = organization.getName();
                String country = organization.getCountry();
                out.writeLong(licenseNumber);
                if (name != null) {
                    out.writeUTF(name);
                }
                if (country != null) {
                    out.writeUTF(country);
                }
                out.flush();
            }
        } catch (IOException e) {
            e.printStackTrace();
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
        Path fileNamePath = Paths.get(fileName);
        if (!Files.exists(fileNamePath)) {
            return null;
        }
        List<Animal> animals = new ArrayList<>();
        try (InputStream in = Files.newInputStream(fileNamePath);
             DataInputStream dataIn = new DataInputStream(in)) {
            while (in.available() > 0) {
                byte animalMetaByte = dataIn.readByte();
                if ((animalMetaByte & ANIMAL_NULLABLE_BIT) != 0) {
                    animals.add(null);
                    continue;
                }
                boolean poisonous = (animalMetaByte & ANIMAL_POISONOUS_BIT) != 0;
                boolean wild = (animalMetaByte & ANIMAL_WILD_BIT) != 0;
                int legsCount = dataIn.readInt();
                String alias = null;
                if ((animalMetaByte & ANIMAL_ALIAS_NULLABLE_BIT) == 0) {
                    alias = dataIn.readUTF();
                }
                Gender gender = null;
                if ((animalMetaByte & ANIMAL_GENDER_NULLABLE_BIT) == 0) {
                    gender = Gender.valueOf(dataIn.readUTF());
                }
                Organization organization = null;
                byte organizationMetaByte = dataIn.readByte();
                if ((organizationMetaByte & ORGANIZATION_NULLABLE_BIT) == 0) {
                    long licenceNumber = dataIn.readLong();
                    String name = null;
                    if ((organizationMetaByte & ORGANIZATION_NAME_NULLABLE_BIT) == 0) {
                        name = dataIn.readUTF();
                    }
                    String country = null;
                    if ((organizationMetaByte & ORGANIZATION_COUNTRY_NULLABLE_BIT) == 0) {
                        country = dataIn.readUTF();
                    }
                    organization = new Organization(name, country, licenceNumber);
                }
                animals.add(new Animal(alias, legsCount, poisonous, wild, organization, gender));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return animals;
    }
}
