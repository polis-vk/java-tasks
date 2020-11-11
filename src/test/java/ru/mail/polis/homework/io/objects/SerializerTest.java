package ru.mail.polis.homework.io.objects;

import junit.framework.TestCase;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class SerializerTest extends TestCase {

    static final int COPY_COUNT = 1000;

    private List<Animal> generateTestSet(){
        Random rnd = new Random();
        List<Animal> dataset = new ArrayList<Animal>();
        for(int i = 0; i < 10; ++i){
            List<String> foods = new ArrayList<>();
            for(int j = 0; j < rnd.nextInt(5); ++j){
                foods.add("FavouriteFood" + j);
            }
            Animal a =
                    new Animal("Name" + i,"Species" + rnd.nextInt(15),
                            new Date(rnd.nextInt() * 1000L), Animal.AnimalGroup.values()[rnd.nextInt(Animal.AnimalGroup.values().length)],
                            new Disease("Disease" + rnd.nextInt(10), Disease.DiseaseSeverity.values()[rnd.nextInt(Disease.DiseaseSeverity.values().length)]),
                            foods, rnd.nextInt(15), rnd.nextDouble(), rnd.nextDouble());
            dataset.add(a);
        }
        List<Animal> finalDataset = new ArrayList<>(COPY_COUNT);
        for(int i = 0; i < COPY_COUNT; ++i){
            for(Animal a : dataset){
                finalDataset.add(new Animal(a));
            }
        }
        return finalDataset;
    }

    private List<AnimalWithMethods> generateTestSetWithMethods(){
        Random rnd = new Random();
        List<AnimalWithMethods> dataset = new ArrayList<AnimalWithMethods>();
        for(int i = 0; i < 10; ++i){
            List<String> foods = new ArrayList<>();
            for(int j = 0; j < rnd.nextInt(5); ++j){
                foods.add("FavouriteFood" + j);
            }
            AnimalWithMethods a =
                    new AnimalWithMethods("Name" + i,"Species" + rnd.nextInt(15),
                            new Date(rnd.nextInt() * 1000L), AnimalWithMethods.AnimalGroup.values()[rnd.nextInt(AnimalWithMethods.AnimalGroup.values().length)],
                            new DiseaseWithMethods("Disease" + rnd.nextInt(10), DiseaseWithMethods.DiseaseSeverity.values()[rnd.nextInt(DiseaseWithMethods.DiseaseSeverity.values().length)]),
                            foods, rnd.nextInt(15), rnd.nextDouble(), rnd.nextDouble());
            dataset.add(a);
        }
        List<AnimalWithMethods> finalDataset = new ArrayList<>(COPY_COUNT);
        for(int i = 0; i < COPY_COUNT; ++i){
            for(AnimalWithMethods a : dataset){
                finalDataset.add(new AnimalWithMethods(a));
            }
        }
        return finalDataset;
    }

    private List<AnimalExternalizable> generateTestSetExternalizable(){
        Random rnd = new Random();
        List<AnimalExternalizable> dataset = new ArrayList<>();
        for(int i = 0; i < 10; ++i){
            List<String> foods = new ArrayList<>();
            for(int j = 0; j < rnd.nextInt(5); ++j){
                foods.add("FavouriteFood" + j);
            }
            AnimalExternalizable a =
                    new AnimalExternalizable("Name" + i,"Species" + rnd.nextInt(15),
                            new Date(rnd.nextInt() * 1000L), AnimalExternalizable.AnimalGroup.values()[rnd.nextInt(AnimalExternalizable.AnimalGroup.values().length)],
                            new DiseaseExternalizable("Disease" + rnd.nextInt(10), DiseaseExternalizable.DiseaseSeverity.values()[rnd.nextInt(DiseaseWithMethods.DiseaseSeverity.values().length)]),
                            foods, rnd.nextInt(15), rnd.nextDouble(), rnd.nextDouble());
            dataset.add(a);
        }
        List<AnimalExternalizable> finalDataset = new ArrayList<>(COPY_COUNT);
        for(int i = 0; i < COPY_COUNT; ++i){
            for(AnimalExternalizable a : dataset){
                finalDataset.add(new AnimalExternalizable(a));
            }
        }
        return finalDataset;
    }

    public void testDefaultSerialization() {
        List<Animal> dataset = generateTestSet();
        Serializer sr = new Serializer();
        String filename = "defaultSerial.txt";

        long writeStart = System.currentTimeMillis();
        sr.defaultSerialize(dataset, filename);
        long writeEnd = System.currentTimeMillis();

        long readStart = System.currentTimeMillis();
        List<Animal> gotDataset = sr.defaultDeserialize(filename);
        long readEnd = System.currentTimeMillis();

        System.out.println("Default: Write: " + (writeEnd - writeStart) + "; Read: " + (readEnd - readStart));
        for(int i = 0; i < dataset.size(); ++i){
            assertEquals(dataset.get(i), gotDataset.get(i));
        }
        File f = new File(filename);
        System.out.println("\tFilesize: " + f.length());
        f.delete();
    }

    public void testSerializationWithMethods() {
        List<AnimalWithMethods> dataset = generateTestSetWithMethods();
        Serializer sr = new Serializer();
        String filename = "methods.txt";

        long writeStart = System.currentTimeMillis();
        sr.serializeWithMethods(dataset, filename);
        long writeEnd = System.currentTimeMillis();

        long readStart = System.currentTimeMillis();
        List<AnimalWithMethods> gotDataset = sr.deserializeWithMethods(filename);
        long readEnd = System.currentTimeMillis();

        System.out.println("Methods: Write: " + (writeEnd - writeStart) + "; Read: " + (readEnd - readStart));
        for(int i = 0; i < dataset.size(); ++i){
            assertEquals(dataset.get(i), gotDataset.get(i));
        }
        File f = new File(filename);
        System.out.println("\tFilesize: " + f.length());
        f.delete();
    }

    public void testSerializationWithExternalizable() {
        List<AnimalExternalizable> dataset = generateTestSetExternalizable();
        Serializer sr = new Serializer();
        String filename = "externalizable.txt";

        long writeStart = System.currentTimeMillis();
        sr.serializeWithExternalizable(dataset, filename);
        long writeEnd = System.currentTimeMillis();

        long readStart = System.currentTimeMillis();
        List<AnimalExternalizable> gotDataset = sr.deserializeWithExternalizable(filename);
        long readEnd = System.currentTimeMillis();

        System.out.println("Externalizable: Write: " + (writeEnd - writeStart) + "; Read: " + (readEnd - readStart));
        for(int i = 0; i < dataset.size(); ++i){
            assertEquals(dataset.get(i), gotDataset.get(i));
        }
        File f = new File(filename);
        System.out.println("\tFilesize: " + f.length());
        f.delete();
    }

    public void testCustomSerialization() {
        List<Animal> dataset = generateTestSet();
        Serializer sr = new Serializer();
        String filename = "custom.txt";
        long writeStart = System.currentTimeMillis();
        sr.customSerialize(dataset, filename);
        long writeEnd = System.currentTimeMillis();

        long readStart = System.currentTimeMillis();
        List<Animal> gotDataset = sr.customDeserialize(filename);
        long readEnd = System.currentTimeMillis();

        System.out.println("Custom: Write: " + (writeEnd - writeStart) + "; Read: " + (readEnd - readStart));
        for(int i = 0; i < dataset.size(); ++i){
            assertEquals(dataset.get(i), gotDataset.get(i));
        }
        File f = new File(filename);
        System.out.println("\tFilesize: " + f.length());
        f.delete();
    }
}