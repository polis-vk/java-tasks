package ru.mail.polis.homework.oop.vet;

public class GeneratorAnimal {
    private GeneratorAnimal() {
    }

    /**
     * В зависимости от передоваемой строки, должен геенрировать разные виды дочерних объектов
     * класса Animal. Дочерние классы должны создаваться на следующие наборы строк:
     * - cat
     * - dog
     * - kangaroo
     * - pigeon
     * - cow
     * - shark
     * - snake
     * Так же при реализации классов стоит учитывать являются ли животные дикими или домашними
     * и в зависимости от этого они должны реализовывать тот или иной интерфейс.
     *
     * @param animalType - тип животного которое надо создать
     * @return - соответствующего потомка
     */
    public static Animal generateAnimal(String animalType)
    {
        if(animalType.equals("cat")){return new Cat(4);}
        if(animalType.equals("dog")){return new Dog(4);}
        if(animalType.equals("kangaroo")){return new Kangaroo(2);}
        if(animalType.equals("pigeon")){return new Pigeon(2);}
        if(animalType.equals("cow")){return new Cow(4);}
        if(animalType.equals("shark")){return new Shark(0);}
        if(animalType.equals("snake")){return new Snake(0);}
        return new Animal(6666);
    }
}

class Cat extends Animal implements Pet
{
    public Cat(int legs) {
        super(legs);
    }
    public String say() {
        return "Mow-Mow";
    }

    public MoveType moveType() {
        return MoveType.RUN;
    }

    public int getLegs() {
        return 4;
    }
}

class Dog extends Animal implements Pet
{
    public Dog(int legs) {
        super(legs);
    }
    public String say() {
        return "Wow-wow";
    }

    public MoveType moveType() {
        return MoveType.RUN;
    }

    public int getLegs() {
        return 4;
    }
}

class Kangaroo extends Animal implements WildAnimal, Pet
{
    private String organization_name;
    public Kangaroo(int legs) {
        super(legs);
    }
    public String say() {
        return "Shha";
    }

    public MoveType moveType() {
        return MoveType.JUMP;
    }

    public int getLegs() {
        return 2;
    }

    @Override
    public String getOrganizationName() {
        return organization_name;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization_name = organizationName;
    }
}

class Pigeon extends Animal implements WildAnimal, Pet
{
    private String organization_name;
    public Pigeon(int legs) {
        super(legs);
    }
    public String say() {
        return "curls-curls";
    }

    public MoveType moveType() {
        return MoveType.FLY;
    }

    public int getLegs() {
        return 2;
    }

    @Override
    public String getOrganizationName() {
        return organization_name;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization_name = organizationName;
    }
}

class Cow extends Animal implements Pet
{
    public Cow(int legs) {
        super(legs);
    }
    public String say() {
        return "Moo-Moo";
    }

    public MoveType moveType() {
        return MoveType.RUN;
    }

    public int getLegs() {
        return 4;
    }
}

class Shark extends Animal implements WildAnimal
{
    private String organization_name;
    public Shark(int legs) {
        super(legs);
    }
    public String say() {
        return "Clack Clack";
    }

    public MoveType moveType() {
        return MoveType.SWIM;
    }

    public int getLegs() {
        return 0;
    }

    @Override
    public String getOrganizationName() {
        return organization_name;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization_name = organizationName;
    }
}

class Snake extends Animal implements WildAnimal
{
    private String organization_name;
    public Snake(int legs) {
        super(legs);
    }
    public String say() {
        return "Shhhh";
    }

    public MoveType moveType() {
        return MoveType.CRAWL;
    }

    public int getLegs() {
        return 0;
    }

    @Override
    public String getOrganizationName() {
        return organization_name;
    }

    @Override
    public void setOrganizationName(String organizationName) {
        this.organization_name = organizationName;
    }
}