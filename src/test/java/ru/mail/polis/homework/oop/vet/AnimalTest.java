package ru.mail.polis.homework.oop.vet;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class AnimalTest {

    @Test
    public void testGenerateCat() {
        Animal cat = GeneratorAnimal.generateAnimal("cat");
        assertEquals("Mow-Mow", cat.say());
        assertEquals(4, cat.getLegs());
        assertEquals(MoveType.RUN, cat.moveType());
    }

    @Test
    public void testGenerateDog() {
        Animal dog = GeneratorAnimal.generateAnimal("dog");
        assertEquals("Wow-wow", dog.say());
        assertEquals(4, dog.getLegs());
        assertEquals(MoveType.RUN, dog.moveType());
    }

    @Test
    public void testGenerateKangaroo() {
        Animal kangaroo = GeneratorAnimal.generateAnimal("kangaroo");
        assertEquals("Shha", kangaroo.say());
        assertEquals(2, kangaroo.getLegs());
        assertEquals(MoveType.JUMP, kangaroo.moveType());
    }

    @Test
    public void testGeneratePigeon() {
        Animal pigeon = GeneratorAnimal.generateAnimal("pigeon");
        assertEquals("curls-curls", pigeon.say());
        assertEquals(2, pigeon.getLegs());
        assertEquals(MoveType.FLY, pigeon.moveType());
    }

    @Test
    public void testGenerateCow() {
        Animal cow = GeneratorAnimal.generateAnimal("cow");
        assertEquals("Moo-Moo", cow.say());
        assertEquals(4, cow.getLegs());
        assertEquals(MoveType.RUN, cow.moveType());
    }

    @Test
    public void testGenerateShark() {
        Animal shark = GeneratorAnimal.generateAnimal("shark");
        assertEquals("Clack Clack", shark.say());
        assertEquals(0, shark.getLegs());
        assertEquals(MoveType.SWIM, shark.moveType());
    }

    @Test
    public void testGenerateSnake() {
        Animal snake = GeneratorAnimal.generateAnimal("snake");
        assertEquals("Shhhh", snake.say());
        assertEquals(0, snake.getLegs());
        assertEquals(MoveType.CRAWL, snake.moveType());
    }

    @Test
    public void testCostTherapy() {
        List<Animal> list = new ArrayList<>();
        list.add(GeneratorAnimal.generateAnimal("cat"));
        list.add(GeneratorAnimal.generateAnimal("dog"));
        list.add(GeneratorAnimal.generateAnimal("cow"));
        list.add(GeneratorAnimal.generateAnimal("kangaroo"));
        list.add(GeneratorAnimal.generateAnimal("pigeon"));

        Animal rareShark = GeneratorAnimal.generateAnimal("shark");
        ((WildAnimal) rareShark).setOrganizationName("GreenPeace");
        list.add(rareShark);

        Animal rareSnake = GeneratorAnimal.generateAnimal("snake");
        ((WildAnimal) rareSnake).setOrganizationName("WWF");
        list.add(rareSnake);

        Veterinary veterinary1 = new Veterinary(5, 10, Collections.singletonList("GreenPeace"));
        assertEquals(54, veterinary1.calculateCostTherapy(list.toArray(new Animal[0])));


        Veterinary veterinary2 = new Veterinary(10, 5, Arrays.asList("GreenPeace", "WWF"));
        assertEquals(58, veterinary2.calculateCostTherapy(list.toArray(new Animal[0])));

    }

}