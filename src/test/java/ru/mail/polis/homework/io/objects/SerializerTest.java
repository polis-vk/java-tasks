package ru.mail.polis.homework.io.objects;

import org.apache.commons.io.FileUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

public class SerializerTest {
    private static String fileName = "testResult";
    private static int kaka;
    private static int objectsCount = 10;
    private static List<Animal> animals;
    private static final Serializer serializer = new Serializer();

    @Before
    public static void initial() {
        animals = new ArrayList<Animal>(objectsCount);
        for(int i = 0; i < objectsCount; i++) {
            Colour cl = new Colour(0, true, true);
            animalType t = animalType.cat;
            List<String> f = Arrays.asList("milk", "water", "meat");
            Animal a = new Animal(2, 0.1, true, "Masa", t, f, cl);
            animals.add(i, a);
        }
        System.out.print("initial");
    }

    @Test
    public void checkCustomSerialize() throws IOException {

        serializer.customSerialize(animals, fileName);
    }



    @Test
    public void checkOUT() throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeInt(kaka);
            System.out.print("out");
        }
    }

    @Test
    public void checkIN() throws IOException {
        if (!Files.exists(Paths.get(fileName))) {
            System.out.print("NO FILES");
        }
        try (DataInputStream in = new DataInputStream(new FileInputStream(fileName))) {
            System.out.print("FILES");
            int str = in.readInt();
            System.out.print(str);
        }
    }
}
