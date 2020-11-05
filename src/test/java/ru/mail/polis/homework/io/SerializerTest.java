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
                    selectRandom("name1", "vasya", "petya", "tests", "sugar", "tiger", "glove", "check", "board", "cola"),
                    selectRandom(Animal.Diet.values()),
                    generateGenotypeDef(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2; i < testSize; i++) {
            animalsDef.add(animalsDef.get(i - testSize / 2));
        }
        // Animals externalizable
        for (int i = 0; i < testSize / 2; i++) {
            animalsExt.add(new AnimalExternalizable(
                    selectRandom("name1", "vasya", "petya", "tests", "sugar", "tiger", "glove", "check", "board", "cola"),
                    selectRandom(AnimalExternalizable.Diet.values()),
                    generateGenotypeExt(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2; i < testSize; i++) {
            animalsExt.add(animalsExt.get(i - testSize / 2));
        }
        // Animals methods
        for (int i = 0; i < testSize / 2; i++) {
            animalsMet.add(new AnimalWithMethods(
                    selectRandom("name1", "vasya", "petya", "tests", "sugar", "tiger", "glove", "check", "board", "cola"),
                    selectRandom(AnimalWithMethods.Diet.values()),
                    generateGenotypeMet(),
                    rng.nextInt(),
                    generateScaredOf(),
                    selectRandom(true, false)));
        }
        for (int i = testSize / 2; i < testSize; i++) {
            animalsMet.add(animalsMet.get(i - testSize / 2));
        }
    }

    @Test
    public void defaultSerialize() throws IOException, ClassNotFoundException {
        System.out.println("Default");
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
        System.out.println("Externalizable");
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
        System.out.println("Private Methods");
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
        System.out.println("Custom");
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

    @Test
    public void customDeepSerialize() throws IOException, ClassNotFoundException {
        System.out.println("Deep Custom");
        timerStart();
        Serializer.customDeepSerialize(animalsDef, fileName);
        timerStop("to serialize");
        timerStart();
        List<Animal> list = Serializer.customDeepDeserialize(fileName);
        timerStop("to deserialize");
        assertEquals(animalsDef, list);
        System.out.println("File size: " + Files.size(Paths.get(fileName)));
        deleteFile(fileName);
    }

    @Test
    public void customDeepStableSerialize() throws IOException, ClassNotFoundException {
        System.out.println("Deep Custom Stable");
        timerStart();
        Serializer.customDeepStableSerialize(animalsDef, fileName);
        timerStop("to serialize");
        timerStart();
        List<Animal> list = Serializer.customDeepStableDeserialize(fileName);
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
        int size = 192;
        List<Integer> scaredOf = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            scaredOf.add(rng.nextInt());
        }
        return scaredOf;
    }

    private static Animal.Genotype generateGenotypeDef() {
        int size = 192;
        List<Animal.Chromosome> chromosomes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            chromosomes.add(new Animal.Chromosome(generateGenes(), selectRandom(true, false)));
        }
        return new Animal.Genotype(chromosomes, selectRandom(true, false));
    }

    private static AnimalWithMethods.Genotype generateGenotypeMet() {
        int size = 192;
        List<AnimalWithMethods.Chromosome> chromosomes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            chromosomes.add(new AnimalWithMethods.Chromosome(generateGenes(), selectRandom(true, false)));
        }
        return new AnimalWithMethods.Genotype(chromosomes, selectRandom(true, false));
    }

    private static AnimalExternalizable.Genotype generateGenotypeExt() {
        int size = 192;
        List<AnimalExternalizable.Chromosome> chromosomes = new ArrayList<>(size);
        for (int i = 0; i < size; i++) {
            chromosomes.add(new AnimalExternalizable.Chromosome(generateGenes(), selectRandom(true, false)));
        }
        return new AnimalExternalizable.Genotype(chromosomes, selectRandom(true, false));
    }

    private static int[] generateGenes() {
        int cSize = 4000;
        int[] genes = new int[cSize];
        for (int j = 0; j < cSize; j++) {
            genes[j] = rng.nextInt();
        }
        return genes;
    }

    private static <T> T selectRandom(T... objects) {
        return objects[rng.nextInt(objects.length)];
    }

    private static void deleteFile(String fileName) throws IOException {
        Files.delete(Paths.get(fileName));
    }
}
