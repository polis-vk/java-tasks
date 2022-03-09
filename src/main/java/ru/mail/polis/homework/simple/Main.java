package ru.mail.polis.homework.simple;

import ru.mail.polis.homework.objects.MaxTask;

import java.util.Arrays;

public class Main {

    public static void main(String[] args) {
        int[] arr = {5, 7, 1, 8, 9, 1, 4, 5, 10, 2, 8, 3, 1};
        System.out.println(Arrays.toString(MaxTask.getMaxArray(arr, 4)));
    }

    static String hello() {
        return "Hello world! I am а first program";
    }
}
