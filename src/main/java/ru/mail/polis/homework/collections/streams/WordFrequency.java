package ru.mail.polis.homework.collections.streams;

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
        return lines
                .flatMap(s -> Arrays.stream(s.toLowerCase().split("[\n .,;:!?]")))
                .peek(s -> System.out.println(s))
                .filter(s -> s.length() > 0)
                .peek(System.out::println)
                .collect(Collectors.groupingBy(s -> s, Collectors.counting()))
                .entrySet()
                .stream()
                .sorted(
                        (o1, o2) -> {
                            int res = o2.getValue().compareTo(o1.getValue());
                            return res == 0 ? o1.getKey().compareTo(o2.getKey()) : res;
                        }
                )
                .map(Map.Entry::getKey)
                .limit(10)
                .collect(Collectors.toList());
    }


}
