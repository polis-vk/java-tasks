package ru.mail.polis.homework.simple;

public class Main {

    public static void main(String[] args) {
        System.out.println(hello());
    }

    static String hello() {
        return "Hello world! I am a first program";
        /*
         * Тест выдавал false, потому что в выводимой строке I am a first...
         * был неверный символ 'a'. При замене его на английскую букву, тест был пройден
         */
    }
}
