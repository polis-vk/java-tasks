package ru.mail.polis.homework.collections.streams;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import java.util.Arrays;
import java.util.stream.Collectors;


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
        Map<String, Integer> mapFreq = new HashMap<>();
        lines.forEach(sentence -> Arrays.stream(sentence.toLowerCase().split("\\s*(\\s|,|!|\\.)\\s*"))
                .forEach(word -> mapFreq.merge(word, 1, Integer::sum)));
        return mapFreq.entrySet().stream()
                .sorted(Map.Entry.comparingByKey())
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(10).map(Map.Entry::getKey).collect(Collectors.toList());
    }


}
