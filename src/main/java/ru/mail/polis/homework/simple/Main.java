package ru.mail.polis.homework.simple;

public class Main {

    public static void main(String[] args) {

        System.out.println(DoubleAdvancedTask.equation(2, 61, -2831, 1400));
        System.out.println(DoubleAdvancedTask.length(0, 1, 0, 5));
        System.out.println(DoubleAdvancedTask.surfaceFunction(
                0,0,1,
                1,1,1,
                10,100,1,
                235,-5));
        System.out.println(IntegerAdvancedTask.progression(1,2,3));
        System.out.println(IntegerAdvancedTask.snake(10, 3, 5, 5, 20, 11));
        System.out.println(IntegerAdvancedTask.kDecimal(454355, 2));
        System.out.println(IntegerAdvancedTask.minNumber(6726455));
    }

    static String hello() {
        return "Hello world! I am а first program";
    }
}
