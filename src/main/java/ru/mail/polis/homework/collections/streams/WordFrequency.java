package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.ToIntFunction;
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
 * 3 балла
 */
public class WordFrequency {

    /**
     * Задачу можно решить без единого условного оператора, только с помощью стримов.
     */
    public static List<String> wordFrequency(Stream<String> lines) {
        Map<String,Integer> countElements = new HashMap<>();
        return lines.flatMap(line -> Arrays.stream(line.split("[.,!:\\-?;\\s]+")))
                .map(String::toLowerCase)
                .peek(element -> countElements.put(element, countElements.getOrDefault(element, 0) + 1))
                .distinct()
                .sorted(Comparator.comparing(countElements::get).reversed().thenComparing(Object::toString))
                .limit(10)
                .collect(Collectors.toList());
    }
}
