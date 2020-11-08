package ru.mail.polis.homework.io.objects;

import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private final String fileName = "file.bin";
    private final int sizeOfList = 1000;
    private final Serializer serializer = new Serializer();

    @Test
    public void checkDefaultSerialize() throws IOException, ClassNotFoundException {
        System.out.println("CHECK DEFAULT SERIALIZE:");
        List<Animal> animalList = getAnimalList();
        TestConsumer defaultSerializeTest = () -> serializer.defaultSerialize(animalList, fileName);
        TestSupplier<List<Animal>> defaultDeserializeTest = () -> serializer.defaultDeserialize(fileName);
        checkSerialize(animalList, defaultSerializeTest, defaultDeserializeTest);
    }

    @Test
    public void checkSerializeWithMethods() throws IOException, ClassNotFoundException {
        System.out.println("CHECK SERIALIZE WITH METHODS");
        List<AnimalWithMethods> animalList = getAnimalWithMethodsList();
        TestConsumer serializeWithMethodsTest = () -> serializer.serializeWithMethods(animalList, fileName);
        TestSupplier<List<AnimalWithMethods>> deserializeWithMethodsTest =
                () -> serializer.deserializeWithMethods(fileName);
        checkSerialize(animalList, serializeWithMethodsTest, deserializeWithMethodsTest);
    }

    @Test
    public void checkSerializeWithExternalizable() throws IOException, ClassNotFoundException {
        System.out.println("CHECK SERIALIZE WITH EXTERNALIZABLE");
        List<AnimalExternalizable> animalList = getAnimalExternalizableList();
        TestConsumer serializeWithExternalizableTest = () -> serializer.serializeWithExternalizable(animalList, fileName);
        TestSupplier<List<AnimalExternalizable>> deserializeWithExternalizableTest
                = () -> serializer.deserializeWithExternalizable(fileName);
        checkSerialize(animalList, serializeWithExternalizableTest, deserializeWithExternalizableTest);
    }

    @Test
    public void checkCustomSerialize() throws IOException, ClassNotFoundException {
        System.out.println("CHECK CUSTOM SERIALIZE");
        List<Animal> animalList = getAnimalList();
        TestConsumer customSerializeTest = () -> serializer.customSerialize(animalList, fileName);
        TestSupplier customDeserializeTest = () -> serializer.customDeserialize(fileName);
        checkSerialize(animalList, customSerializeTest, customDeserializeTest);
    }

    public Path path() {
        return Paths.get(fileName);
    }

    public Builder getChildAnimalBuilder() {
        return new Builder()
                .setName("Child")
                .setBreed(Breeds.LION)
                .setAge(1)
                .setEat(List.of(Eat.MILK))
                .setLocation("zoo");
    }

    private Builder getMotherAnimalBuilder() {
        return new Builder()
                .setName("Mom")
                .setBreed(Breeds.LION)
                .setAge(6)
                .setEat(List.of(Eat.MEAT, Eat.MILK, Eat.FISH))
                .setLocation("zoo");
    }

    private Builder getFatherAnimalBuilder() {
        return new Builder()
                .setName("Dad")
                .setBreed(Breeds.LION)
                .setAge(7)
                .setInWild(true)
                .setLocation("Jungle");
    }

    public List<Animal> getAnimalList() {
        return IntStream.rangeClosed(1, sizeOfList)
                .mapToObj(num -> getChildAnimalBuilder())
                .map(builder -> builder
                        .setMother(getMotherAnimalBuilder()
                                .buildAnimal())
                        .setFather(getFatherAnimalBuilder()
                                .buildAnimal())
                        .buildAnimal())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<AnimalWithMethods> getAnimalWithMethodsList() {
        return IntStream.rangeClosed(1, sizeOfList)
                .mapToObj(num -> getChildAnimalBuilder())
                .map(builder -> builder
                        .setMother(getMotherAnimalBuilder()
                                .buildAnimalWithMethods())
                        .setFather(getFatherAnimalBuilder()
                                .buildAnimalWithMethods())
                        .buildAnimalWithMethods())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public List<AnimalExternalizable> getAnimalExternalizableList() {
        return IntStream.rangeClosed(1, sizeOfList)
                .mapToObj(num -> getChildAnimalBuilder())
                .map(builder -> builder
                        .setMother(getMotherAnimalBuilder()
                                .buildAnimalExternalizable())
                        .setFather(getFatherAnimalBuilder()
                                .buildAnimalExternalizable())
                        .buildAnimalExternalizable())
                .collect(Collectors.toCollection(ArrayList::new));
    }

    private void acceptTestMethodAndPrintWorkTime(TestConsumer testMethod) throws IOException {
        long startTime = System.currentTimeMillis();
        testMethod.accept();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("Work time:  " + resultTime + " ms");
    }

    private <T> void acceptTestMethodAndPrintWorkTime(TestSupplier<T> testMethod)
            throws IOException, ClassNotFoundException {
        long startTime = System.currentTimeMillis();
        testMethod.get();
        long endTime = System.currentTimeMillis();
        long resultTime = endTime - startTime;
        System.out.println("Work time:  " + resultTime + " ms");
    }

    private <T extends List> void checkSerialize(T list, TestConsumer testConsumer, TestSupplier<T> testSupplier) throws IOException, ClassNotFoundException {
        System.out.println("SERIALIZER:");
        acceptTestMethodAndPrintWorkTime(testConsumer);
        System.out.println("DESERIALIZER:");
        acceptTestMethodAndPrintWorkTime(testSupplier);
        checkFileSize();
        T newList = testSupplier.get();
        assertEquals(list.toString(), newList.toString());
        Files.delete(path());
        System.out.println();
    }

    private void checkFileSize() throws IOException {
        System.out.printf("Size of file: %s bytes", Files.size(path()));
        System.out.println();
    }
}
