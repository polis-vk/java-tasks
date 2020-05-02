package ru.mail.polis.homework.io.objects;

public enum Type {
    Cat,
    Dog,
    Cow,
    Parrot,
    Fish;

    public static Type parseType(String parse) {
        switch (parse) {
            case "Cat":
                return Type.Cat;
            case "Dog":
                return Type.Dog;
            case "Cow":
                return Type.Cow;
            case "Parrot":
                return Type.Parrot;
            case "Fish":
                return Type.Fish;
            default:
                return null;
        }
    }
}
