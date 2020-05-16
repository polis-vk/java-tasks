package ru.mail.polis.homework.io.objects;

import org.junit.After;
import org.junit.Test;


import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.*;

public class SerializerTest {


    private Serializer serializer = new Serializer();

    private static List<Animal> generate() {
        List<Animal> animals = new ArrayList();

        List<Vaccination> vaccinations = new ArrayList();

        vaccinations.add(new Vaccination("22.05.2001", "Vanguard", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("22.05.2001", "Vaczicet", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("22.05.2001", "Vanguard", "Dr. Doolittle", "V.R. Soloviev"));
        vaccinations.add(new Vaccination("13.10.2001", "Microderms", "XXI century", "A.D. Plackida"));

        Animal mother = new Animal("Janifer Aniston Jr.", "5.04.1995", AnimalTypes.MAMMALS);
        Animal father = new Animal("Eric Slowhand Clapton", "1.03.1994", AnimalTypes.MAMMALS);

        animals.add(new Animal("John Mayer II", "1.03.2001", AnimalTypes.MAMMALS, mother, father, vaccinations, null, "Rossosh"));

        vaccinations = new ArrayList();

        vaccinations.add(new Vaccination("23.06.2002", "Vanguard", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("12.07.2002", "Vaczicet", "Malusheva Inc.", "E.Z. Malusheva"));
        vaccinations.add(new Vaccination("12.07.2002", "Vanguard", "Woof! Woof!", "A.N. Myasnikov"));
        vaccinations.add(new Vaccination("15.12.2002", "Microderms", "Malusheva Inc.y", "E.Z. Malusheva"));

        mother = new Animal("Patty Boyd", "12.03.1998", AnimalTypes.MAMMALS);
        father = new Animal("Eric Slowhand Clapton", "1.03.1994", AnimalTypes.MAMMALS);

        animals.add(new Animal("White Beam Black Ear", "13.03.2002", AnimalTypes.MAMMALS, mother, father, vaccinations, null, "Voronezh"));

        return animals;
    }

    private static List<Animal> generateForMany() {
        List<Animal> animals = new ArrayList();

        List<Vaccination> vaccinations = new ArrayList();

        vaccinations.add(new Vaccination("22.05.2001", "Vanguard", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("22.05.2001", "Vaczicet", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("22.05.2001", "Vanguard", "Dr. Doolittle", "V.R. Soloviev"));

        Animal mother = new Animal("Alla Pugachova", "5.04.1990", AnimalTypes.MAMMALS);
        Animal father = new Animal("Philipp Kirkorov", "1.03.1991", AnimalTypes.MAMMALS);

        animals.add(new Animal("bababa s bebebe", "1.03.2000", AnimalTypes.MAMMALS, mother, father, vaccinations, null, "Berlin"));

        vaccinations = new ArrayList();

        vaccinations.add(new Vaccination("23.06.2002", "Vanguard", "Dr. Doolittle", "N.N. Drozdov"));
        vaccinations.add(new Vaccination("12.07.2002", "Ot Bloch i vshey", "Malusheva Inc.", "E.Z. Malusheva"));

        mother = new Animal("Lenin V.I.", "12.03.1993", AnimalTypes.MAMMALS);
        father = new Animal("Stalin I.V.", "1.03.1992", AnimalTypes.MAMMALS);

        animals.add(new Animal("Socialism", "13.03.2000", AnimalTypes.MAMMALS, mother, father, vaccinations, null, "Moscow"));

        return animals;
    }

    @After
    public void remove() throws IOException {
        Files.delete(Paths.get("src", "test", "resources", "serializer", "animal.txt"));
    }

    @Test
    public void defaultSerializationJustList() throws IOException {
        List<Animal> animalListBefore = generate();
        serializer.defaultSerialize(animalListBefore, Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        List<Animal> animalListAfter = serializer.defaultDeserialize(Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        assertEquals(animalListBefore, animalListAfter);
    }

    @Test
    public void defaultSerializationEmptyList() {
        List<Animal> animalListBefore = new ArrayList<>();
        serializer.defaultSerialize(animalListBefore, Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        List<Animal> animalListAfter = serializer.defaultDeserialize(Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        assertEquals(animalListBefore, animalListAfter);
    }

    @Test
    public void defaultSerializationManyLists() {

        List<Animal> animalListBeforeFirst = generate();
        serializer.defaultSerialize(animalListBeforeFirst, Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        List<Animal> animalListAfterFirst = serializer.defaultDeserialize(Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());

        List<Animal> animalListBeforeSecond = generateForMany();
        serializer.defaultSerialize(animalListBeforeSecond, Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());
        List<Animal> animalListAfterSecond = serializer.defaultDeserialize(Paths.get("src", "test", "resources", "serializer", "animal.txt").toString());

        assertEquals(animalListBeforeFirst, animalListAfterFirst);
        assertEquals(animalListBeforeSecond, animalListAfterSecond);
        assertNotEquals(animalListBeforeFirst, animalListAfterSecond);
    }
}
