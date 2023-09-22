package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.DoubleUnaryOperator;
import java.util.function.Predicate;
import java.util.stream.Stream;

public class SimpleStreams {

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */

    public static boolean isPrime(int n) {
        List<Integer> list = List.of(n);
        boolean bool = false;
        boolean b = Stream.iterate(2, t -> t+1)
                .filter(t -> n%t == 0)
                .limit(1)
                .anyMatch(x -> { if(x == n) return true;
                    return false;});
        //b.forEach(System.out::println);
        return b;
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        List<String> words = new LinkedList<>();
        Map<String, Integer> map = new HashMap<>();
        StringBuilder empty = new StringBuilder();
        for(int i = 0; i < text.length(); ++i)
        {
            if(Character.isLetter(text.charAt(i)))
                empty.append(text.charAt(i));
            else {
                if(!empty.toString().isEmpty()) {
                    words.add(empty.toString());
                    empty = new StringBuilder();
                }
            }
        }
        Stream<String> mapStream = words.stream()
                .filter(badWords::contains);
        mapStream.forEach(System.out::println);
        return map;
    }


    /**
     * Маленький мальчик кидает мячик n раз в поле силы тяжести под углом alpha к поверхности земли.
     * Так же известно, что мальчик устает с каждым броском все больше. Дана начальная скорость v - скорость
     * при первом броске и функция изменения скорости от номера броска - changeV
     *
     * Посчитать расстояние на которое улетит мячик.
     * Для расчета дальности броска можно пользоваться физическими формулами движения и законом сохранения энергии.
     * g = 9.8
     *
     * 3 балла
     */
    public static double calcDistance(double v, DoubleUnaryOperator changeV, double alpha, int n) {
        return 0;
    }
}
