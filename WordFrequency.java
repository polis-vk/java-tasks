package ru.mail.polis.homework.collections.streams;

import javax.xml.crypto.dsig.keyinfo.KeyValue;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
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
 * 3 балла
 */
public class WordFrequency {

    /**
     * Задачу можно решить без единого условного оператора, только с помощью стримов.
     */
    public static List<String> wordFrequency(Stream<String> lines) {

        Map<String, Long> dictionary =  lines.map(String::toLowerCase)
                .map(x -> x.split("[.,;:!?\n ]"))
                .flatMap(Arrays::stream)
                .collect(Collectors.groupingBy(x -> x, Collectors.counting()));

        return dictionary.keySet().
                stream()
                .sorted((o1, o2) -> (int)(dictionary.get(o2) - dictionary.get(o1) != 0 ? dictionary.get(o2) - dictionary.get(o1) : o1.compareTo(o2)))
                .skip(1)
                .limit(10)
                .collect(Collectors.toList());
    }

}
