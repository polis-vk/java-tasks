package ru.mail.polis.homework.io.objects;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Нужно создать тесты для этого файла
 * 1) тест на чтение и запись пустого списка
 * 2) тест на чтение и запись некоторого списка (список до и после процедуры должны быть равны)
 * 3) тест на чтение и запись некоторых разных списков (одинаковые списки до и после процедуры должны быть равны,
 * а разные списки до и после процедуры должны быть не равны)
 */
public class Serializer {

    /**
     * 1 балл
     * Реализовать простую сериализацию, с помощью специального потока для сериализации объектов
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл в который "пишем" животных
     */
    public void defaultSerialize(List<Animal> animals, String fileName) {
        Path pathToFile = Paths.get(fileName);

        try {
            Files.createDirectories(pathToFile.getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }

        try (ObjectOutputStream outputStream = new ObjectOutputStream(Files.newOutputStream(pathToFile))) {
            outputStream.writeObject(animals);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 1 балл
     * Реализовать простую дисериализацию, с помощью специального потока для дисериализации объектов
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> defaultDeserialize(String fileName) {
        Path pathToFile = Paths.get(fileName);
        if (!Files.exists(pathToFile)) {
            return null;
        }

        try (ObjectInputStream inputStream = new ObjectInputStream(Files.newInputStream(pathToFile))) {
            return (List<Animal>) inputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 2 балла
     * Реализовать ручную сериализацию, с помощью высокоровневых потоков
     *
     * @param animals  Список животных для сериализации
     * @param fileName файл, в который "пишем" животных
     */
    public void customSerialize(List<Animal> animals, String fileName) {

        try {
            Files.createDirectories(Paths.get(fileName).getParent());
        } catch (IOException e) {
            e.printStackTrace();
        }


        try (ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream()) {

            for (Animal animal : animals) {
                byte[] buffer = animal.toString().getBytes();
                byteArrayOutputStream.write(buffer);
            }

            try (FileOutputStream fileOutputStream = new FileOutputStream(fileName)) {
                byteArrayOutputStream.writeTo(fileOutputStream);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 2 балла
     * Реализовать ручную дисериализацию, с помощью высокоровневых потоков
     *
     * @param fileName файл из которого "читаем" животных
     * @return список животных
     */
    public List<Animal> customDeserialize(String fileName) {

        if (!Files.exists(Paths.get(fileName))) {
            return null;
        }

        List<Animal> animals = new ArrayList<>();

        try (FileInputStream fileInputStream = new FileInputStream(fileName)) {
            byte[] buffer = new byte[fileInputStream.available()];
            while (fileInputStream.available() > 0) {
                fileInputStream.read(buffer);
                int b;
                try (ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(buffer)) {
                    Animal animal;
                    String word = "";
                    String name = "";
                    String type = "";
                    String nameOwner = "";
                    String phoneOwner = "";
                    while ((b = byteArrayInputStream.read()) != -1) {

                        word += (char) b;
                        if (isFieldObject(word, "{ \"name\" : \"", "\", \"type\" : \"")) {
                            name = doWord(word, "{ \"name\" : \"", "\", \"type\" : \"");
                            word = word.substring(word.indexOf("\", \"type\" : \""));
                        }

                        if (isFieldObject(word, "\", \"type\" : \"", "\", \"{ \"name\" : \"")) {
                            type = doWord(word, "\", \"type\" : \"", "\", \"{ \"name\" : \"");
                            word = word.substring(word.indexOf("\", \"{ \"name\" : \""));
                        }

                        if (isFieldObject(word, "\", \"{ \"name\" : \"", "\", \"phoneNumber\" : \"")) {
                            nameOwner = doWord(word, "\", \"{ \"name\" : \"", "\", \"phoneNumber\" : \"");
                            word = word.substring(word.indexOf("\", \"phoneNumber\" : \""));
                        }

                        if (isFieldObject(word, "\", \"phoneNumber\" : \"", "\" }\" }")) {
                            phoneOwner = doWord(word, "\", \"phoneNumber\" : \"", "\" }\" }");
                            word = "";
                            animal = new Animal(name, AnimalType.valueOf(type), new AnimalOwner(nameOwner, phoneOwner));
                            animals.add(animal);
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }

            }

        } catch (IOException e) {
            e.printStackTrace();
        }


        return animals;
    }

    public boolean isFieldObject(String word, String s, String s2) {
        return word.startsWith(s) && word.endsWith(s2);
    }

    public static String doWord(String word, String start, String end) {
        StringBuilder result = new StringBuilder();
        int startIndex = word.indexOf(start);
        int endIndex = word.indexOf(end);
        for (int i = startIndex + start.length(); i < endIndex; i++) {
            result.append(word.charAt(i));
        }
        return result.toString();
    }
}
