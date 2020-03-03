package ru.mail.polis.homework.simple;

public class Main {

    public static void main(String[] args) {
        System.out.println(hello());
    }

    /**
     * Проблема заключалась в том, что в строке использовались и русские и английские символы
     */
    static String hello() {
        return "Hello world! I am a first program";
    }
}
