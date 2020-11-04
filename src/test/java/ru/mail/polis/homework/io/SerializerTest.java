package ru.mail.polis.homework.io;

import org.junit.BeforeClass;
import org.junit.Test;
import ru.mail.polis.homework.io.objects.*;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.List;
import java.util.Random;
import java.util.ArrayList;
import java.nio.file.Files;

import static org.junit.Assert.assertEquals;

public class SerializerTest {
    private static String fileName = "perft";
    private static int testSize = 10000;
    private static List<Animal> animalsDef = new ArrayList<>(testSize * 2);
    private static List<AnimalExternalizable> animalsExt = new ArrayList<>(testSize * 2);
    private static List<AnimalWithMethods> animalsMet = new ArrayList<>(testSize * 2);

    private static Random rng = new Random();
    private static long startTime;

    @BeforeClass
    public static void setup() {
        // Animals default
        for (int i = 0; i < testSize / 2; i++) {
            animalsDef.add(new Animal(
                    selectRandom("name1", "vasya", "petya", "test", "sugar", "tiger", "gloves", "check", "keyboard", "cola"),
                    selectRandom(Animal.Diet.values()),
                    generateGenotype(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2 + 1; i < testSize; i++) {
            animalsDef.add(animalsDef.get(i - testSize / 2));
        }
        // Animals externalizable
        for (int i = 0; i < testSize / 2; i++) {
            animalsExt.add(new AnimalExternalizable(
                    selectRandom("name1", "vasya", "petya", "test", "sugar", "tiger", "gloves", "check", "keyboard", "cola"),
                    selectRandom(AnimalExternalizable.Diet.values()),
                    generateGenotype(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2 + 1; i < testSize; i++) {
            animalsExt.add(animalsExt.get(i - testSize / 2));
        }
        // Animals methods
        for (int i = 0; i < testSize / 2; i++) {
            animalsMet.add(new AnimalWithMethods(
                    selectRandom("name1", "vasya", "petya", "test", "sugar", "tiger", "gloves", "check", "keyboard", "cola"),
                    selectRandom(AnimalWithMethods.Diet.values()),
                    generateGenotype(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2 + 1; i < testSize; i++) {
            animalsMet.add(animalsMet.get(i - testSize / 2));
        }
    }

    @Test
    public void defaultSerialize() throws IOException, ClassNotFoundException {
        System.out.println(" - - Default - -");
        timerStart();
        Serializer.defaultSerialize(animalsDef, fileName);
        timerStop("to serialize");
        timerStart();
        List<Animal> list = Serializer.defaultDeserialize(fileName);
        timerStop("to deserialize");
        assertEquals(animalsDef, list);
        System.out.println("File size: " + Files.size(Paths.get(fileName)));
        deleteFile(fileName);
    }


    @Test
    public void externalizableSerialize() throws IOException, ClassNotFoundException {
        System.out.println(" - - Externalizable - -");
        timerStart();
        Serializer.serializeWithExternalizable(animalsExt, fileName);
        timerStop("to serialize");
        timerStart();
        List<AnimalExternalizable> list = Serializer.deserializeWithExternalizable(fileName);
        timerStop("to deserialize");
        assertEquals(animalsExt, list);
        System.out.println("File size: " + Files.size(Paths.get(fileName)));
        deleteFile(fileName);
    }

    @Test
    public void methodsSerialize() throws IOException, ClassNotFoundException {
        System.out.println(" - - Methods - -");
        timerStart();
        Serializer.serializeWithMethods(animalsMet, fileName);
        timerStop("to serialize");
        timerStart();
        List<AnimalWithMethods> list = Serializer.deserializeWithMethods(fileName);
        timerStop("to deserialize");
        assertEquals(animalsMet, list);
        System.out.println("File size: " + Files.size(Paths.get(fileName)));
        deleteFile(fileName);
    }

    @Test
    public void customSerialize() throws IOException, ClassNotFoundException {
        System.out.println(" - - Custom - -");
        timerStart();
        Serializer.customSerialize(animalsDef, fileName);
        timerStop("to serialize");
        timerStart();
        List<Animal> list = Serializer.customDeserialize(fileName);
        timerStop("to deserialize");
        assertEquals(animalsDef, list);
        System.out.println("File size: " + Files.size(Paths.get(fileName)));
        deleteFile(fileName);
    }

    private static void timerStart() {
        startTime = System.currentTimeMillis();
    }

    private static void timerStop(String text) {
        long endTime = System.currentTimeMillis();
        startTime = endTime - startTime;
        System.out.println("Time " + text + ": " + startTime);
    }

    private static List<Integer> generateScaredOf() {
        int size = rng.nextInt(192);
        List<Integer> scaredOf = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scaredOf.add(rng.nextInt());
        }
        return scaredOf;
    }

    private static Genotype generateGenotype() {
        int size = rng.nextInt(192);
        List<Chromosome> chromosomes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            int cSize = rng.nextInt(4000);
            int[] genes = new int[cSize];
            for (int j = 0; j < cSize; j++) {
                genes[j] = rng.nextInt();
            }
            chromosomes.add(new Chromosome(genes, selectRandom(true, false)));
        }
        return new Genotype(chromosomes, selectRandom(true, false));
    }

    private static <T> T selectRandom(T... objects) {
        return objects[rng.nextInt(objects.length)];
    }

    private static void deleteFile(String fileName) throws IOException {
        Files.delete(Paths.get(fileName));
    }
}
