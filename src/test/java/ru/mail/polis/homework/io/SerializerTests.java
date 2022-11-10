package ru.mail.polis.homework.io;

import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertEquals;

public class SerializerTests {

    private final int ANIMAL_COUNT = 650000;

    @Test
    public void autoSerializeTest() throws IOException {
        List<Animal> inputList = randomAnimalList();
        String path = Paths.get("src", "test", "resources", "serialize", "auto-serialize").toAbsolutePath().toString();
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        Serializer serializer = new Serializer();

        long startWriteTime = System.currentTimeMillis();
        serializer.defaultSerialize(inputList, path);
        long endWriteTime = System.currentTimeMillis();
        long startReadTime = System.currentTimeMillis();
        List<Animal> deserializeList = serializer.defaultDeserialize(path);
        long endReadTime = System.currentTimeMillis();

        assertEquals(inputList.size(), deserializeList.size());
        for (int i = 0; i < deserializeList.size(); i++) {
            assertEquals(inputList.get(i), deserializeList.get(i));
        }

        double finalWriteTime = (endWriteTime - startWriteTime) / 1000.0;
        double finalReadTime = (endReadTime - startReadTime) / 1000.0;
        System.out.println("autoSerializeTest:\n\t\twrite time = " + finalWriteTime + " sec\n" +
                "\t\tread time = " + finalReadTime + " sec");
    }

    @Test
    public void serializeWithMethodsTest() throws IOException {
        List<AnimalWithMethods> inputList = randomAnimalWithMethodsList();
        String path = Paths.get("src", "test", "resources", "serialize", "serialize-with-methods").toAbsolutePath().toString();
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        Serializer serializer = new Serializer();

        long startWriteTime = System.currentTimeMillis();
        serializer.serializeWithMethods(inputList, path);
        long endWriteTime = System.currentTimeMillis();
        long startReadTime = System.currentTimeMillis();
        List<AnimalWithMethods> deserializeList = serializer.deserializeWithMethods(path);
        long endReadTime = System.currentTimeMillis();

        assertEquals(inputList.size(), deserializeList.size());
        for (int i = 0; i < deserializeList.size(); i++) {
            assertEquals(inputList.get(i), deserializeList.get(i));
        }

        double finalWriteTime = (endWriteTime - startWriteTime) / 1000.0;
        double finalReadTime = (endReadTime - startReadTime) / 1000.0;
        System.out.println("serializeWithMethodsTest:\n\t\twrite time = " + finalWriteTime + " sec\n" +
                "\t\tread time = " + finalReadTime + " sec");
    }


    @Test
    public void externalizeTest() throws IOException {
        List<AnimalExternalizable> inputList = randomAnimalExternalizableList();
        String path = Paths.get("src", "test", "resources", "serialize", "externalizable").toAbsolutePath().toString();
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        Serializer serializer = new Serializer();

        long startWriteTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(inputList, path);
        long endWriteTime = System.currentTimeMillis();
        long startReadTime = System.currentTimeMillis();
        List<AnimalExternalizable> deserializeList = serializer.deserializeWithExternalizable(path);
        long endReadTime = System.currentTimeMillis();

        assertEquals(inputList.size(), deserializeList.size());
        for (int i = 0; i < deserializeList.size(); i++) {
            assertEquals(inputList.get(i), deserializeList.get(i));
        }

        double finalWriteTime = (endWriteTime - startWriteTime) / 1000.0;
        double finalReadTime = (endReadTime - startReadTime) / 1000.0;
        System.out.println("externalizeTest:\n\t\twrite time = " + finalWriteTime + " sec\n" +
                "\t\tread time = " + finalReadTime + " sec");
    }

    @Test
    public void customSerializeTest() throws IOException {
        List<Animal> inputList = randomAnimalList();
        String path = Paths.get("src", "test", "resources", "serialize", "custom-serialize").toAbsolutePath().toString();
        File file = new File(path);
        file.getParentFile().mkdirs();
        file.createNewFile();
        Serializer serializer = new Serializer();

        long startWriteTime = System.currentTimeMillis();
        serializer.customSerialize(inputList, path);
        long endWriteTime = System.currentTimeMillis();
        long startReadTime = System.currentTimeMillis();
        List<Animal> deserializeList = serializer.customDeserialize(path);
        long endReadTime = System.currentTimeMillis();

        assertEquals(inputList.size(), deserializeList.size());
        for (int i = 0; i < deserializeList.size(); i++) {
            assertEquals(inputList.get(i), deserializeList.get(i));
        }

        double finalWriteTime = (endWriteTime - startWriteTime) / 1000.0;
        double finalReadTime = (endReadTime - startReadTime) / 1000.0;
        System.out.println("customSerializeTest:\n\t\twrite time = " + finalWriteTime + " sec\n" +
                "\t\tread time = " + finalReadTime + " sec");
    }

    private List<Animal> randomAnimalList() {
        List<Animal> list = new ArrayList<>();
        for (int i = 0; i < ANIMAL_COUNT; i++) {
            Animal newAnimal = generateAnimal();
            list.add(newAnimal);
        }
        return list;
    }

    private List<AnimalWithMethods> randomAnimalWithMethodsList() {
        List<AnimalWithMethods> list = new ArrayList<>();
        for (int i = 0; i < ANIMAL_COUNT; i++) {
            Animal newAnimal = generateAnimal();
            if (newAnimal == null) {
                list.add(null);
            } else {
                AnimalWithMethods animalSerializable = new AnimalWithMethods(
                        newAnimal.isPet(),
                        newAnimal.isWild(),
                        newAnimal.getLegsCount(),
                        newAnimal.getName(),
                        newAnimal.getAge(),
                        newAnimal.getMoveType(),
                        newAnimal.getId(),
                        newAnimal.getLivingEnvironment() == null ? null :
                                new AnimalWithMethods.LivingEnvironment(
                                        newAnimal.getLivingEnvironment().getTemperature(),
                                        newAnimal.getLivingEnvironment().getWeather()
                                )
                );
                list.add(animalSerializable);
            }
        }
        return list;
    }

    private List<AnimalExternalizable> randomAnimalExternalizableList() {
        List<AnimalExternalizable> list = new ArrayList<>();
        for (int i = 0; i < ANIMAL_COUNT; i++) {
            Animal newAnimal = generateAnimal();
            if (newAnimal == null) {
                list.add(null);
            } else {
                AnimalExternalizable animalExternalizable = new AnimalExternalizable(
                        newAnimal.isPet(),
                        newAnimal.isWild(),
                        newAnimal.getLegsCount(),
                        newAnimal.getName(),
                        newAnimal.getAge(),
                        newAnimal.getMoveType(),
                        newAnimal.getId(),
                        newAnimal.getLivingEnvironment() == null ? null :
                                new AnimalExternalizable.LivingEnvironment(
                                        newAnimal.getLivingEnvironment().getTemperature(),
                                        newAnimal.getLivingEnvironment().getWeather()
                                )
                );
                list.add(animalExternalizable);
            }
        }
        return list;
    }

    private Animal generateAnimal() {
        if (Math.random() < 0.03) {
            return null;
        }

        boolean isPet = Math.random() < 0.5;
        boolean isWild = Math.random() < 0.5;
        Integer legsCount = Math.random() < 0.2 ? null : ThreadLocalRandom.current().nextInt(0, 50);
        Double age = Math.random() < 0.2 ? null : ThreadLocalRandom.current().nextDouble(0, 80);
        Byte id = Math.random() < 0.2 ? null : (byte) ThreadLocalRandom.current().nextDouble(0, 80);

        char[] charArray = new char[15];
        for (int i = 0; i < charArray.length; i++) {
            charArray[i] = (char) ('a' + ThreadLocalRandom.current().nextInt(0, 26));
        }
        String name = Math.random() < 0.2 ? null : new String(charArray);

        int pickMoveType = new Random().nextInt(MoveType.values().length);
        MoveType moveType = Math.random() < 0.2 ? null : MoveType.values()[pickMoveType];

        double temperature = ThreadLocalRandom.current().nextDouble(-30, 30);
        int pickWeatherIndex = new Random().nextInt(Weather.values().length);
        Weather weather = Math.random() < 0.2 ? null : Weather.values()[pickWeatherIndex];
        Animal.LivingEnvironment livingEnvironment = Math.random() < 0.2 ?
                null :
                new Animal.LivingEnvironment(temperature, weather);

        return new Animal(isPet, isWild, legsCount, name, age, moveType, id, livingEnvironment);
    }
}
