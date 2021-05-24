package ru.mail.polis.homework.collections.streams;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.Map.Entry.comparingByKey;
import static java.util.Map.Entry.comparingByValue;

/**
 * Ќаписать программу, котора€ из текста (стрим строк), возвращает 10 самых попул€рных слов (¬ пор€дке убывани€ частоты).
 * —ловом считаетс€ последовательность символов из букв и цифр от пробела до пробела или знака препинани€ (.,!:-?;).
 * (ѕосмотрите статические методы в классе Character)
 * <p>
 * ¬ исходном стриме строка - это строка из книги, котора€ может содержать в себе много слов.
 * <p>
 * ≈сли слов в стриме меньше 10, то вывести все слова. ≈сли слова имеют одинаковое количество упоминаний, то выводить
 * в лексикографическом пор€деке.
 * —лова надо сравнивать без учета регистра.
 * 3 балла
 */
public class WordFrequency {

    private static final String SEPARATORS_REGEX = "[ .,!:\\-?;]+";
    /**
     * «адачу можно решить без единого условного оператора, только с помощью стримов.
     */
    public static List<String> wordFrequency(Stream<String> lines) {
        return lines
                .map(String::toLowerCase)
                .flatMap(s -> Arrays.stream(s.split(SEPARATORS_REGEX)))
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue()
                        .reversed()
                        .thenComparing(comparingByKey()))
                .limit(10)
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());
    }
}
