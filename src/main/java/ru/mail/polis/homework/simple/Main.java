package ru.mail.polis.homework.simple;

public class Main {

    public static void main(String[] args) {
        System.out.println(hello());
    }

    static String hello() {
        return "Hello world! I am a first program";
    }
    // Тест падал из-за того что 'a' перед 'first' была русская, а не английская;
}
