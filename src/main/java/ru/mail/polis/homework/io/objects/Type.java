package ru.mail.polis.homework.io.objects;

public enum Type {
    Dog,
    Cat,
    Lion,
    Crocodile,
    Fish,
    Chicken,
    unidentifiedAnimal;

    public static Type parseType(String type) {
        switch (type) {
            case "Dog":
                return Dog;
            case "Cat":
                return Cat;
            case "Lion":
                return Lion;
            case "Crocodile":
                return Crocodile;
            case "Fish":
                return Fish;
            case "Chicken":
                return Chicken;
            default:
                return unidentifiedAnimal;
        }
    }
}