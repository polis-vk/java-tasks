package ru.mail.polis.homework.collections.streams;

import java.util.Arrays;
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
    public static List<String> wordFrequency(Stream<String> lines) {                    //Комментарии писал для себя, писать stream'ы в первый раз больно
        return lines.map(String::toLowerCase)                                           //Перевод строки в нижний регистр
                .flatMap(text -> Arrays.stream(text.split("[.,!:\\-?;\\s]+")))   //Разбиение строки на слова
                .collect(Collectors.toMap(key->key, value -> 1, Integer::sum))    //Перевод потока слов в карту Map<String, Integer>
                .entrySet()                                                             //Перевод карты в набор вхождений
                .stream()                                                               //Переход от набора вхождений к потоку
                .sorted(Map.Entry                                                       //Сортировка вхождений
                        .<String, Integer>comparingByValue()                            //Сортировка по возрастанию значения
                        .reversed()                                                     //Обратная сортировка, т.к. нам нужна сортировка по убыванию
                        .thenComparing(Map.Entry.comparingByKey()))                     //Сортировка по лексографическому порядку
                .map(Map.Entry::getKey)                                                 //Перевод потока вхождений в поток строк
                .limit(10)                                                              //Удаление всех элементов потока кроме первых 10
                .collect(Collectors.toList());                                          //Перевод потока слов в список
    }
}
