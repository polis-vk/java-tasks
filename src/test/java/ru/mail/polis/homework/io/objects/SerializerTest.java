package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import org.junit.FixMethodOrder;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import static org.junit.Assert.assertEquals;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SerializerTest {
    private static final Path TEST_DIRECTORY = Paths.get("testResources");
    private static final Path DEFAULT_SERIALIZE_OUTPUT_FILE = TEST_DIRECTORY.resolve("defaultSerialize.bin");
    private static final Path SERIALIZE_WITH_METHODS_OUTPUT_FILE = TEST_DIRECTORY.resolve("serializeWithMethods.bin");
    private static final Path SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE = TEST_DIRECTORY.resolve("serializeWithExternalizable.bin");
    private static final Path CUSTOM_SERIALIZE_OUTPUT_FILE = TEST_DIRECTORY.resolve("customSerialize.bin");
    private static final int NUMBER_OF_OBJECTS = 10;
    private static final int NUMBER_OF_WRITES = 500;

    private static final List<Animal> animals = new ArrayList<>();
    private static final List<AnimalWithMethods> animalsWithMethods = new ArrayList<>();
    private static final List<AnimalExternalizable> animalsExternalizable = new ArrayList<>();
    private static final Serializer serializer = new Serializer();
    private static final Random rnd = new Random();

    @BeforeClass
    public static void initialize() throws IOException{
        List<String> names = Arrays.asList("Alex", "Bob", "Chris", "Donald", "Ethan", "Felix", "George");
        List<String> domains = Arrays.asList("Archaea", "Bacteria", "Eucarya");
        List<String> kingdoms = Arrays.asList("Animalia", "Plantae", "Fungi", "Protista", "Monera");
        List<String> phyla = Arrays.asList("Mollusca", "Porifera", "Cnidaria", "Platyhelminthes", "Nematoda", "Annelida", "Arthropoda", "Echinodermata");
        List<String> classes = Arrays.asList("Mammalia", "Aves", "Diplopoda");
        List<String> orders = Arrays.asList("Carnivora", "Lagomorpha");
        List<String> families = Arrays.asList("Ailuridae", "Leporidae", "Felidae", "Canidae");
        List<String> genera = Arrays.asList("Canis", "Felis", "Ailurus", "Romerolagus");
        List<String> allSpecies = Arrays.asList("F. catus", "A. fulgens", "R. diazi", "C. lupus");
        List<Color> allColors = Arrays.asList(Color.values());
        List<EatingStrategy> allEatingStrategies = Arrays.asList(EatingStrategy.values());
        
        for (int i = 0; i < NUMBER_OF_OBJECTS; i++) {
            String name = names.get(rnd.nextInt(names.size()));
            int age = rnd.nextInt(100);
            boolean isTame = rnd.nextBoolean();
            int colorsCount = rnd.nextInt(5) + 1;
            Color[] colors = new Color[colorsCount];
            for (int j = 0; j < colorsCount; j++) {
                colors[j] = allColors.get(rnd.nextInt(allColors.size()));
            }
            EatingStrategy eatingStrategy = allEatingStrategies.get(rnd.nextInt(allEatingStrategies.size()));
            String domain = domains.get(rnd.nextInt(domains.size()));
            String kingdom = kingdoms.get(rnd.nextInt(kingdoms.size()));
            String phylum = phyla.get(rnd.nextInt(phyla.size()));
            String classT = classes.get(rnd.nextInt(classes.size()));
            String order = orders.get(rnd.nextInt(orders.size()));
            String family = families.get(rnd.nextInt(families.size()));
            String genus = genera.get(rnd.nextInt(genera.size()));
            String species = allSpecies.get(rnd.nextInt(allSpecies.size()));

            Animal.Builder builder = new Animal.Builder(name, age, isTame, eatingStrategy, colors);

            builder.setDomain(domain)
                .setKingdom(kingdom)
                .setPhylum(phylum)
                .setClassT(classT)
                .setOrder(order)
                .setFamily(family)
                .setGenus(genus)
                .setSpecies(species);  

            AnimalWithMethods.Builder builderWithMethods = new AnimalWithMethods.Builder(name, age, isTame, eatingStrategy, colors);

            builderWithMethods.setDomain(domain)
                .setKingdom(kingdom)
                .setPhylum(phylum)
                .setClassT(classT)
                .setOrder(order)
                .setFamily(family)
                .setGenus(genus)
                .setSpecies(species);

            AnimalExternalizable.Builder builderExternalizable = new AnimalExternalizable.Builder(name, age, isTame, eatingStrategy, colors);

            builderExternalizable.setDomain(domain)
                .setKingdom(kingdom)
                .setPhylum(phylum)
                .setClassT(classT)
                .setOrder(order)
                .setFamily(family)
                .setGenus(genus)
                .setSpecies(species);

            animals.add(builder.build());
            animalsWithMethods.add(builderWithMethods.build());
            animalsExternalizable.add(builderExternalizable.build());
        }

        if (Files.exists(TEST_DIRECTORY)) {
            FileUtils.deleteDirectory(TEST_DIRECTORY.toFile());
        }
        Files.createDirectories(TEST_DIRECTORY);
        Files.createFile(DEFAULT_SERIALIZE_OUTPUT_FILE);
        Files.createFile(SERIALIZE_WITH_METHODS_OUTPUT_FILE);
        Files.createFile(SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE);
        Files.createFile(CUSTOM_SERIALIZE_OUTPUT_FILE);
    }

    @AfterClass
    public static void cleanUp() throws IOException {
        FileUtils.deleteDirectory(TEST_DIRECTORY.toFile());
    }

    @Test
    public void aDefaultSerialize() throws IOException, ClassNotFoundException {
        String filePath = DEFAULT_SERIALIZE_OUTPUT_FILE.toAbsolutePath().toString();

        System.out.printf("-DefaultSerialize Test-\n(Reading/Writing %d Objects %d Times)\n",
        NUMBER_OF_OBJECTS, NUMBER_OF_WRITES);

        long beginTime = System.currentTimeMillis();
        serializer.defaultSerialize(animals, filePath, true);
        for (int i = 1; i < NUMBER_OF_WRITES; i++) {
            serializer.defaultSerialize(animals, filePath, false);
        }
        long endTime = System.currentTimeMillis();

        System.out.print(String.format("The file (%d bytes) was written in %d ms ",
        Files.size(Path.of(filePath)), endTime - beginTime));

        long dBeginTime = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.defaultDeserialize(filePath, NUMBER_OF_WRITES);
        long dEndTime = System.currentTimeMillis();

        System.out.println(String.format("and read in %d ms\n",
        dEndTime - dBeginTime));
        
        List<Animal> writtenList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WRITES; i++) {
            writtenList.addAll(animals);
        }
        assertEquals(writtenList, deserializedAnimals);
    }

    @Test
    public void bSerializeWithMethods() throws IOException, ClassNotFoundException {
        String filePath = SERIALIZE_WITH_METHODS_OUTPUT_FILE.toAbsolutePath().toString();

        System.out.printf("-SerializeWithMethods Test-\n(Reading/Writing %d Objects %d Times)\n",
        NUMBER_OF_OBJECTS, NUMBER_OF_WRITES);

        long beginTime = System.currentTimeMillis();
        serializer.serializeWithMethods(animalsWithMethods, filePath, true);
        for (int i = 1; i < NUMBER_OF_WRITES; i++) {
            serializer.serializeWithMethods(animalsWithMethods, filePath, false);
        }
        long endTime = System.currentTimeMillis();

        System.out.print(String.format("The file (%d bytes) was written in %d ms ",
        Files.size(Path.of(filePath)), endTime - beginTime));

        long dBeginTime = System.currentTimeMillis();
        List<AnimalWithMethods> deserializedAnimalsWithMethods = serializer.deserializeWithMethods(filePath, NUMBER_OF_WRITES);
        long dEndTime = System.currentTimeMillis();

        System.out.println(String.format("and read in %d ms\n",
        dEndTime - dBeginTime));

        List<AnimalWithMethods> writtenList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WRITES; i++) {
            writtenList.addAll(animalsWithMethods);
        }
        assertEquals(writtenList, deserializedAnimalsWithMethods);
    }

    @Test
    public void cSerializeWithExternalizable() throws IOException, ClassNotFoundException {
        String filePath = SERIALIZE_WITH_EXTERNALIZABLE_OUTPUT_FILE.toAbsolutePath().toString();

        System.out.printf("-SerializeWithExternalizable Test-\n(Reading/Writing %d Objects %d Times)\n",
        NUMBER_OF_OBJECTS, NUMBER_OF_WRITES);

        long beginTime = System.currentTimeMillis();
        serializer.serializeWithExternalizable(animalsExternalizable, filePath, true);
        for (int i = 1; i < NUMBER_OF_WRITES; i++) {
            serializer.serializeWithExternalizable(animalsExternalizable, filePath, false);
        }
        long endTime = System.currentTimeMillis();

        System.out.print(String.format("The file (%d bytes) was written in %d ms ",
        Files.size(Path.of(filePath)), endTime - beginTime));

        long dBeginTime = System.currentTimeMillis();
        List<AnimalExternalizable> deserializedAnimalsExternalizable = serializer.deserializeWithExternalizable(filePath, NUMBER_OF_WRITES);
        long dEndTime = System.currentTimeMillis();

        System.out.println(String.format("and read in %d ms\n",
        dEndTime - dBeginTime));

        List<AnimalExternalizable> writtenList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WRITES; i++) {
            writtenList.addAll(animalsExternalizable);
        }
        assertEquals(writtenList, deserializedAnimalsExternalizable);
    }

    @Test
    public void dCustomSerialize() throws IOException {
        String filePath = CUSTOM_SERIALIZE_OUTPUT_FILE.toAbsolutePath().toString();

        System.out.printf("-CustomSerialize Test-\n(Reading/Writing %d Objects %d Times)\n",
        NUMBER_OF_OBJECTS, NUMBER_OF_WRITES);

        long beginTime = System.currentTimeMillis();
        serializer.customSerialize(animals, filePath, true);
        for (int i = 1; i < NUMBER_OF_WRITES; i++) {
            serializer.customSerialize(animals, filePath, false);
        }
        long endTime = System.currentTimeMillis();

        System.out.print(String.format("The file (%d bytes) was written in %d ms ",
        Files.size(Path.of(filePath)), endTime - beginTime));

        long dBeginTime = System.currentTimeMillis();
        List<Animal> deserializedAnimals = serializer.customDeserialize(filePath, NUMBER_OF_WRITES);
        long dEndTime = System.currentTimeMillis();

        System.out.println(String.format("and read in %d ms\n",
        dEndTime - dBeginTime));

        List<Animal> writtenList = new ArrayList<>();
        for (int i = 0; i < NUMBER_OF_WRITES; i++) {
            writtenList.addAll(animals);
        }
        assertEquals(writtenList, deserializedAnimals);
    }

    @Test
    public void appendingTest() throws IOException, ClassNotFoundException {
        List<Animal> smallAnimalsList = new ArrayList<>();
        smallAnimalsList.add(animals.get(0));
        String filePath = DEFAULT_SERIALIZE_OUTPUT_FILE.toAbsolutePath().toString();
        serializer.defaultSerialize(smallAnimalsList, filePath, true);
        serializer.defaultSerialize(smallAnimalsList, filePath, false);
        smallAnimalsList = serializer.defaultDeserialize(filePath, 2);
        if (smallAnimalsList.get(0) == smallAnimalsList.get(1)) {
            System.out.println("True");
        }
        assertEquals(smallAnimalsList.get(0), smallAnimalsList.get(1));
    }
}