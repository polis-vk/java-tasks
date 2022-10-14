package ru.mail.polis.homework.oop.vet;

import org.junit.Test;

import java.util.*;

import static org.junit.Assert.assertEquals;

public class AnimalTest {

    @Test
    public void testGenerateCat() {
        AbstractAnimal cat = GeneratorAnimal.generateAnimal("cat");
        assertEquals("Mow-Mow", cat.say());
        assertEquals(4, cat.getLegs());
        assertEquals(MoveType.RUN, cat.moveType());
    }

    @Test
    public void testGenerateDog() {
        AbstractAnimal dog = GeneratorAnimal.generateAnimal("dog");
        assertEquals("Wow-wow", dog.say());
        assertEquals(4, dog.getLegs());
        assertEquals(MoveType.RUN, dog.moveType());
    }

    @Test
    public void testGenerateKangaroo() {
        AbstractAnimal kangaroo = GeneratorAnimal.generateAnimal("kangaroo");
        assertEquals("Shha", kangaroo.say());
        assertEquals(2, kangaroo.getLegs());
        assertEquals(MoveType.JUMP, kangaroo.moveType());
    }

    @Test
    public void testGeneratePigeon() {
        AbstractAnimal pigeon = GeneratorAnimal.generateAnimal("pigeon");
        assertEquals("curls-curls", pigeon.say());
        assertEquals(2, pigeon.getLegs());
        assertEquals(MoveType.FLY, pigeon.moveType());
    }

    @Test
    public void testGenerateCow() {
        AbstractAnimal cow = GeneratorAnimal.generateAnimal("cow");
        assertEquals("Moo-Moo", cow.say());
        assertEquals(4, cow.getLegs());
        assertEquals(MoveType.RUN, cow.moveType());
    }

    @Test
    public void testGenerateShark() {
        AbstractAnimal shark = GeneratorAnimal.generateAnimal("shark");
        assertEquals("Clack Clack", shark.say());
        assertEquals(0, shark.getLegs());
        assertEquals(MoveType.SWIM, shark.moveType());
    }

    @Test
    public void testGenerateSnake() {
        AbstractAnimal snake = GeneratorAnimal.generateAnimal("snake");
        assertEquals("Shhhh", snake.say());
        assertEquals(0, snake.getLegs());
        assertEquals(MoveType.CRAWL, snake.moveType());
    }

    @Test
    public void testCostTherapy() {
        List<AbstractAnimal> list = new ArrayList<>();
        list.add(GeneratorAnimal.generateAnimal("cat"));
        list.add(GeneratorAnimal.generateAnimal("dog"));
        list.add(GeneratorAnimal.generateAnimal("cow"));
        list.add(GeneratorAnimal.generateAnimal("kangaroo"));
        list.add(GeneratorAnimal.generateAnimal("pigeon"));

        AbstractAnimal rareShark = GeneratorAnimal.generateAnimal("shark");
        ((WildAnimal) rareShark).setOrganizationName("GreenPeace");
        list.add(rareShark);

        AbstractAnimal rareSnake = GeneratorAnimal.generateAnimal("snake");
        ((WildAnimal) rareSnake).setOrganizationName("WWF");
        list.add(rareSnake);

        Veterinary veterinary1 = new Veterinary(5, 10, Collections.singletonList("GreenPeace"));
        assertEquals(54, veterinary1.calculateCostTherapy(list.toArray(new AbstractAnimal[0])));

        Veterinary veterinary2 = new Veterinary(10, 5, Arrays.asList("GreenPeace", "WWF"));
        assertEquals(58, veterinary2.calculateCostTherapy(list.toArray(new AbstractAnimal[0])));

    }

}