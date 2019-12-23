package ru.mail.polis.homework.collections.streams;

import java.util.*;
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
        Map<String, Long> map = new TreeMap<>();
        lines.forEach(str -> {
            Arrays.stream(str.split("( |\\.|,|!|:|-|\\?|;)+")).forEach(word -> {
                String s = word.toLowerCase();
                long l = map.getOrDefault(s, 0l);
                map.put(s, ++l);
            });
        });
        List<String> list = new ArrayList<>();
        map.entrySet().stream().sorted((e1, e2) -> Long.compare(e2.getValue(), e1.getValue())).limit(10).forEach(entry -> {
            list.add(entry.getKey());
        });
        return list;
    }



}
