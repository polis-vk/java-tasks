package ru.mail.polis.homework.collections.streams;

import java.io.UnsupportedEncodingException;
import java.util.*;
import java.util.function.Function;
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
 */
public class WordFrequency {
//.flatMap(str -> Arrays.stream(str.split(Character.UnicodeBlock.GENERAL_PUNCTUATION.toString())))
    /**
     * Задачу можно решить без единого условного оператора, только с помощью стримов.
     * Если будут использоваться условные операторы, то оценка максимальная оценка 2 балла.
     */
    public static List<String> wordFrequency(Stream<String> lines) {
        return lines
                .flatMap(str -> Arrays.stream(str.split("(\\p{Punct}|\\p{Space})+")))
                .map(String::toLowerCase)
                .collect(Collectors.groupingBy(word->word, Collectors.counting()))
                .entrySet().stream()
                .sorted(Comparator.<Map.Entry<String, Long>>comparingLong(entry->-entry.getValue())
                        .thenComparing(Map.Entry::getKey))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }

}
