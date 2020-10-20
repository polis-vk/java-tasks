package ru.mail.polis.homework.collections.streams;


import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.DoubleUnaryOperator;
import java.util.stream.Stream;

public class SimpleStreams {
    public static void main(String[] args) {
        System.out.println(createBadWordsDetectingStream("hello my name is Vadim, fg! hhg s hello!&", List.of("hello")));
    }

    /**
     * Реализуйте проверку на простоту входящего числа с помощью стримов.
     * Никаких циклов.
     * 1 балл
     */
    public static boolean isPrime(int n) {
        return Stream.iterate(2, x -> x < Math.sqrt(n), x -> x + 1).noneMatch(x -> n % x == 0);
    }

    /**
     * Вам передается текст и список плохих слов. Вам нужно вернуть мапу (слово -> количество упоминаний в тексте)
     * Слово - набор символ между началом строки/ концом строки / пробелами / знаками препинания (.,;:!?)
     * 1 балл
     */
    public static Map<String, Integer> createBadWordsDetectingStream(String text, List<String> badWords) {
        Map<String, Integer> out = new HashMap<>();
        Arrays.stream(text.split("[^aA-zZ]+")).forEach(x -> {
            if (badWords.contains(x)) {
                if (out.containsKey(x)){
                    int count = out.get(x);
                    out.put(x, ++count);
                } else out.put(x, 1);
            }
        });
        return out;
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
