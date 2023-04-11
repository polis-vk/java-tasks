package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Написать программу, которая из текста (стрим строк), возвращает 10 самых популярных слов (В порядке убывания частоты).
 * Словом считается последовательность символов из букв и цифр от пробела до пробела или знака препинания (.,!:-?;).
 * (Посмотрите статические методы в классе Character)
 * <p>
 * В исходном стриме строка - это строка из книги, которая может содержать в себе много слов.
 * <p>
 * Если слов в стриме меньше 10, то вывести все слова. Если слова имеют одинаковое количество упоминаний, то выводить
 * в лексикографическом порядеке.
 * Слова надо сравнивать без учета регистра.
 * 5 тугриков
 */
public class WordFrequency {

    /**
     * Задачу можно решить без единого условного оператора, только с помощью стримов.
     */
    public static List<String> wordFrequency(Stream<String> lines) {
        List<String> allWords = new ArrayList<>();
        HashMap<String, Integer> wordAndFrequency = new HashMap<>();

        lines.forEach(s -> allWords.addAll(Arrays.asList(s.toLowerCase().replaceAll("[^a-zа-я]", " ").split("\\s+"))));

        allWords.forEach(s -> wordAndFrequency.merge(s, 1, Integer::sum));


        return wordAndFrequency
                .entrySet()
                .stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .sorted((x, y) -> x.getValue().equals(y.getValue())
                        ? x.getKey().compareTo(y.getKey())
                        : 0)
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }


}
