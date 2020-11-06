package ru.mail.polis.homework.io.objects;

import org.junit.BeforeClass;
import org.junit.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    
    private static final Path FILE_PATH = Paths.get("src", "test", "resources", "fileForSerialize.bin");
    private static final Random RANDOM = new Random();
    private static final Serializer serializer = new Serializer();
    
    private static final int N = 10000;
    private static List<Animal> animalList;
    private static List<AnimalWithMethods> animalWithMethodsList;
    private static List<AnimalExternalizable> animalExternalizableList;
    
    @BeforeClass
    public static void setUp() {
        animalList = Stream.generate(RandomAnimal::getAnimal)
                .limit(N)
                .collect(Collectors.toList());
        animalWithMethodsList = Stream.generate(RandomAnimal::getAnimalWithMethods)
                .limit(N)
                .collect(Collectors.toList());
        animalExternalizableList = Stream.generate(RandomAnimal::getAnimalExternalizable)
                .limit(N)
                .collect(Collectors.toList());
    }
    
    @Test
    public void serializeTest() throws IOException {
        checkCorrectSerialize(serializer::defaultSerialize, serializer::defaultDeserialize, animalList);
        checkCorrectSerialize(serializer::serializeWithExternalizable, serializer::deserializeWithExternalizable, animalExternalizableList);
        checkCorrectSerialize(serializer::serializeWithMethods, serializer::deserializeWithMethods, animalWithMethodsList);
        checkCorrectSerialize(serializer::customSerialize, serializer::customDeserialize, animalList);
    }
    
    private <T> void checkCorrectSerialize(BiConsumer<List<T>, String> serialize, Function<String, List<T>> deserialize, List<T> animals) throws IOException {
        serialize.accept(animals, FILE_PATH.toString());
        List<T> animalsAfterSerialize = deserialize.apply(FILE_PATH.toString());
        Files.deleteIfExists(FILE_PATH);
        assertEquals(animals, animalsAfterSerialize);
    }
    
    @Test
    public void serializeTimeTest() throws IOException {
        serializeTime(serializer::defaultSerialize, serializer::defaultDeserialize, animalList, "Default serialize");
        serializeTime(serializer::serializeWithExternalizable, serializer::deserializeWithExternalizable, animalExternalizableList, "External serialize");
        serializeTime(serializer::serializeWithMethods, serializer::deserializeWithMethods, animalWithMethodsList, "Serialize with methods");
        serializeTime(serializer::customSerialize, serializer::customDeserialize, animalList, "Custom serialize");
    }
    
    private <T> void serializeTime(BiConsumer<List<T>, String> serialize, Function<String, List<T>> deserialize, List<T> animals, String serializeType) throws IOException {
        long startTime = System.currentTimeMillis();
        serialize.accept(animals, FILE_PATH.toString());
        long finishTime = System.currentTimeMillis();
        long serializeTime = finishTime - startTime;
        startTime = System.currentTimeMillis();
        deserialize.apply(FILE_PATH.toString());
        finishTime = System.currentTimeMillis();
        long deserializeTime = finishTime - startTime;
        printResult(serializeType, serializeTime, deserializeTime, Files.size(FILE_PATH));
        Files.deleteIfExists(FILE_PATH);
    }
    
    private void printResult(String serializeType, long serializeTime, long deserializeTime, long fileSize) {
        System.out.printf(serializeType
                        + ":\nserialize time: %d ms\ndeserialize time: %d ms\nfile size: %d bytes\n\n",
                serializeTime, deserializeTime, fileSize);
    }
    
    
    private static class RandomAnimal {
        private static final int MIN_WEIGHT = 100;
        private static final int MAX_WEIGHT = 200;
        private static final int MAX_AGE = 100;
        private static final int MIN_NAME_LENGTH = 3;
        private static final int MAX_NAME_LENGTH = 10;
        private static final int OWNER_TYPES_COUNT = Animal.Owner.values().length;
        
        public static Animal getAnimal() {
            return new Animal(RandomAnimal.getAge(), RandomAnimal.getIsFriendly(),
                    RandomAnimal.getName(), RandomAnimal.getWeightByLastDays(),
                    RandomAnimal.getKind(), RandomAnimal.getOwnerType());
        }
        
        public static AnimalExternalizable getAnimalExternalizable() {
            return new AnimalExternalizable(RandomAnimal.getAge(), RandomAnimal.getIsFriendly(),
                    RandomAnimal.getName(), RandomAnimal.getWeightByLastDays(),
                    RandomAnimal.getKind(), RandomAnimal.getOwnerType());
        }
        
        public static AnimalWithMethods getAnimalWithMethods() {
            return new AnimalWithMethods(RandomAnimal.getAge(), RandomAnimal.getIsFriendly(),
                    RandomAnimal.getName(), RandomAnimal.getWeightByLastDays(),
                    RandomAnimal.getKind(), RandomAnimal.getOwnerType());
        }
        
        private static int getAge() {
            return RANDOM.nextInt(MAX_AGE);
        }
        
        private static boolean getIsFriendly() {
            return RANDOM.nextBoolean();
        }
        
        private static String getName() {
            char[] name = new char[RANDOM.nextInt(MAX_NAME_LENGTH - MIN_NAME_LENGTH) + MIN_NAME_LENGTH];
            name[0] = (char) (RANDOM.nextInt(26) + 'A');
            for (int i = 1; i < name.length; i++) {
                name[i] = (char) (RANDOM.nextInt(26) + 'a');
            }
            return new String(name);
        }
        
        private static List<Integer> getWeightByLastDays() {
            return RANDOM.ints(MIN_WEIGHT, MAX_WEIGHT)
                    .limit(RANDOM.nextInt(20) + 1)
                    .boxed()
                    .collect(Collectors.toList());
        }
        
        private static Kind getKind() {
            return new Kind(getName(), RANDOM.nextInt(Integer.MAX_VALUE) + 1);
        }
        
        private static Animal.Owner getOwnerType() {
            return Animal.Owner.values()[RANDOM.nextInt(OWNER_TYPES_COUNT)];
        }
    }
}