package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collector;
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
        return lines.flatMap(String::lines).map(x -> {return x.toLowerCase().replaceAll("\\p{Punct}", " ").split(" ");})
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .filter(x -> !x.getKey().replaceAll("\\p{Punct}", "").isEmpty())
                .sorted(Comparator.comparing((Function<Map.Entry<String, Long>, Long>) Map.Entry::getValue).reversed().thenComparing(new Comparator<Map.Entry<String, Long>>() {
                    @Override
                    public int compare(Map.Entry<String, Long> stringLongEntry, Map.Entry<String, Long> t1) {
                        return stringLongEntry.getKey().compareTo(t1.getKey());
                    }
                }))
                .limit(10)
                .map( entry -> String.format( "%s", entry.getKey())).collect(Collectors.toList());
    }


}
