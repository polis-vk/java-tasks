package ru.mail.polis.homework.collections.streams;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Написать программу, которая из текста (стрим строк), возвращает 10 самых популярных слов (В порядке убывания частоты).
 * Словом считается последовательность символов из букв и цифр от пробела до пробела или знака препинания (.,!:-?;).
 * (Посмотрите статические методы в классе Character)
 *
 * В исходном стриме строка - это строка из книги, которая может содержать в себе много слов.
 *
 * Если слов в стриме меньше 10, то вывести все слова. Если слова имеют одинаковое количество упоминаний, то выводить
 * в лексикографическом порядеке.
 * Слова надо сравнивать без учета регистра.
 */
public class WordFrequency {

    /**
     * Задачу можно решить без единого условного оператора, только с помощью стримов.
     * Если будут использоваться условные операторы, то оценка максимальная оценка 2 балла.
     */


    public static List<String> wordFrequency(Stream<String> lines) {
        List<String> list = lines
                .flatMap((str) -> Arrays.stream(str.split("[.,!:-?; ]+")))
                .collect(Collectors.toList());

        list.forEach(s -> s = s.toLowerCase());

        return  list
                .stream()
                .collect(Collectors.toMap(Function.identity(), v -> 1, Integer::sum))
                .entrySet()
                .stream()
                .sorted((t1, t2) -> t2.getValue().compareTo(t1.getValue()))
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }

}
